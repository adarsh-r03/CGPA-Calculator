package com.example.cgpacalculator.model;

import java.util.ArrayList;
import java.util.List;

public class Student {
    private String enrollmentNo;
    private String name;
    private List<Subject> subjects;
    private double cgpa;

    public Student() {
        this.subjects = new ArrayList<>();
    }

    public String getEnrollmentNo() {
        return enrollmentNo;
    }

    public void setEnrollmentNo(String enrollmentNo) {
        this.enrollmentNo = enrollmentNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Subject> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<Subject> subjects) {
        this.subjects = subjects;
    }

    public double getCgpa() {
        return cgpa;
    }

    public void setCgpa(double cgpa) {
        this.cgpa = cgpa;
    }

    public void calculateCGPA() {
        if (subjects.isEmpty()) {
            this.cgpa = 0.0;
            return;
        }
        double total = 0;
        for (Subject subject : subjects) {
            total += subject.getMarks() / 10; // Convert marks out of 100 to 10-point scale
        }
        this.cgpa = total / subjects.size();
    }
}