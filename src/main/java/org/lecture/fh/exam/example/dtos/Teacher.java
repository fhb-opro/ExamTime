package org.lecture.fh.exam.example.dtos;

import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class Teacher {
    private final String subject;
    private final String name;

    public Teacher(String teacherName, String subject) {
        this.name = teacherName;
        this.subject = subject;
    }

}
