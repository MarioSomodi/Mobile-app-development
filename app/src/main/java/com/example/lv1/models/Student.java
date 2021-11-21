package com.example.lv1.models;

public class Student {
    private String Name;
    private String Surname;
    private String Subject;

    public Student(String name, String surname, String subject){
        Name = name;
        Surname = surname;
        Subject = subject;
    }
    public String getName(){
        return Name;
    }
    public String getSurname(){
        return Surname;
    }
    public String getSubject(){
        return Subject;
    }
}
