import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.lecture.fh.exam.example.Exam;
import org.lecture.fh.exam.example.dtos.Passlevel;
import org.lecture.fh.exam.example.dtos.Question;
import org.lecture.fh.exam.example.dtos.Subject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ExamTest {

    private final ByteArrayOutputStream bo = new ByteArrayOutputStream();

    private Subject subject;
    private Question question;
    private Exam exam;

    @BeforeEach
    public void setUp() {
        List<Question> questionList = new ArrayList<>();
        subject = new Subject("OPRO", questionList);
        question = new Question("Is polymorphism possible in Java?", true);
        Question question2 = new Question("Collections can hold primitives.", false);
        Question question3 = new Question("After an error the application can be continued", false);
        Question question4 = new Question("Does Java allow multiple inheritance?", false);
        questionList.add(question);
        questionList.add(question2);
        questionList.add(question3);
        questionList.add(question4);
        exam = new Exam("Barbara", subject);
        exam.getGivenAnswers().put(question, true);
        exam.getGivenAnswers().put(question2, true);
        exam.getGivenAnswers().put(question3, false);
        exam.getGivenAnswers().put(question4, false);


        System.setOut(new PrintStream(bo, true));
    }

    @Test
    public void askQuestions() {
        String input = "true" +
                System.lineSeparator() +
                "false" +
                System.lineSeparator() +
                "false" +
                System.lineSeparator() +
                "false";
        System.setIn(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)));
        Scanner scanner = new Scanner(System.in);

        exam.askQuestions(scanner);
        Map<Question, Boolean> givenAnswers = exam.getGivenAnswers();
        Boolean answer = givenAnswers.get(question);
        assertTrue(answer);
        System.out.println();
        String[] split = bo.toString().split(System.lineSeparator());

        assertEquals("Is polymorphism possible in Java?", split[1]);
        assertEquals("Is the answer to this question true or false?", split[2]);
        assertEquals("Collections can hold primitives.", split[3]);


    }

    @Test
    public void askQuestionsWrongInput() {
        String input = "asdf" +
                System.lineSeparator() +
                "true" +
                System.lineSeparator() +
                "false" +
                System.lineSeparator() +
                "false" +
                System.lineSeparator() +
                "false";
        System.setIn(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)));
        Scanner scanner = new Scanner(System.in);

        exam.askQuestions(scanner);
        Map<Question, Boolean> givenAnswers = exam.getGivenAnswers();
        Boolean answer = givenAnswers.get(question);
        assertTrue(answer);
        String s = bo.toString();
        System.out.println();
        String[] split = s.split(System.lineSeparator());

        assertEquals("Is polymorphism possible in Java?", split[1]);
        assertEquals("Is the answer to this question true or false?", split[2]);
        assertEquals("Please enter true or false", split[3]);
        assertEquals("Collections can hold primitives.", split[5]);


    }

    @Test
    public void evaluateExam() {
        Passlevel lvPassed = new Passlevel("passed", 60);
        Passlevel lvSuccess = new Passlevel("success", 80);
        Passlevel lvAward = new Passlevel("award", 90);


        List<Passlevel> passlevelList = new ArrayList<>();
        passlevelList.add(lvPassed);
        passlevelList.add(lvSuccess);
        passlevelList.add(lvAward);

        Map<String, List<Passlevel>> passLevelMap = new HashMap<>();
        passLevelMap.put(subject.getName(), passlevelList);

        exam.evaluate(passLevelMap);

        String s = bo.toString();
        String[] split = s.split(System.lineSeparator());
        assertEquals(exam.getStudentName() + ", your passgrade is 75 points, you finished the exam with \"passed\" (needed minimum points were 60).", split[1]);


    }


    @AfterEach
    public void tearDown() {
        System.setIn(System.in);
        System.setOut(System.out);
    }


}
