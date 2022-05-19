package org.lecture.fh.exam.example;

import org.lecture.fh.exam.example.dtos.Passlevel;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

public interface Examinable {
    void evaluate(Map<String, List<Passlevel>> passLevel);
    void askQuestions(Scanner scanner);
}
