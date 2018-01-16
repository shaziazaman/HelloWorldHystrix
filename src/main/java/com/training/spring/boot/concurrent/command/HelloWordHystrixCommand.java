package com.training.spring.boot.concurrent.command;

import com.netflix.hystrix.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HelloWordHystrixCommand extends HystrixCommand<String> {
    Logger logger = LoggerFactory.getLogger(HelloWordHystrixCommand.class);

    private String greeting;
    int counter;

    public HelloWordHystrixCommand(String greeting, int timeout, int counter) {
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("CommandGroup"))
                .andCommandKey(HystrixCommandKey.Factory.asKey("CommandKey"))
                .andThreadPoolKey(HystrixThreadPoolKey.Factory.asKey("CommandGroup"))
                .andCommandPropertiesDefaults(HystrixCommandProperties.Setter()
                        .withExecutionTimeoutInMilliseconds(timeout)));
        this.greeting = greeting;
        this.counter = counter;
    }

    @Override
    protected String run() throws Exception {
        logger.info(greeting  + " | " + counter);
        return greeting;
    }

    @Override
    protected String getFallback() {
        // implement the fallback method
        return null;
    }
}
