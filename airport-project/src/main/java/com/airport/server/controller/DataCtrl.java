package com.airport.server.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.airport.server.common.DataMap;

@RestController
@RequestMapping("/api")
public class DataCtrl {

    private Logger log = LoggerFactory.getLogger(DataCtrl.class);

    @Autowired
    private DataMap dataMap;

    @GetMapping("/getAirpotInfo")
    public Map<String, Object> getAirpotInfo(String code) {
        log.info("getAirpotInfo");
        return dataMap.get(code);
    }

    @GetMapping("/calculateDistance")
    public Map<String, Object> calculateDistance(String originAirportCode, String destinationAirportCode) {
        log.info("getAirpotInfo");
        double distance = dataMap.calculateDistance(originAirportCode, destinationAirportCode);
        Map<String, Object> res = new HashMap<String, Object>();
        res.put("distance", distance);
        return res;
    }

    @ExceptionHandler(Exception.class)
    public Map<String, Object> handleException(Exception ex) {
        Map<String, Object> err = new HashMap<String, Object>();
        err.put("error", ex.getMessage());
        return err;
    }

}
