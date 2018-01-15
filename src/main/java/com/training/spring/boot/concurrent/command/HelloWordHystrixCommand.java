package com.training.spring.boot.concurrent.command;

import com.netflix.hystrix.*;
import com.sun.tracing.dtrace.NameAttributes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.inject.Named;

/**
 * Created by shazia on 1/14/2018.
 */

public class HelloWordHystrixCommand extends HystrixCommand<String> {
    Logger logger = LoggerFactory.getLogger(HelloWordHystrixCommand.class);

    private String greeting;

    public HelloWordHystrixCommand(String greeting, int timeout) {
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("GroupName"))
                .andCommandKey(HystrixCommandKey.Factory.asKey("CommandKey"))
                .andThreadPoolKey(HystrixThreadPoolKey.Factory.asKey("ThreadPoolKey"))
                .andCommandPropertiesDefaults(HystrixCommandProperties.Setter()
                        .withExecutionTimeoutInMilliseconds(timeout)));
        this.greeting = greeting;
    }

    @Override
    protected String run() throws Exception {
        logger.info(greeting);
        return greeting;
    }

    @Override
    protected String getFallback() {
        // implement the fallback method
        return null;
    }
}
