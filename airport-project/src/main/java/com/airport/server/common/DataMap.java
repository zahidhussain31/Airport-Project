package com.airport.server.common;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

@Component
public class DataMap {

    private List<Map<String, Object>> table;
    private String[] headers;
    private List<String> iataCode;
    private List<String> icaoCode;

    @PostConstruct
    private void init() {
        table = new ArrayList<Map<String, Object>>();

        iataCode = new ArrayList<String>();
        icaoCode = new ArrayList<String>();

        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader("./airport-data.txt"));
            String line = reader.readLine();
            parseHeader(line);
            while (true) {
                line = reader.readLine();
                if (line == null) {
                    break;
                }
                parseRow(line);
            }
            reader.close();
        } catch (IOException e) {
            System.out.println("Please keep airport-data.txt in jar path");
            e.printStackTrace();
        }

    }

    private void parseHeader(String line) {
        String[] t = line.split("\\|");
        headers = new String[t.length];
        int i = 0;
        for (String header : t) {
            headers[i++] = header.replace(" ", "_");
        }
    }

    private void parseRow(String line) {
        line = line.replace("\"", "");
        String[] data = line.split("\\|");
        int i = 0;
        Map<String, Object> rowData = new HashMap<String, Object>();
        for (String value : data) {
            rowData.put(headers[i++], value);
        }
        iataCode.add(rowData.get("IATA_Code").toString());
        icaoCode.add(rowData.get("ICAO_Code").toString());
        table.add(rowData);
    }

    public Map<String, Object> get(String code) {
        int pos = iataCode.indexOf(code);
        if (pos == -1) {
            pos = icaoCode.indexOf(code);
        }
        return table.get(pos);
    }

    public double calculateDistance(String originAirportCode, String destinationAirportCode) {

        Map<String, Object> a1 = this.get(originAirportCode);
        Map<String, Object> a2 = this.get(destinationAirportCode);

        double lat1 = Double.valueOf(a1.get("Latitude").toString());
        double lon1 = Double.valueOf(a1.get("Longitude").toString());

        double lat2 = Double.valueOf(a2.get("Latitude").toString());
        double lon2 = Double.valueOf(a2.get("Longitude").toString());

        System.out.println("Lat1: " + lat1 + " Lon1:" + lon1);
        System.out.println("Lat2: " + lat2 + " Lon2:" + lon2);

        return Util.distance(lat1, lon1, lat2, lon2);
    }

}
