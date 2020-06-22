package com.java.application.answerToEverything.utils;

import com.java.application.answerToEverything.entity.Answer;
import com.java.application.answerToEverything.entity.Question;

import java.util.HashSet;
import java.util.Set;

public class TestUtils {

    public static String[] generate_InvalidQuestion_Input(){
        return new String[] {"LoremipsumdolorsitametconsectetueradipiscingelitAeneancommodoligulaegetdolorAeneanassaCumsociisnatoqueLoremipsumdolorsitametconsectetueradipiscingelitAeneancommodoligulaegetdolorAeneanassaCumsociisnatoqueLoremipsumdolorsitametconsectetueradipiscingelitAeneancommodoligulaegetdolorAeneanassaCumsociisnatoque?"};
    }

    public static String[] generate_QuestionWithoutQuestionMark_Input(){
        return new String[] {"Lorue"};
    }

    public static String[] generate_InvalidAnswer_Input(){
        return new String[] {"Question?", "answer1", "LoremipsumdolorsitametconsectetueradipiscingelitAeneancommodoligulaegetdolorAeneanassaCumsociisnatoqueLoremipsumdolorsitametconsectetueradipiscingelitAeneancommodoligulaegetdolorAeneanassaCumsociisnatoqueLoremipsumdolorsitametconsectetueradipiscingelitAeneancommodoligulaegetdolorAeneanassaCumsociisnatoque?"};
    }

    public static String[] generate_AskQuestion_Input(){
        return new String[] {"What is Peters favorite food?"};
    }

    public static String[] generate_ValidQuestionWithAnswers_Input(){
        return new String[] {"What is Peters favorite food?", "Pizza" ,"Spaghetti", "Ice cream"};
    }

    public static Question getSavedQuestion(){
        Question question = new Question();
        question.setQuestionId(1);
        question.setQuestion("What is Peters favorite food?");
        Set<Answer> answerSet = new HashSet<>();
        answerSet.add(new Answer(100,"Pizza"));
        answerSet.add(new Answer(100,"Ice cream"));
        answerSet.add(new Answer(100,"Spaghetti"));
        question.setAnswers(answerSet);
        return question;
    }
}
