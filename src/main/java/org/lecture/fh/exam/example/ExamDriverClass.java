package org.lecture.fh.exam.example;

import lombok.extern.slf4j.Slf4j;
import org.lecture.fh.exam.example.dtos.Subject;
import org.lecture.fh.exam.example.dtos.Teacher;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

@Slf4j
public class ExamDriverClass {


    public static void main(String[] args) throws IOException {

        String anotherStudent = "y";

        do {
            Configuration config = new Configuration();
            Scanner scanner = new Scanner(System.in);
            String name = login(scanner);

            int amountOfExams = 2;
            List<Examinable> exams = new ArrayList<>();  //all taken exams
            Subject alreadyTakenSubject = null;
            List<String> availableSubjects = config.getAvailableSubjects();



            for (int i = 1; i <= amountOfExams; i++) {
                printSubjects(availableSubjects);
                System.out.println("\nWhich subject would you like to take?");
                String chosenSubject = scanner.nextLine();
                Subject subject = null;
                for (Subject s : config.getSubjectQuestions()) {
                    if (chosenSubject.equals(s.getName())) {
                        log.debug("Chosen Subject: " + s.getName());
                        subject = s;
                        if (i == 1) {
                            alreadyTakenSubject = subject;
                        }

                    }
                }

                if (subject == null) {
                    System.err.println("Subject not found or has no questions assigned.");
                    availableSubjects.remove(chosenSubject);
                    i--;
                    continue;
                }

                Teacher examiner = config.getTeacher(subject.getName());
                if (i > 1 && Objects.equals(alreadyTakenSubject, subject)) {
                    System.err.println("You have already taken this subject. Please choose another one");
                    availableSubjects.remove(alreadyTakenSubject);
                    i--;  //decrement index variable to enable another subject.
                    continue;
                }


                if (examiner != null) {
                    System.out.println("Teacher " + examiner.getName() + " enters the room.");
                    Examinable e = new Exam(name, subject);  //start new exam for a student in a subject.
                    exams.add(e);  //adds exam to taken exams
                    // in order to test this method it is necessary to pass the scanner, so that we can mock it in the testcases.
                    e.askQuestions(scanner);
                    if (i < amountOfExams) {
                        System.out.println("Would you like to take another exam? (y/n)");
                        var anotherExam = scanner.nextLine();
                        if (!anotherExam.equalsIgnoreCase("y")) {
                            break;
                        }
                    }
                } else {
                    System.err.println("Currently, there is no teacher for this subject. Choose another subject");
                    log.error("Currently, there is no teacher for this subject. Choose another subject");
                    availableSubjects.remove(subject);
                    i--; //decrement index variable to enable another subject
                }
            }

            //evaluate exams
            exams.forEach(e -> e.evaluate(config.getPassLevel()));

            anotherStudent = continueApplication(scanner);
        } while (anotherStudent.equals("y"));

    }

    private static String continueApplication(Scanner scanner) {
        String anotherStudent;
        System.out.println();
        System.out.println();
        System.out.println();

        System.out.println("Does another student want to take an exam? y/n");
        anotherStudent = scanner.nextLine().toLowerCase();
        return anotherStudent;
    }

    private static String login(Scanner scanner) {
        System.out.println("Please enter your first name");
        String firstName = scanner.nextLine();
        System.out.println("Please enter your last name");
        String lastName = scanner.nextLine();
        String name = firstName + " " + lastName;

        log.debug("Student " + name + " logged in");
        System.out.println("Welcome to your Examination.");
        return name;
    }

    private static void printSubjects(List<String> availableSubjects) {
        System.out.println("Available Subjects: ");
        availableSubjects.forEach(System.out::println);
    }


}
