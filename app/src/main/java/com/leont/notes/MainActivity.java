package com.leont.notes;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
public static ArrayList<String> ar;
   public static SharedPreferences sharedPreferences;
    ListView listView;
  public static   ArrayAdapter arrayAdapter;
    Intent intent;
    String f;




    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }



    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        intent = new Intent(getApplicationContext(), Write.class);
        ar.add("");
        intent.putExtra("val",ar.size()-1);
        startActivity(intent);
        arrayAdapter.notifyDataSetChanged();
        try {
            sharedPreferences.edit().putString("ar", ObjectSerializer.serialize(ar)).apply();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return true;

        }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

/*
        SharedPreferences preferences = getSharedPreferences("com.leont.notes", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.commit();
*/

        listView=findViewById(R.id.listV);

        sharedPreferences = this.getSharedPreferences("com.leont.notes", Context.MODE_PRIVATE);

        try {
            ar = (ArrayList<String>) ObjectSerializer.deserialize(sharedPreferences.getString("ar", ObjectSerializer.serialize(new ArrayList<String>())));
        } catch (IOException e) {
            e.printStackTrace();
        }


        f=sharedPreferences.getString("f",null);



            if (ar.size()==0&&f==null) {
                f="false";
                sharedPreferences.edit().putString("f",f).apply();
            ar.add("Example note");
                arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, ar);


                listView.setAdapter(arrayAdapter);
                arrayAdapter.notifyDataSetChanged();
                try {
                    sharedPreferences.edit().putString("ar", ObjectSerializer.serialize(ar)).apply();
                } catch (IOException e) {
                    e.printStackTrace();
                }

        }

else{
                arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, ar);


                listView.setAdapter(arrayAdapter);
            }

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){
            @Override
            public boolean onItemLongClick(AdapterView<?> av, View v, int pos, long id)
            {
               final int p=pos;
                new AlertDialog.Builder(MainActivity.this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Are you sure?").setMessage("Do you want to delete this note?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ar.remove(p);
                                arrayAdapter.notifyDataSetChanged();
                                try {
                                    sharedPreferences.edit().putString("ar", ObjectSerializer.serialize(ar)).apply();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                            }
                        }).setNegativeButton("No",null)
                        .show();
               return true;
            }
        });




            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                intent = new Intent(getApplicationContext(), Write.class);
                intent.putExtra("val",position);

                startActivity(intent);


            }
            }


);}





}


