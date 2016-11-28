package com.mayankattri.mc_project_2;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EmailActivity extends AppCompatActivity {

    GmailSender sender;
    private String MESSAGE = this.getClass().getSimpleName();
    static List<EmailTask> emailTaskList = new ArrayList<>();
    private RecyclerView recyclerView;
    public static EmailAdapter mAdapter;
    public LinearLayout v;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        DBHandler db = new DBHandler(this);

        // Reading all notes
        Log.d("Reading: ", "Reading all notes");
        List<EmailTask> emailTasks = db.getAllEmailTask();

        emailTaskList = emailTasks;
        v = (LinearLayout) findViewById(R.id.view);

        for (EmailTask n : emailTasks) {
            String log = "ID.: " + n.getId() + "\nEmail: " + n.getEmail() + "\nSubject: "
                    + n.getSubject() + "\nBody: " + n.getBody() + "\nDate: " + n.getDate() +
                    "\nTime: " + n.getTime();
            Log.d("EmailTasks : ", log);
        }

        recyclerView = (RecyclerView) findViewById(R.id.RV_email);
        mAdapter = new EmailAdapter(emailTasks);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this.getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
    }

    public void execute(String taskstr) {
        // Add your mail Id and Password
        sender = new GmailSender("mayank14063@iiitd.ac.in", "");

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.
                Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);

        MyAsyncClass task = new MyAsyncClass();
        task.execute(taskstr);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_email, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            sender = new GmailSender("mayank14063@iiitd.ac.in", "629265028874");
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.
                    Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            MyAsyncClass emailTask = new MyAsyncClass();
            emailTask.execute();
            return true;
        }
        if (id == R.id.MI_add) {
            AddEmailTaskFragment d = new AddEmailTaskFragment();
            d.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    Log.d("EmailActivity : ", "Dialog Dismissed");
//                    Intent intent = getIntent();
//                    finish();
//                    startActivity(intent);
                }
            });
            d.show(getSupportFragmentManager(), "AddEmailTaskFragment");
        }

        return super.onOptionsItemSelected(item);
    }

    class MyAsyncClass extends AsyncTask<String, Void, Void> {

        ProgressDialog pDialog;

//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//            pDialog = new ProgressDialog(EmailActivity.this);
//            pDialog.setMessage("Please wait...");
//            pDialog.show();
//        }

        @Override
        protected Void doInBackground(String... mApi) {
            try {
                // Add subject, Body, your mail Id, and receiver mail Id.
                // sendTo in last
                String[] emailFields = mApi[0].split("```");
                String userEmail = "mayank14063@iiitd.ac.in";
                System.out.println(emailFields[0]);
                System.out.println(emailFields[1]);
                System.out.println(emailFields[2]);
                sender.sendMail(emailFields[1], emailFields[2], userEmail, emailFields[0]);
//                sender.sendMail("MC Project", " Task Automator", "mayank14063@iiitd.ac.in", "a3mayank@gmail.com");
            }
            catch (Exception ex) {

            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
//            pDialog.cancel();
//            Toast.makeText(getApplicationContext(), "Email send", Toast.LENGTH_SHORT).show();
        }
    }

    public static class AddEmailTaskFragment extends DialogFragment {

        public static String hour;
        public static String minute;
        public static String year;
        public static String month;
        public static String day;
        public static String time;
        public static String date;

        public AddEmailTaskFragment() {
        }

        private DialogInterface.OnDismissListener onDismissListener;

        public void setOnDismissListener(DialogInterface.OnDismissListener onDismissListener) {
            this.onDismissListener = onDismissListener;
        }

        @Override
        public void onDismiss(DialogInterface dialog) {
            super.onDismiss(dialog);
            if (onDismissListener != null) {
                onDismissListener.onDismiss(dialog);
            }
        }

        @NonNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {

            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("Add an Email Task");

            // Get the layout inflater
            LayoutInflater inflater = getActivity().getLayoutInflater();

            // Inflate and set the layout for the dialog
            // Pass null as the parent view because its going in the dialog layout
            builder.setView(inflater.inflate(R.layout.dialog_add_emailtask, null));
            // Add action buttons
            builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int id) {
                    addEmailTask();
                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    AddEmailTaskFragment.this.getDialog().cancel();
                }
            });

            return builder.create();
        }

        public void addEmailTask() {

            EditText et_email = (EditText) getDialog().findViewById(R.id.ET_email);
            EditText et_subject = (EditText) getDialog().findViewById(R.id.ET_subject);
            EditText et_body = (EditText) getDialog().findViewById(R.id.ET_body);
            EditText et_day = (EditText) getDialog().findViewById(R.id.ET_day);
            EditText et_month = (EditText) getDialog().findViewById(R.id.ET_month);
            EditText et_year = (EditText) getDialog().findViewById(R.id.ET_year);
            EditText et_hour = (EditText) getDialog().findViewById(R.id.ET_hour);
            EditText et_minute = (EditText) getDialog().findViewById(R.id.ET_minute);

            String email = "" + et_email.getText().toString();
            String subject = "" +  et_subject.getText().toString();
            String body = "" +  et_body.getText().toString();
            day = "" +  et_day.getText().toString();
            month = "" +  et_month.getText().toString();
            year = "" +  et_year.getText().toString();
            hour = "" +  et_hour.getText().toString();
            minute = "" +  et_minute.getText().toString();

            time = hour+":"+minute;
            date = day+"-"+month+"-"+year;
            String dt = year+" "+month+" "+day+" "+hour+" "+minute;
            String taskStr = email+"```"+subject+"```"+body;

            if (!email.equals("") && !subject.equals("") && !body.equals("") && !day.equals("")
                    && !month.equals("") && !year.equals("") && !hour.equals("") && !minute.equals("")) {
                DBHandler db = new DBHandler(getActivity());

                EmailTask task = new EmailTask(email, subject, body, date, time);
                db.addEmailTask(task);
                EmailActivity.emailTaskList.add(task);
                EmailActivity.mAdapter.notifyDataSetChanged();

                Receiver emailReceiver = new Receiver();
                emailReceiver.setAlarmEmail(getActivity(), dt, taskStr);

                Toast.makeText(getActivity().getBaseContext(), "Added", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
