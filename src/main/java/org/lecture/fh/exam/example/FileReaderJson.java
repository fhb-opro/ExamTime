package org.lecture.fh.exam.example;

import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.lecture.fh.exam.example.dtos.Passlevel;
import org.lecture.fh.exam.example.dtos.Question;
import org.lecture.fh.exam.example.dtos.Subject;
import org.lecture.fh.exam.example.dtos.Teacher;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class FileReaderJson {


    private static FileReaderJson instance;


    private FileReaderJson() {
        //
    }

    public static FileReaderJson getInstance() {
        if (instance == null) {
            instance = new FileReaderJson();
        }
        return instance;
    }


    public List<Teacher> getTeacher(Path path) throws IOException {
        List<Teacher> teacherList = new ArrayList<>();
        var content = new String(Files.readAllBytes(path));
        JSONArray jo = new JSONArray(content);
        for (int i = 0; i < jo.length(); i++) {
            var object = jo.getJSONObject(i);
            String subjectName = object.getString("subject");
            String teacherName = object.getString("teacherName");
            teacherList.add(new Teacher(teacherName, subjectName));
        }
        return teacherList;
    }


    public List<Subject> getSubjectQuestions(Path path, Path questionPath) throws IOException, NotEnoughQuestionsException {


        List<Subject> list = new ArrayList<>();
        var content = new String(Files.readAllBytes(path));
        JSONArray jo = new JSONArray(content);
        for (int i = 0; i < jo.length(); i++) {
            var object = jo.getJSONObject(i);
            String subjectName = object.getString("subject");
            List<Question> questionList = getQuestions(questionPath, subjectName);
            Subject subject = new Subject(subjectName, questionList);
            if (questionList.size() == 4) {
                list.add(new Subject(subjectName, questionList));
            } else if (questionList.isEmpty()) {
                log.warn("No questions for subject " + subjectName);
            } else if (questionList.size() < 4) {
                log.warn("Less than four questions for subject " + subjectName);
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

        List<Question> list = new ArrayList<>();
        var content = new String(Files.readAllBytes(path));
        JSONArray jo = new JSONArray(content);
        for (int i = 0; i < jo.length(); i++) {
            var object = jo.getJSONObject(i);
            if (subjectName.equals(object.getString("subject"))) {
                list.add(new Question(object.getString("question"), object.getBoolean("answer")));
            }
        }
        return list;
    }

    public Map<String, List<Passlevel>> getPasslevelPerSubject(Path path) throws IOException {
        Map<String, List<Passlevel>> passlevelMap = new HashMap<>();
        var content = new String(Files.readAllBytes(path));
        JSONArray jo = new JSONArray(content);
        for (int i = 0; i < jo.length(); i++) {
            var object = jo.getJSONObject(i);
            Passlevel lvl = new Passlevel(object.getString("evaluation"), object.getInt("pass level"));
            String subject = object.getString("subject");
            var passlevels = passlevelMap.getOrDefault(subject, new ArrayList<>());
            passlevels.add(lvl);
            passlevelMap.put(subject, passlevels);

        }

        return passlevelMap;

    }

    public List<String> getSubjectList(Path subjectPath) throws IOException {
        List<String> subjects = new ArrayList<>();
        var content = new String(Files.readAllBytes(subjectPath));
        JSONArray jo = new JSONArray(content);
        for (int i = 0; i < jo.length(); i++) {
            var object = jo.getJSONObject(i);
            subjects.add(object.getString("subject"));
        }
        return subjects;
    }
}


