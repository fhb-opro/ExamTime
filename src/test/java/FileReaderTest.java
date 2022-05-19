import org.junit.jupiter.api.Test;
import org.lecture.fh.exam.example.FileReader;
import org.lecture.fh.exam.example.NotEnoughQuestionsException;
import org.lecture.fh.exam.example.dtos.Passlevel;
import org.lecture.fh.exam.example.dtos.Question;
import org.lecture.fh.exam.example.dtos.Subject;
import org.lecture.fh.exam.example.dtos.Teacher;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class FileReaderTest {


    @Test
    public void loadTeacher() throws IOException {
        FileReader instance = FileReader.getInstance();
        Path p = Paths.get("src/test/resources/teacher.csv");
        List<Teacher> teacher = instance.getTeacher(p);
        assertEquals(5, teacher.size());
    }

    @Test
    public void loadPasslevels() throws IOException {
        FileReader instance = FileReader.getInstance();
        Path p = Paths.get("src/test/resources/passlevels.csv");
        Map<String, List<Passlevel>> passlevelPerSubject = instance.getPasslevelPerSubject(p);
        assertEquals(5, passlevelPerSubject.size());
        assertFalse(passlevelPerSubject.get("OPRO").isEmpty());
        assertEquals(3, passlevelPerSubject.get("OPRO").size());
    }

    @Test
    public void loadQuestions() throws IOException, NotEnoughQuestionsException {
        FileReader instance = FileReader.getInstance();
        Path questionPath = Paths.get("src/test/resources/questions.csv");
        Path subjectPath = Paths.get("src/test/resources/subjects.csv");
        List<Subject> subjectQuestions = instance.getSubjectQuestions(subjectPath, questionPath);
        assertEquals(5, subjectQuestions.size());

        //Is polymorphism possible in Java?
        Subject subject = subjectQuestions.get(0);
        assertEquals("OPRO", subject.getName());
        Question firstQuestion = subject.getQuestions().get(0);
        assertEquals("Is polymorphism possible in Java?", firstQuestion.getQuestion());
        assertTrue(firstQuestion.isCorrectAnswer());
    }


}
