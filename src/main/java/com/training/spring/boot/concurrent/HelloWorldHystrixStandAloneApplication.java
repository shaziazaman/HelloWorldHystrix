package com.training.spring.boot.concurrent;

import com.training.spring.boot.concurrent.controller.HelloWorldController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.inject.Inject;

/**
 * Created by shazia on 1/12/2018.
 */

@SpringBootApplication
public class HelloWorldHystrixStandAloneApplication implements CommandLineRunner {

    Logger logger = LoggerFactory.getLogger(HelloWorldHystrixStandAloneApplication.class);

    @Value("${application.greeting}")
    private String greeting;

    @Inject HelloWorldController controller;

    public static void main(String[] args) throws Exception {

        SpringApplication.run(HelloWorldHystrixStandAloneApplication.class, args).close();
    }

    @Override
    public void run(String... strings) throws Exception {
        logger.info(greeting);
        controller.controlFlow();
    }
}
