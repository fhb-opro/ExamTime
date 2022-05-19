package org.lecture.fh.exam.example;

import lombok.Getter;
import org.lecture.fh.exam.example.dtos.Passlevel;
import org.lecture.fh.exam.example.dtos.Question;
import org.lecture.fh.exam.example.dtos.Subject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

@Getter
public class Exam implements Examinable {

    private final String studentName;
    private final Subject subject;
    private final Map<Question, Boolean> givenAnswers;


    public Exam(String studentName, Subject subject) {
        this.studentName = studentName;
        this.subject = subject;
        this.givenAnswers = new HashMap<>();
    }

    /**
     * evaluates the answers given in the exam, counting each answer as 25% of the passgrade.
     *
     * @param passLevel the available passlevels for the subject name
     */
    @Override
    public void evaluate(Map<String, List<Passlevel>> passLevel) {
        // without passgrades

        int correctAnswers = 0;
        for (Question q : givenAnswers.keySet()) {
            if (q.isCorrectAnswer() == givenAnswers.get(q)) {
                correctAnswers++;
            }
        }

/* without passgrade
        if (correctAnswers >= 2) {
            System.out.printf("You passed %s with %d points. Congratulations!", subject.getName(), correctAnswers);
        } else {
            System.out.printf("You failed %s with %d points. Good luck next time!", subject.getName(), correctAnswers);

        }

 */
        System.out.println(studentName + ", let's grade your " + subject.getName() + " exam");
        List<Passlevel> passlevels = passLevel.get(subject.getName());
        Passlevel foundGrade = new Passlevel("failed", 0);
        int points = 25 * correctAnswers;
        Passlevel nextPass = new Passlevel("All answers were correct", 100);
        for (Passlevel lvl : passlevels) {
            //do this, as long as there are correct answers need to be considered.
            if (correctAnswers >= 0) {
                //is the passgrade <= the achieved points?
                if (lvl.getPassgrade() <= points) {
                    foundGrade = lvl;
                    //decrement, as this correct answer has now been taken into account
                    correctAnswers--;
                } else {
                    nextPass = lvl;
                }
            }
        }
        if (nextPass.getPassgrade() == 100) {
            System.out.printf("%s, your passgrade is %s percent, you finished the exam with \"%s\".%n", studentName, points, foundGrade.getLevel());
        } else {
            System.out.printf("%s, your passgrade is %s percent, you finished the exam with \"%s\" (needed " + " for" + " next level:  %s).%n", studentName, points, foundGrade.getLevel(), nextPass.getPassgrade());
        }
    }

    /**
     * Ask the answers for all the existing questions the subject.
     */
    @Override
    public void askQuestions(Scanner scanner) {
        System.out.println(studentName + ", let's start your exam");
        for (Question question : subject.getQuestions()) {
            System.out.println(question);
            do {
                System.out.println("Is the answer to this question true or false?");
                var answer = scanner.nextLine();
                if (answer.equalsIgnoreCase("true") || answer.equalsIgnoreCase("false")) {
                    this.givenAnswers.put(question, Boolean.parseBoolean(answer.toLowerCase()));
                    break;
                } else {
                    System.out.println("Please enter true or false");
                }
            } while (true);
        }
    }
}
