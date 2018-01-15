package com.training.spring.boot.concurrent.command;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.netflix.hystrix.contrib.javanica.command.AsyncResult;
import com.sun.tracing.dtrace.NameAttributes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import javax.inject.Named;
import java.util.concurrent.Future;

/**
 * Created by shazia on 1/15/2018.
 */
@Named
public class HelloWorldHystrixCommandWithAnnotation {

    Logger logger = LoggerFactory.getLogger(HelloWorldHystrixCommandWithAnnotation.class);

    @Value("${command.greeting}")
    private String greeting;

    @Value("${execution.timeout:3000}")
    private String timeout;

    @HystrixCommand(groupKey = "AnnotationGroup"
            , commandKey = "getGreeting"
            , fallbackMethod = "getError"
            , commandProperties = {
                @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "1000")
            }
            , threadPoolProperties = {
                @HystrixProperty(name="coreSize", value = "1")
                , @HystrixProperty(name="maxQueueSize",value="5")
            }
    )
    public Future<String> callWithAnnotation() {
        logger.info(greeting);
        return new AsyncResult<String>() {
            @Override
            public String invoke() {
                return greeting;
            }
        };
    }

    public String getError() {
        return null;
    }
}
