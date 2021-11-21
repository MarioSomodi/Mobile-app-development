package com.example.lv1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.lv1.adapter.StudentRecyclerAdapter;
import com.example.lv1.models.Storage;
import com.example.lv1.models.Student;

import java.util.List;

public class HomePageActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        mRecyclerView = (RecyclerView) findViewById(R.id.RecyclerViewF);

        Storage StorageSingleton = Storage.getInstance();
        List<Object> students = StorageSingleton.getStudents();
        Student s = new Student("", "", "Trenutno nema studenata");
        if(students.size() == 0){
            students.add("Studenti");
            students.add(s);
        }else{
            Student a = (Student) students.get(1);
            if(a.getSubject() == "Trenutno nema studenata")
                students.remove(1);
        }

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new StudentRecyclerAdapter(students);
        mRecyclerView.setAdapter(mAdapter);
    }

    public void OpenAddNewStudent(View view){
        Intent AddNewStudentAct = new Intent(HomePageActivity.this, AddStudentActivity.class);
        startActivity(AddNewStudentAct);
    }
}