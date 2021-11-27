package com.example.lv1.models;

public class Course {
    public String subject = null;
    public String proffesor = null;
    public String academicYear = null;
    public String labHours = null;
    public String lectureHours = null;

    public Course(){}

    public Course(String Subject, String Professor, String AcademicYear, String LabHours, String LectureHours){
        subject = Subject;
        proffesor = Professor;
        academicYear = AcademicYear;
        labHours = LabHours;
        lectureHours = LectureHours;
    }
}
