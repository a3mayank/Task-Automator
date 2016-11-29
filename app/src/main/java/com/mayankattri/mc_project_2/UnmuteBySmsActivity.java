package com.mayankattri.mc_project_2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

public class UnmuteBySmsActivity extends AppCompatActivity {

    ArrayAdapter<String> adapterUnmute;
    public static ArrayList<String> unmuteContacts;
    EditText ET_contact;
    public static SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unmute_by_sms);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ListView listView = (ListView) findViewById(R.id.listView_unmute);
        Button B_Add = (Button) findViewById(R.id.B_unmuteAdd);
        ET_contact = (EditText) findViewById(R.id.ET_unmuteContact);
        unmuteContacts = new ArrayList<>();
        prefs = PreferenceManager.getDefaultSharedPreferences(this);

        if (prefs != null) {
            Map<String,?> keys = prefs.getAll();
            for (Map.Entry<String, ?> entry : keys.entrySet()) {
                Log.d("map values", entry.getKey() + ": " +
                        entry.getValue().toString());
                unmuteContacts.add(entry.getValue().toString());
            }
        }

        adapterUnmute = new ArrayAdapter<>(this, R.layout.list_item_unmute, R.id.text_view_unmute, unmuteContacts);
        listView.setAdapter(adapterUnmute);

        B_Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String C = "+91" + ET_contact.getText().toString();
                if (!C.equals("")) {
                    adapterUnmute.add(C);
                    adapterUnmute.notifyDataSetChanged();
                    unmuteContacts.add(C);
                    prefs.edit().putString(C, C).apply();
                    ET_contact.setText("");
                }
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                TextView tv = (TextView) view.findViewById(R.id.text_view_unmute);
                System.out.println(tv.getText());
                unmuteContacts.remove(tv.getText().toString());
                adapterUnmute.remove(tv.getText().toString());
                prefs.edit().remove(tv.getText().toString()).apply();
                return false;
            }
        });
    }

}
