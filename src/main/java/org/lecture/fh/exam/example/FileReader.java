package org.lecture.fh.exam.example;

import lombok.extern.slf4j.Slf4j;
import org.lecture.fh.exam.example.dtos.Passlevel;
import org.lecture.fh.exam.example.dtos.Question;
import org.lecture.fh.exam.example.dtos.Subject;
import org.lecture.fh.exam.example.dtos.Teacher;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
public class FileReader {

    private static FileReader instance;


    private FileReader() {
        //
    }

    public static FileReader getInstance() {
        if (instance == null) {
            instance = new FileReader();
        }
        return instance;
    }


    public List<Teacher> getTeacher(Path path) throws IOException {
        List<String> allLines = Files.readAllLines(path);
        allLines.remove(0);

        List<Teacher> teacherList = new ArrayList<>();
        for (String line : allLines) {
            String[] split = line.split(",");
            String teacherName = split[0];
            String subject = split[1];

            Teacher teacher = new Teacher(teacherName, subject);
            teacherList.add(teacher);
        }

        return teacherList;
    }


    public List<Subject> getSubjectQuestions(Path path, Path questionPath) throws IOException, NotEnoughQuestionsException {
        List<String> allLines = Files.readAllLines(path);
        allLines.remove(0);

        List<Subject> list = new ArrayList<>();
        for (String line : allLines) {
            String[] split = line.split(",");  //subject,question,answer
            String subject = split[0];
            List<Question> questionList = getQuestions(questionPath, subject);
            if (questionList.size() == 4) {
                list.add(new Subject(subject, questionList));
            } else if (questionList.isEmpty()) {
                log.warn("No questions for subject " + subject);
            } else if (questionList.size() < 4) {
                log.warn("Less than four questions for subject " + subject);
            }
        }

        for (Subject subject : list) {
            if (subject.getQuestions().size() != 4) {
                throw new NotEnoughQuestionsException(subject.getName());
            }
        }

        return list;
    }

    private List<Question> getQuestions(Path path, String subjectName) throws IOException {
        List<String> allLines = Files.readAllLines(path);
        allLines.remove(0);

        List<Question> list = new ArrayList<>();

        for (String line : allLines) {

            String[] split = line.split(",");
            if (split[0].equalsIgnoreCase(subjectName)) {
                String question = split[1];
                boolean correctAnswer = Boolean.parseBoolean(split[2]);
                Question q = new Question(question, correctAnswer);
                list.add(q);
            }
        }

        return list;
    }

    public Map<String, List<Passlevel>> getPasslevelPerSubject(Path path) throws IOException {
        List<String> allLines = Files.readAllLines(path);
        allLines.remove(0);
        Map<String, List<Passlevel>> passlevelMap = new HashMap<>();

        for (String line : allLines) {
            String[] split = line.split(",");
            Passlevel p = new Passlevel(split[2], Integer.parseInt(split[1]));
            String subject = split[0];
            var passlevels = passlevelMap.getOrDefault(subject, new ArrayList<>());
            passlevels.add(p);
            passlevelMap.put(subject, passlevels);
        }
        return passlevelMap;

    }

    public List<String> getSubjectList(Path subjectPath) throws IOException {

        List<String> allLines = Files.readAllLines(subjectPath);
        allLines.remove(0);
        return allLines;
    }
}
