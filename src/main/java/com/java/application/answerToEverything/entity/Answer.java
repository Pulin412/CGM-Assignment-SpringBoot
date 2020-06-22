package com.java.application.answerToEverything.entity;

import javax.persistence.*;

@Entity
@Table(name = "answers")
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer answerId;

    @Column(length = 255)
    private String answer;

    public Answer() {
    }

    public Answer(Integer answerId, String answer) {
        this.answerId = answerId;
        this.answer = answer;
    }

    @Override
    public String toString() {
        return "Answer [id=" + answerId + ", answer=" + answer + "]";
    }

    public Integer getAnswerId() {
        return answerId;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswerId(Integer answerId) {
        this.answerId = answerId;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
