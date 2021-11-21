package com.example.lv1.models;

import java.util.ArrayList;
import java.util.List;

public class Storage {
    private List<Object> dataList;
    static private Storage instance;
    private Storage(){
        dataList = new ArrayList<Object>();
    }
    public static Storage getInstance(){
        if(instance == null){
            instance = new Storage();
        }
        return instance;
    }

    public void addStudent(Student student){
        this.dataList.add(student);
    }
    public List<Object> getStudents(){
        return dataList;
    }
}
