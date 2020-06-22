package com.java.application.answerToEverything.service;

import com.java.application.answerToEverything.entity.Answer;
import com.java.application.answerToEverything.entity.Question;
import com.java.application.answerToEverything.repository.QuestionRepository;
import com.java.application.answerToEverything.util.AppUtil;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.logging.Logger;

@Service
public class QuestionnaireServiceImpl implements IQuestionnaireService {

    private static Logger log = Logger.getLogger(QuestionnaireServiceImpl.class.getName());
    private QuestionRepository questionRepository;

    public QuestionnaireServiceImpl(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    @Override
    /*
        Main method to process the input and return appropriate response.
     */
    public String evaluateInput(String[] input){

        log.info("ANSWER-TO-EVERYTHING :::::: received input");
        /*
            Checking if the input is not null or empty i.e. without any question.
         */
        if(input == null || input.length == 0){
            log.warning("ANSWER-TO-EVERYTHING :::::: Null or empty input.");
            return "Please enter question..";
        }

        int questionIndex = 0;
        boolean isValidQuestion = false;
        StringBuilder questionBuilder = new StringBuilder();

        /*
            Logic to extract the question from the input -
                - Loop through all the incoming input arguments.
                - Save all the strings into a StringBuilder to form the question.
                - As soon as '?' comes, break the loop and use the created string builder as question.
         */
        for (String word : input) {
            questionBuilder.append(word).append(" ");
            if(word.contains("?")){
                isValidQuestion = true;
                break;
            }
            questionIndex++;
        }
        String question = questionBuilder.toString().trim();
        log.info("ANSWER-TO-EVERYTHING :::::: incoming question - " + question);
        /*
            validate the question for the length of the incoming question, should be less than 255 characters.
            If its not validated, throw an IllegalArgumentException with proper message string.
         */
        if(AppUtil.isQuestionValid(isValidQuestion, question)){
            /*
                check if only question is received or answers are also there in the input.
             */
            if(input.length == questionIndex + 1){
                /*
                    If only question is there, find the answers from the Database and print them in seperated lines.
                    If question is not present in the database, return with the generic answer.
                    If question is present in the database but there are no answers saved, return message to the user - "No answers saved.."
                 */
                Optional<Question> answerObj = questionRepository.findByQuestion(question);
                if(answerObj.isPresent()){
                    log.info("ANSWER-TO-EVERYTHING :::::: fetching answers for --  " + question);
                    Set<Answer> fromDbAnswers = answerObj.get().getAnswers();
                    if(fromDbAnswers != null && fromDbAnswers.size() > 0){
                        fromDbAnswers.forEach(answer -> System.out.println("- " + answer.getAnswer()));
                        return "";
                    }else{
                        return "No answers saved..";
                    }
                }else{
                    return "the answer to life, universe and everything is 42";
                }
            }else{
                /*
                    If question is there with answers as well, save the question in the database.
                 */
                return addQuestion(input, questionIndex, question);
            }
        }else{
            throw new IllegalArgumentException("Question is invalid");
        }
    }

    private String addQuestion(String[] input, int questionIndex, String question) {
        log.info("ANSWER-TO-EVERYTHING :::::: saving --  " + question);
        Optional<Question> questionInDb = questionRepository.findByQuestion(question);
        if(questionInDb.isPresent()) {
            log.warning("ANSWER-TO-EVERYTHING :::::: Question is already present.");
            return "Question is already present.";
        }
        Question savedQuestion = questionRepository.save(AppUtil.questionMapper(input, questionIndex, question));
        return savedQuestion != null ? "question added successfully" : "Issue in saving question, try again!";
    }

}
