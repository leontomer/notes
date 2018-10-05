package com.leont.notes;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import java.io.IOException;

public class Write extends AppCompatActivity {
 EditText w;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);
        w=findViewById(R.id.editT);
        final Intent intent=getIntent();
        w.setText(MainActivity.ar.get(intent.getIntExtra("val",0)));
        w.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void afterTextChanged(Editable mEdit)
            {

              //  w.setText(mEdit.toString());
                MainActivity.ar.set(intent.getIntExtra("val",0),mEdit.toString());

                MainActivity.arrayAdapter.notifyDataSetChanged();
                try {
                    MainActivity.sharedPreferences.edit().putString("ar", ObjectSerializer.serialize(MainActivity.ar)).apply();
                } catch (IOException e) {
                    e.printStackTrace();
                }

;
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after){}

            public void onTextChanged(CharSequence s, int start, int before, int count){


            }
        });




    }









}

