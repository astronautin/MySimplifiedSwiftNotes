package com.danni.mysimplifiedswiftnotes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuItemCompat;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.MenuInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import android.os.Environment;

import java.io.File;
import java.util.ArrayList;

import static com.danni.mysimplifiedswiftnotes.EditActivity.EXTRA_MESSAGE_HEADING;
import static com.danni.mysimplifiedswiftnotes.EditActivity.NEW_NOTE_REQUEST;
import static com.danni.mysimplifiedswiftnotes.EditActivity.NOTE_REQUEST_CODE;


public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "position";
    //public static final String EXTRA_MESSAGE_CONTENT = "theContent";

    // Layout components
    private static ListView listView;
    private ImageButton newNote;
    private TextView noNotes;
    private Toolbar toolbar;
    //private MenuItem searchMenu;

    public ArrayList<String> arrayList;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //get input from EditActivity
        Intent intent = getIntent();
        String messageFromEdit = intent.getStringExtra(EXTRA_MESSAGE_HEADING);

        //use toolbar instead of actionBar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarMain);
        setSupportActionBar(toolbar);

        arrayList = new ArrayList<>();
        String myString = "me first";
        arrayList.add(myString);

//        if (!isNullOrEmpty(messageFromEdit)){
//            arrayList.add(messageFromEdit);
//        }

        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, arrayList);

        listView = (ListView)findViewById(R.id.listView);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = listView.getItemAtPosition(position).toString();
                Intent intent = new Intent(getApplicationContext(), EditActivity.class);
                intent.putExtra(EXTRA_MESSAGE, item);
                startActivity(intent);
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                arrayList.remove(i);
                adapter.notifyDataSetChanged();
                Toast.makeText(
                        getApplicationContext(),
                        "Note has been removed.",
                        Toast.LENGTH_SHORT).show();
                return true;
            }
        });

        // If newNote button clicked -> Start EditActivity intent
        newNote = (ImageButton)findViewById(R.id.newNote);
        newNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, EditActivity.class);
//                startActivity(intent);
                intent.putExtra(NOTE_REQUEST_CODE, NEW_NOTE_REQUEST);
                startActivityForResult(intent, NEW_NOTE_REQUEST);//startActivityForResult(intent, requestCode); //ex: requestCode = 1
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {

            if (data != null) {
                String a = data.getStringExtra(EXTRA_MESSAGE_HEADING);
                arrayList.add(a);

//                listItems.add(a);
//                adapter.add(a);
//                adapter.notifyDataSetChanged();
            }

        }
//            Bundle theData = null;
//            if (data != null) {
//                theData = data.getExtras();
//            }
//            if (theData != null) {
//                if (requestCode == NEW_NOTE_REQUEST) {
//                    String a = theData.getString(EXTRA_MESSAGE_HEADING);
////                    arrayList.add(a);
//                    adapter.add(a);
////                    adapter.notifyDataSetChanged();
//
//                }
//            }
//        }
    }


    // Call Back method  to get the Message form other Activity
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data)
//    {
//        super.onActivityResult(requestCode, resultCode, data);
//        // check if the request code is same as what is passed  here it is 2
//        if(requestCode==2)
//        {
//            String message=data.getStringExtra(EXTRA_MESSAGE_HEADING);
//            //textView1.setText(message);
//
//        }
//    }

    public static boolean isNullOrEmpty(String str) {
        if(str != null && !str.isEmpty())
            return false;
        return true;
    }

}