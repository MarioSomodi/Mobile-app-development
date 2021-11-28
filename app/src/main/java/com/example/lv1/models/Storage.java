package com.example.lv1.models;

import android.graphics.Bitmap;

import com.example.lv1.viewModels.StudentVM;

import java.util.ArrayList;
import java.util.List;

public class Storage {
    private List<Object> dataList;
    public Bitmap profileImage;
    public boolean rotation;
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
    public void setProfileImage(Bitmap img, boolean rot){
        profileImage = img;
        rotation = rot;
    }
    public void addStudent(StudentVM student){
        this.dataList.add(student);
    }
    public List<Object> getStudents(){
        return dataList;
    }
}
