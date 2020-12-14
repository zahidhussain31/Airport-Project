package com.airport.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.airport.server")
public class Server {

    public static void main(String[] args) {
        SpringApplication.run(Server.class, args);
        System.out.println("Started..");
    }

}
