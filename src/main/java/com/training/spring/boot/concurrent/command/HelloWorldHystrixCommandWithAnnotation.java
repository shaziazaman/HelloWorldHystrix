package com.training.spring.boot.concurrent.command;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.command.AsyncResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import javax.inject.Named;
import java.util.concurrent.Future;

@Named
public class HelloWorldHystrixCommandWithAnnotation {

    Logger logger = LoggerFactory.getLogger(HelloWorldHystrixCommandWithAnnotation.class);

    @Value("${command.greeting}")
    private String greeting;

    private int counter;

    @HystrixCommand(groupKey = "AnnotationGroup"
            , commandKey = "AnnotationGroup"
            , fallbackMethod = "getError"
            , threadPoolKey = "AnnotationGroup"
    )
    public Future<String> callWithAnnotation(int counter) {
        return new AsyncResult<String>() {
            @Override
            public String invoke() {
                utilityProcess(counter);
                return greeting;
            }
        };
    }

    private void utilityProcess(int counter) {
        logger.info("Utility process being called | " + counter);
    }
    public String getError(int counter) {
        return greeting + " | error |" + counter;
    }
}
