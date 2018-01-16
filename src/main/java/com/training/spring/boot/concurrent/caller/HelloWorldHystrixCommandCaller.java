package com.training.spring.boot.concurrent.caller;

import com.training.spring.boot.concurrent.command.HelloWordHystrixCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

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
        List<Future<String>> futuers = new ArrayList<>();
        for(int counter = 1; counter < 15; counter++) {
            HelloWordHystrixCommand hystrixCommand = new HelloWordHystrixCommand(command_greeting, timeout, counter);
            Future<String> future = hystrixCommand.queue();
            futuers.add(future);
        }
        try {
            for(Future<String> future: futuers) {
                String response = future.get();
                logger.info("got response from command execution: " + response);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }
}
