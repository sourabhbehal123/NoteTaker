package com.example.hp_pc.notetaker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mListView = (ListView) findViewById(R.id.main_listview_notes);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.main_note_action:
                startActivity(new Intent(this, noteActivity.class));
                break;
        }
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        mListView.setAdapter(null);
        ArrayList<Note> notes = utilities.getAllSavedNotes(this);

        if (notes == null && notes.size() == 0) { //check if we have any notes!
            Toast.makeText(this, "you have no saved note", Toast.LENGTH_SHORT).show();
            return;
        } else {
            final NoteAdapter na = new NoteAdapter(this, R.layout.item_note, notes);
            mListView.setAdapter(na);
            mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    //run the NoteActivity in view/edit mode
                    String fileName = ((Note) mListView.getItemAtPosition(position)).getDateTime()
                            + utilities.FILE_EXTENSION;
                    Intent viewNoteIntent = new Intent(getApplicationContext(), noteActivity.class);
                    viewNoteIntent.putExtra("NOTE_FILE", fileName);
                    startActivity(viewNoteIntent);

                }


            });
        }
    }
}