package com.training.spring.boot.concurrent.controller;

import com.training.spring.boot.concurrent.caller.HelloWorldHystrixCommandCaller;
import com.training.spring.boot.concurrent.command.HelloWorldHystrixCommandWithAnnotation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@Named
public class HelloWorldController {
    Logger logger = LoggerFactory.getLogger(HelloWorldController.class);
    @Value("${controller.greeting}")
    private String greeting;

    @Inject HelloWorldHystrixCommandCaller hystrixCommandCaller;
    @Inject HelloWorldHystrixCommandWithAnnotation commandWithAnnotation;

    public void controlFlow() {
        logger.info(greeting);
        hystrixCommandCaller.callHystrixCommand();

        List<Future<String>> futures = new ArrayList<>();
        for(int counter = 1; counter < 15; counter++) {
            Future<String> future = commandWithAnnotation.callWithAnnotation(counter);
            futures.add(future);
        }
        try {
            for(Future<String> future: futures) {
                String response = future.get();
                logger.info("response returned from annotated command: " + response);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
