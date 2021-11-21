package com.example.lv1.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lv1.R;
import com.example.lv1.models.Student;

import java.util.List;

public class StudentRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    final int STUDENT = -1;
    final int HEADER = -2;
    List<Object> dataList;

    public StudentRecyclerAdapter(List<Object> myDataset){
        dataList = myDataset;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType){
        if(viewType == STUDENT){
            View view = (View) LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.student_view_holder, viewGroup, false);
            return new StudentViewHolder(view);
        }else{
            View view = (View) LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.header_view_holder, viewGroup, false);
            return new HeaderViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position){
        if(getItemViewType(position) == STUDENT){
            StudentViewHolder studentViewHolder = (StudentViewHolder) viewHolder;
            Student student = (Student) dataList.get(position);
            studentViewHolder.tvName.setText(
                    ((Student) dataList.get(position)).getName()
            );
            studentViewHolder.tvSurname.setText(
                    ((Student) dataList.get(position)).getSurname()
            );
            studentViewHolder.tvSubject.setText(
                    ((Student) dataList.get(position)).getSubject()
            );
        }else{
            HeaderViewHolder headerViewHolder = (HeaderViewHolder) viewHolder;
            headerViewHolder.tvHeader.setText(dataList.get(position).toString());
        }
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if(dataList.get(position) instanceof Student){
            return STUDENT;
        }else{
            return HEADER;
        }
    }

    public class HeaderViewHolder extends RecyclerView.ViewHolder{
        TextView tvHeader;
        public HeaderViewHolder(@NonNull View itemView) {
            super(itemView);
            tvHeader = itemView.findViewById(R.id.header);
        }
    }
    public class StudentViewHolder extends RecyclerView.ViewHolder{
        TextView tvName;
        TextView tvSurname;
        TextView tvSubject;
        public StudentViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvImeVH);
            tvSurname = itemView.findViewById(R.id.tvPrezimeVH);
            tvSubject = itemView.findViewById(R.id.tvPredmetVH);
        }
    }

}
