package com.zespolowka.forms;

import com.zespolowka.entity.createTest.ProgrammingLanguages;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;
import java.util.TreeSet;

public class TaskForm {
    public static final int CLOSEDTASK = 0;
    public static final int OPENTASK = 1;
    public static final int PROGRAMMINGTASK = 2;
    private static final Logger logger = LoggerFactory.getLogger(TaskForm.class);

    private String question;

    private String answer;

    private int taskType;

    private Integer points = 1;

    private Set<String> languages = new TreeSet<>();

    private Set<ProgrammingTaskForm> programmingTaskForms;

    private Boolean caseSensitivity;

    private Boolean wrongReset = true;

    private Boolean countNotFull;


    public TaskForm() {
        programmingTaskForms = new TreeSet<>();
    }

    public TaskForm(int taskType) {
        logger.info("TaskForm1");
        this.taskType = taskType;
        programmingTaskForms = new TreeSet<>();
        if (taskType == 2) {
            programmingTaskForms.add(new ProgrammingTaskForm(ProgrammingLanguages.JAVA.toString()));
            programmingTaskForms.add(new ProgrammingTaskForm(ProgrammingLanguages.CPP.toString()));
            programmingTaskForms.add(new ProgrammingTaskForm(ProgrammingLanguages.PYTHON.toString()));
        }
    }

    public TaskForm(String question, int taskType, String answer) {
        this.question = question;
        this.taskType = taskType;
        this.answer = answer;
    }

    public TaskForm(String question, int taskType) {
        this.question = question;
        this.answer = "";
        this.taskType = taskType;
    }

    public TaskForm(String question, String answer, int taskType) {
        this.question = question;
        this.answer = answer;
        this.taskType = taskType;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        logger.info("setQuestion");
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        logger.info("setAnswer");
        this.answer = answer;
    }

    public Set<String> getLanguages() {
        return languages;
    }

    public void setLanguages(Set<String> languages) {
        logger.info("setLanguages");
        if (languages == null) {
            logger.info("pusto?");
            this.languages = new TreeSet<>();
        } else this.languages = languages;
        Set<ProgrammingTaskForm> programmingTaskFormSet = new TreeSet<>();
        logger.info("aaaa" + this.languages.contains(ProgrammingLanguages.JAVA.toString()));
        if (this.languages.contains(ProgrammingLanguages.JAVA.toString())) {
            programmingTaskFormSet.add(new ProgrammingTaskForm(ProgrammingLanguages.JAVA.toString(), true));
        } else {
            programmingTaskFormSet.add(new ProgrammingTaskForm(ProgrammingLanguages.JAVA.toString()));
        }
        if (this.languages.contains(ProgrammingLanguages.CPP.toString())) {
            programmingTaskFormSet.add(new ProgrammingTaskForm(ProgrammingLanguages.CPP.toString(), true));
        } else {
            programmingTaskFormSet.add(new ProgrammingTaskForm(ProgrammingLanguages.CPP.toString()));
        }
        if (this.languages.contains(ProgrammingLanguages.PYTHON.toString())) {
            programmingTaskFormSet.add(new ProgrammingTaskForm(ProgrammingLanguages.PYTHON.toString(), true));
        } else {
            programmingTaskFormSet.add(new ProgrammingTaskForm(ProgrammingLanguages.PYTHON.toString()));
        }
        setProgrammingTaskForms(programmingTaskFormSet);
    }

    public Set<ProgrammingTaskForm> getProgrammingTaskForms() {
        if (programmingTaskForms.size() == 0) {
            programmingTaskForms.add(new ProgrammingTaskForm(ProgrammingLanguages.JAVA.toString()));
            programmingTaskForms.add(new ProgrammingTaskForm(ProgrammingLanguages.CPP.toString()));
            programmingTaskForms.add(new ProgrammingTaskForm(ProgrammingLanguages.PYTHON.toString()));
        }
        return programmingTaskForms;
    }

    public void setProgrammingTaskForms(Set<ProgrammingTaskForm> programmingTaskForms) {
        logger.info("setProgrammingTaskForms");
        if (programmingTaskForms == null) {
            this.programmingTaskForms = new TreeSet<>();
        } else this.programmingTaskForms = programmingTaskForms;
    }

    public int getTaskType() {
        return taskType;
    }

    public void setTaskType(int taskType) {
        logger.info("setTaskType");
        this.taskType = taskType;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public Boolean getCaseSensitivity() {
        return caseSensitivity;
    }

    public void setCaseSensitivity(Boolean caseSensitivity) {
        this.caseSensitivity = caseSensitivity;
    }

    public Boolean getWrongReset() {
        return wrongReset;
    }

    public void setWrongReset(Boolean wrongReset) {
        this.wrongReset = wrongReset;
    }

    public Boolean getCountNotFull() {
        return countNotFull;
    }

    public void setCountNotFull(Boolean countNotFull) {
        this.countNotFull = countNotFull;
    }

    @Override
    public String toString() {
        return "TaskForm{" +
                "question='" + question + '\'' +
                ", answer='" + answer + '\'' +
                ", taskType=" + taskType +
                ", points=" + points +
                ", languages=" + languages +
                ", programmingTaskForms=" + programmingTaskForms +
                ", caseSensitivity=" + caseSensitivity +
                ", wrongReset=" + wrongReset +
                ", countNotFull=" + countNotFull +
                '}';
    }
}
