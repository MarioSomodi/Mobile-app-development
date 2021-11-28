package com.example.lv1.viewModels;

import android.content.ClipData;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.util.Pair;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.lv1.R;
import com.example.lv1.fragments.SummaryFragment;
import com.example.lv1.models.Course;
import com.example.lv1.models.Storage;
import com.example.lv1.models.Student;

import java.util.List;

public class SummaryViewModel extends ViewModel {

    public MutableLiveData<Integer> change = new MutableLiveData<>();
    private MutableLiveData<Student> student = new MutableLiveData<>();
    private MutableLiveData<Course> course = new MutableLiveData<>();
    public MutableLiveData<Bitmap> profileImage = new MutableLiveData<>();
    public MutableLiveData<Boolean> rotation = new MutableLiveData<>();

    public SummaryViewModel(){
        Student s = new Student();
        Course c = new Course();
        student.setValue(s);
        course.setValue(c);
        change.setValue(0);
    }

    public void setStudentProperty(String value, int propertyIndex) {
        Student studentObj = student.getValue();
        if(propertyIndex == 1){
            studentObj.name = value;
        }else if(propertyIndex == 2){
            studentObj.surname = value;
        }else{
            studentObj.birthday = value;
        }
        student.setValue(studentObj);
        change.setValue(1);
    }
    public void setProfileImage(Bitmap img, boolean rot){
        rotation.setValue(rot);
        profileImage.setValue(img);
    }

    public void setCourseProperty(String value, int propertyIndex) {
        Course courseObj = course.getValue();
        if(propertyIndex == 1){
            courseObj.subject = value;
        }else if(propertyIndex == 2){
            courseObj.proffesor = value;
        }else if (propertyIndex == 3){
            courseObj.academicYear = value;
        } else if (propertyIndex == 4){
            courseObj.labHours = value;
        } else if (propertyIndex == 5){
            courseObj.lectureHours = value;
        }
        course.setValue(courseObj);
        change.setValue(2);
    }
    public Student getStudent(){
        return student.getValue();
    }
    public Course getCourse(){
        return course.getValue();
    }
}
