package org.lecture.fh.exam.example;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.lecture.fh.exam.example.dtos.Passlevel;
import org.lecture.fh.exam.example.dtos.Subject;
import org.lecture.fh.exam.example.dtos.Teacher;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@Getter
public class Configuration {
    private static final Path teacherPath = Paths.get("src/main/resources/teacher.json");
    private static final Path subjectPath = Paths.get("src/main/resources/subjects.json");
    private static final Path questionPath = Paths.get("src/main/resources/questions.json");
    private static final Path passlevelPath = Paths.get("src/main/resources/passlevels.json");
    private final Map<String, List<Passlevel>> passLevel;  //Subject Name, Passlvl
    private final List<Teacher> teacher;
    private final List<Subject> subjectQuestions = new ArrayList<>();
    private final List<String> availableSubjects = new ArrayList<>();

    public Configuration() throws IOException {
        log.trace("Reading files.");

        FileReaderJson fileReader = FileReaderJson.getInstance();

        try {
            this.availableSubjects.addAll(fileReader.getSubjectList(subjectPath));
            this.subjectQuestions.addAll(fileReader.getSubjectQuestions(subjectPath, questionPath));
        } catch (NotEnoughQuestionsException e) {
            System.err.println("Incorrect configuration found. Please provide more questions");
            log.error(e.getMessage());
        }
        this.teacher = fileReader.getTeacher(teacherPath);
        this.passLevel = fileReader.getPasslevelPerSubject(passlevelPath);
        log.trace("Finished reading files.");

    }

    /**
     * Return the teacher for the given subject
     *
     * @param subject the list of subjets
     * @return found teacher or null if not found
     */
    public Teacher getTeacher(String subject) {

        Teacher examiner = null;

        for (Teacher t : this.teacher) {
            if (subject.equals(t.getSubject())) {
                examiner = t;
                break;
            }
        }
        return examiner;
    }
}
