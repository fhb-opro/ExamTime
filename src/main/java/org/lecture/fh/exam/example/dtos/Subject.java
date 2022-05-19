package org.lecture.fh.exam.example.dtos;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@EqualsAndHashCode
@ToString
public class Subject {
    private final String name;
    private final List<Question> questions;

    public Subject(String subject, List<Question> questionList) {
        this.name = subject;
        this.questions = questionList;
    }
}
