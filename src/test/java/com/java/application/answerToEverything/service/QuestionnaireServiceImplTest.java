package com.java.application.answerToEverything.service;

import com.java.application.answerToEverything.entity.Question;
import com.java.application.answerToEverything.repository.QuestionRepository;
import com.java.application.answerToEverything.utils.TestUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

@ActiveProfiles("test")
@SpringBootTest
public class QuestionnaireServiceImplTest {

    @MockBean
    private QuestionRepository questionRepository;

    @Autowired
    private QuestionnaireServiceImpl questionnaireService;

    /*
        Positive scenario test cases -
            1. If valid question with answers is passed as an argument.
            2. If any new question is asked which is not stored in the system already.
            3. If any old question is asked which is already stored in the system.
     */
    @Test
    void test_when_validQuestionWithAnswers_then_QuestionAdded(){
        Mockito.when(questionRepository.save(Mockito.any(Question.class))).thenReturn(new Question());
        Assertions.assertEquals("question added successfully",questionnaireService.evaluateInput(TestUtils.generate_ValidQuestionWithAnswers_Input()));
    }

    @Test
    void test_when_AskNewQuestion_then_PredefinedAnswerShouldBePrinted(){
        Mockito.when(questionRepository.findByQuestion(Mockito.any(String.class))).thenReturn(Optional.empty());
        Assertions.assertEquals("the answer to life, universe and everything is 42",questionnaireService.evaluateInput(TestUtils.generate_AskQuestion_Input()));
    }

    @Test
    void test_when_AskOldQuestion_then_SavedAnswerShouldBePrinted(){
        Mockito.when(questionRepository.findByQuestion(Mockito.any(String.class))).thenReturn(Optional.of(TestUtils.getSavedQuestion()));
        Assertions.assertEquals("",questionnaireService.evaluateInput(TestUtils.generate_AskQuestion_Input()));
    }

    /*
        Negative scenario test cases -
            1. If null is passed as an argument.
            2. If any question with more than 255 character is passed as an argument.
            3. If any answer with more than 255 character is passed as an argument.
            4. If any question is passed in wrong format i.e. without '?'.
            5. If same question is saved again with the exact same string.
     */
    @Test
    void test_when_NoArgumentsPassed_then_ErrorMessageShouldGetPrinted(){
        Assertions.assertEquals("Please enter question..",questionnaireService.evaluateInput(null));
    }

    @Test
    void test_when_QuestionLengthMoreThan255_then_ThrowException(){
        Exception exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            questionnaireService.evaluateInput(TestUtils.generate_InvalidQuestion_Input());
        });
        Assertions.assertTrue(exception.getMessage().contains("Length of Question exceeds 255 characters"));
    }

    @Test
    void test_when_AnswerLengthMoreThan255_then_ThrowException(){
        Exception exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            questionnaireService.evaluateInput(TestUtils.generate_InvalidAnswer_Input());
        });
        Assertions.assertTrue(exception.getMessage().contains("Length of Answer exceeds 255 characters"));
    }

    @Test
    void test_when_QuestionWithWrongFormat_then_ThrowException(){
        Exception exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            questionnaireService.evaluateInput(TestUtils.generate_QuestionWithoutQuestionMark_Input());
        });
        Assertions.assertTrue(exception.getMessage().contains("Question format is incorrect"));
    }

    @Test
    void test_when_SameQuestionTriedToBeSavedAgain_then_ErrorMessage(){
        Mockito.when(questionRepository.findByQuestion(Mockito.any(String.class))).thenReturn(Optional.of(TestUtils.getSavedQuestion()));
        Assertions.assertEquals("Question is already present.",questionnaireService.evaluateInput(TestUtils.generate_ValidQuestionWithAnswers_Input()));
    }
}
