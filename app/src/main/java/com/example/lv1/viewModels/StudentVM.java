package com.example.lv1.viewModels;

public class StudentVM {
    private String name;
    private String surname;
    private String subject;

    public StudentVM(String Name, String Surname, String Subject){
        name = Name;
        surname = Surname;
        subject = Subject;
    }

    public String getName(){
        return name;
    }
    public String getSurname(){
        return surname;
    }
    public String getSubject(){
        return subject;
    }
}
