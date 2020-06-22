package com.java.application.answerToEverything.repository;

import com.java.application.answerToEverything.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface QuestionRepository extends JpaRepository<Question, Integer> {
    Optional<Question> findByQuestion(String question);
}
