package com.training.spring.boot.concurrent.caller;

import com.training.spring.boot.concurrent.command.HelloWordHystrixCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import javax.inject.Named;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * Created by shazia on 1/12/2018.
 */

@Named
public class HelloWorldHystrixCommandCaller {
    Logger logger = LoggerFactory.getLogger(HelloWorldHystrixCommandCaller.class);

    @Value("${command.caller.greeting}")
    private String greeting = "Made it to hystrix command";

    @Value("${command.greeting}")
    private String command_greeting;

    @Value("${execution.timeout:#{3000}}")
    private int timeout;

    public void callHystrixCommand(){
        logger.info(greeting);
        HelloWordHystrixCommand hystrixCommand = new HelloWordHystrixCommand(command_greeting, timeout);
        Future<String> future = hystrixCommand.queue();
        try {
            String response = future.get();
            logger.info("got response from command execution: " + response);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }



//    public HelloWorldHystrixCommandCaller(String name) {
//        super(HystrixCommandGroupKey.Factory.asKey("TrainingGroup"));
//        this.name = name;
//    }

//    @Override
//    protected String run() throws Exception {
//        logger.info(greeting);
//        requestProcessor.processRequest(name);
//        return name + " | " + greeting;
//    }
}
