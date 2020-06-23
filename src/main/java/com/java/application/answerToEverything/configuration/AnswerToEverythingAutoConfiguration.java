package com.java.application.answerToEverything.configuration;

import com.java.application.answerToEverything.repository.QuestionRepository;
import com.java.application.answerToEverything.service.IQuestionnaireService;
import com.java.application.answerToEverything.service.QuestionnaireServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnClass(IQuestionnaireService.class)
public class AnswerToEverythingAutoConfiguration {

    @Autowired
    QuestionRepository questionRepository;

    @Bean
    @ConditionalOnMissingBean
    public IQuestionnaireService questionnaireService(){
        return new QuestionnaireServiceImpl(questionRepository);
    }
}
