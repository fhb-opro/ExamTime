package org.lecture.fh.exam.example;

public class NotEnoughQuestionsException extends Exception {

    private final String subject;

    NotEnoughQuestionsException(String subject) {
        this.subject = subject;
    }

    @Override
    public String getMessage() {
        return "Less then four questions in subject: " + subject;
    }
}
