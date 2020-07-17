package com.example.englishtester.model;

public class Answer {
    private String idAnswer;
    private String answer;

    public Answer(String idAnswer, String answer) {
        this.idAnswer = idAnswer;
        this.answer = answer;
    }

    public String getIdAnswer() {
        return idAnswer;
    }

    @Override
    public String toString() {
        return "Answer{" +
                "idAnswer='" + idAnswer + '\'' +
                ", answer='" + answer + '\'' +
                '}';
    }

    public void setIdAnswer(String idAnswer) {
        this.idAnswer = idAnswer;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
