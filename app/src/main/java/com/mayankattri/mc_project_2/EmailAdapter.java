package com.mayankattri.mc_project_2;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.List;

/**
 * Created by mayank on 31/10/16.
 */
public class EmailAdapter extends RecyclerView.Adapter<EmailAdapter.MyViewHolder> {

    private List<EmailTask> emailTaskList;

    public EmailAdapter(List<EmailTask> emailTaskList) {
        this.emailTaskList = emailTaskList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView email, subject, body, date, time;
//        public CheckBox cb;

        public MyViewHolder(View view) {
            super(view);
            email = (TextView) view.findViewById(R.id.TV_email);
            subject = (TextView) view.findViewById(R.id.TV_subject);
            body = (TextView) view.findViewById(R.id.TV_body);
            date = (TextView) view.findViewById(R.id.TV_date);
            time = (TextView) view.findViewById(R.id.TV_time);
//            cb = (CheckBox) itemView.findViewById(R.id.CB);

//            cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                @Override
//                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                    if(b){
//                        System.out.println("checked");
//                    }
//                }
//            });
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_email, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        EmailTask task = emailTaskList.get(position);

        holder.email.setText(task.getEmail());
        holder.subject.setText(task.getSubject());
        holder.body.setText(task.getBody());
        holder.date.setText(task.getDate());
        holder.time.setText(task.getTime());
//        boolean status;
//        status = note.getStatus() != 0;
//        holder.cb.setChecked(status);
    }

    @Override
    public int getItemCount() {
        return emailTaskList.size();
    }
}
