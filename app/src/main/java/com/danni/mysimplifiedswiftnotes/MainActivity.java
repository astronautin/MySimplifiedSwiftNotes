package com.danni.mysimplifiedswiftnotes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuItemCompat;

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
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import android.os.Environment;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity  implements AdapterView.OnItemClickListener,
        Toolbar.OnMenuItemClickListener{

    // Layout components
    private static ListView listView;
    private ImageButton newNote;
    private TextView noNotes;
    private Toolbar toolbar;
    private MenuItem searchMenu;

    private static NoteAdapter adapter; // Custom ListView notes adapter

    // Array of selected positions for deletion
    public static ArrayList<Integer> checkedArray = new ArrayList<Integer>();
    public static boolean deleteActive = false; // True if delete mode is active, false otherwise

    TextView textView;
    String[] listItem;

    public static ArrayList notes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Init notes array
        notes = new ArrayList();

        setContentView(R.layout.activity_main);

        //use toolbar instead of actionBar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarMain);
        setSupportActionBar(toolbar);

        listView = (ListView)findViewById(R.id.listView);
        newNote = (ImageButton)findViewById(R.id.newNote);
        noNotes = (TextView)findViewById(R.id.noNotes);

        // Initialize NoteAdapter with notes array
        adapter = new NoteAdapter(getApplicationContext(), notes);
        listView.setAdapter(adapter);


        // Set item click, multi choice and scroll listeners
        listView.setOnItemClickListener(this);

        // If newNote button clicked -> Start EditActivity intent with NEW_NOTE_REQUEST as request
        newNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, EditActivity.class);
                startActivity(intent);
            }
        });

    }

    /**
     * Favourite or un-favourite the note at position
     * @param context application context
     * @param favourite true to favourite, false to un-favourite
     * @param position position of note
     */
    public static void setFavourite(Context context, boolean favourite, int position) {

    }

    /**
     * If item clicked in list view -> Start EditActivity intent with position as requestCode
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this, EditActivity.class);
        intent.putExtra(EditActivity.EXTRA_MESSAGE, position);
        startActivity(intent);
    }


    /**
     * Item clicked in Toolbar menu callback method
     * @param menuItem Item clicked
     * @return true if click detected and logic finished, false otherwise
     */
//   @Override
//    public boolean onMenuItemClick(MenuItem menuItem) {
//        int id = menuItem.getItemId();
//        return false;
//    }


    /**
     * Callback method when 'Delete' icon pressed
     * @param mode ActionMode of selection
     * @param item MenuItem clicked, in our case just action_delete
     * @return true if clicked, false otherwise
     */
    //@Override
    public boolean onActionItemClicked(final ActionMode mode, MenuItem item) {
        return false;
    }


    // Long click detected on ListView item -> start selection ActionMode (delete mode)
    //@Override
    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
        mode.getMenuInflater().inflate(R.menu.menu_delete, menu); // Inflate 'menu_delete' menu
        deleteActive = true; // Set deleteActive to true as we entered delete mode
        //newNoteButtonVisibility(false); // Hide newNote button
        adapter.notifyDataSetChanged(); // Notify adapter to hide favourite buttons

        return true;
    }

    // Selection ActionMode finished (delete mode ended)
    //@Override
    public void onDestroyActionMode(ActionMode mode) {
    }

    //@Override
    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
        return false;
    }


    //@Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    //@Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        return false;
    }
}