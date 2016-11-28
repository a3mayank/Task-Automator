package com.mayankattri.mc_project_2;

/**
 * Created by mayank on 1/10/16.
 */
public class EmailTask {
    private int id;
    private String email;
    private String subject;
    private String body;
    private String date;
    private String time;

    public EmailTask() {
    }

    public EmailTask(int id, String email, String subject, String body, String date, String time) {
        this.id = id;
        this.email = email;
        this.subject = subject;
        this.body = body;
        this.date = date;
        this.time = time;
    }

    public EmailTask(String email, String subject, String body, String date, String time) {
        this.email = email;
        this.subject = subject;
        this.body = body;
        this.date = date;
        this.time = time;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getSubject() {
        return subject;
    }

    public String getBody() {
        return body;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }
}
