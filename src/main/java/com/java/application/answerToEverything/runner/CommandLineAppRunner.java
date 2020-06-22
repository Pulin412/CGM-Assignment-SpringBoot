package com.java.application.answerToEverything.runner;

import com.java.application.answerToEverything.service.IQuestionnaireService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile("!test")
@Component
public class CommandLineAppRunner implements CommandLineRunner {

    @Autowired
    private IQuestionnaireService service;

    @Override
    public void run(String... args) throws Exception {
        System.out.println(service.evaluateInput(args));
    }
}
