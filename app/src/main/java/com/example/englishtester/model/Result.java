package com.example.englishtester.model;

public class Result {
    private String id;
    private String type;
    private String score;

    public Result(String id, String type, String score) {
        this.id = id;
        this.type = type;
        this.score = score;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "Result{" +
                "id='" + id + '\'' +
                ", type='" + type + '\'' +
                ", score='" + score + '\'' +
                '}';
    }
}
