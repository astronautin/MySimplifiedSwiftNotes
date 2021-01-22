package com.danni.mysimplifiedswiftnotes;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class EditActivity extends AppCompatActivity implements Toolbar.OnMenuItemClickListener {

    public static final String EXTRA_MESSAGE_CONTENT = "theContent";
    public static final String EXTRA_MESSAGE_HEADING = "theHeading";

    static final int NEW_NOTE_REQUEST = 60000;
    static final String NOTE_REQUEST_CODE = "requestCode";


    private String[] colourArr; // Colours string array
    private int[] colourArrResId; // colourArr to resource int array
    private int[] fontSizeArr; // Font sizes int array
    private String[] fontSizeNameArr; // Font size names string array

    // Defaults
    //private String colour = "#FFFFFF"; // white default
    private int fontSize = 18; // Medium default
    //private Boolean hideBody = false;

    private AlertDialog fontDialog, saveChangesDialog;
    //private ColorPickerDialog colorPickerDialog;

    SharedPreferences myPrefs;

    EditText headingEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        fontSizeArr = new int[] {14, 18, 22}; // 0 for small, 1 for medium, 2 for large
        fontSizeNameArr = getResources().getStringArray(R.array.fontSizeNames);

        setContentView(R.layout.activity_edit);

        // Init layout components
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbarEdit);
        setSupportActionBar(toolbar);
        toolbar.setTitle(R.string.newNote); //order important!

        Intent intent = getIntent();
        //final String itemFromMainActivity = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        // Get data bundle from MainActivity
        Bundle bundle = getIntent().getExtras();
//        if (bundle.getInt(NOTE_REQUEST_CODE) == NEW_NOTE_REQUEST) {
//            headingEditText.requestFocus();
//        }

        myPrefs = getSharedPreferences("prefID", Context.MODE_PRIVATE);

//        String name = myPrefs.getString("nameKey","No name");
//        int age = myPrefs.getInt("ageKey",0);

        String heading = myPrefs.getString("headingKey","No name");
        String content = myPrefs.getString("contentKey","No name");

        Button buttonSave = (Button) findViewById(R.id.saveButton);
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get reference to TextView
                TextView label = (TextView) findViewById(R.id.labelID);

                //get references to Name and Age EditTexts
                //EditText nameEditText = (EditText) findViewById(R.id.content);
                //EditText ageEditText = (EditText) findViewById(R.id.numberID);

                headingEditText = (EditText) findViewById(R.id.heading);
                EditText contentEditText = (EditText) findViewById(R.id.content);

                //set up SharedPreferences
                myPrefs = getSharedPreferences("prefID", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = myPrefs.edit();
                //editor.putString("nameKey", nameEditText.getText().toString());
                //editor.putInt("ageKey", Integer.parseInt(ageEditText.getText().toString()));
                editor.putString("headingKey", headingEditText.getText().toString());
                editor.putString("contentKey", contentEditText.getText().toString());
                editor.apply();
                String heading = myPrefs.getString("headingKey","Default");
                label.setText("your title is " + heading);


                Intent intent1 = new Intent(getApplicationContext(), MainActivity.class);
                intent1.putExtra(EXTRA_MESSAGE_HEADING, heading);
                //startActivity(intent1);
                setResult(RESULT_OK, intent1);
                finish();
            }
        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the app bar.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return super.onCreateOptionsMenu(menu);
    }


//
//
//    public void onSave(View v) {
//
//        String title  = titleEdit.getText().toString();
//        int body  = Integer.valueOf(bodyEdit.getText().toString());
//
//        SharedPreferences.Editor editor = sp.edit();
//        editor.putString(KEY_TITLE, title);
//        editor.putInt(KEY_BODY, body);
//        editor.commit();
//
//        Toast.makeText(this, "Saved!", Toast.LENGTH_SHORT).show();
//
//    }

    /**
     * Item clicked in Toolbar menu callback method
     * @param item Item clicked
     * @return true if click detected and logic finished, false otherwise
     */
    @Override
    public boolean onMenuItemClick(MenuItem item) {
        int id = item.getItemId();
        // Font size menu item clicked -> show font picker dialog
        if (id == R.id.action_font_size) {
            fontDialog.show();
            return true;
        }

        return false;
    }



    /**
     * Check if passed EditText text is empty or not
     * @param editText The EditText widget to check
     * @return true if empty, false otherwise
     */
    protected boolean isEmpty(EditText editText) {
        return editText.getText().toString().trim().length() == 0;
    }

    /**
     * Show Toast for 'Title cannot be empty'
     */
    protected void toastEditTextCannotBeEmpty() {
        Toast toast = Toast.makeText(getApplicationContext(),
                getResources().getString(R.string.toast_edittext_cannot_be_empty),
                Toast.LENGTH_LONG);
        toast.show();
    }


    /**
     * Orientation changed callback method
     * If orientation changed -> If any AlertDialog is showing -> dismiss it to prevent WindowLeaks
     * @param newConfig Configuration passed by system
     */
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
//        if (colorPickerDialog != null && colorPickerDialog.isDialogShowing())
//            colorPickerDialog.dismiss();

        if (fontDialog != null && fontDialog.isShowing())
            fontDialog.dismiss();

        super.onConfigurationChanged(newConfig);
    }
}
