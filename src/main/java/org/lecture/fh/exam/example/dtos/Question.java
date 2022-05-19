package org.lecture.fh.exam.example.dtos;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class Question {

    private final String question;
    private final boolean correctAnswer;

    public Question(String question, boolean correctAnswer) {
        this.question = question;
        this.correctAnswer = correctAnswer;
    }

    @Override
    public String toString() {
        return question;
    }
}
