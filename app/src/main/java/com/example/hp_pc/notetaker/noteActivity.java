package com.example.hp_pc.notetaker;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

public class noteActivity extends AppCompatActivity {
    public EditText mEditTitle;
    public EditText mEditcontent;

    private String mFileName;
    private Note mLoadedNote;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        mEditTitle = (EditText) findViewById(R.id.note_title);
        mEditcontent = (EditText) findViewById(R.id.note_content);

        mFileName = getIntent().getStringExtra("NOTE_FILE");
        if(mFileName != null && !mFileName.isEmpty() && mFileName.endsWith(utilities.FILE_EXTENSION)) {
            mLoadedNote = utilities.getNoteByFileName(getApplicationContext(), mFileName);
            if (mLoadedNote != null) {
                //update the widgets from the loaded note
                mEditTitle.setText(mLoadedNote.getTitle());
                mEditcontent.setText(mLoadedNote.getMcontent());

            }
        }
        }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_new_saveitem, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.main_note_action_save:
                saveNote();
            case R.id.main_note_action_delete:
                    deleteNote();
                break;
        }
        return true;
    }


    private void saveNote() {
        Note note;
        if (mEditTitle.getText().toString().trim().isEmpty()||mEditcontent.getText().toString().isEmpty()){
            Toast.makeText(this,"please enter a title and a content",Toast.LENGTH_SHORT).show();
            return;

        }
                if(mLoadedNote==null){
         note = new Note(System.currentTimeMillis(), mEditTitle.getText().toString()
                , mEditcontent.getText().toString());}
        else {
                    note=new Note(mLoadedNote.getDateTime(),mEditTitle.getText().toString()
                    ,mEditcontent.getText().toString());
                }
        if (utilities.saveNote(this, note)) {
            Toast.makeText(this, "your note is saved!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "your notes is notsaved", Toast.LENGTH_SHORT).show();
        }
        finish();
    }
    private void deleteNote() {
         if (mLoadedNote==null){
                finish();
         }
        else {
             AlertDialog.Builder dialog=new AlertDialog.Builder(this)
                     .setTitle("DELETE")
                      .setMessage("You are about to delete "+ mEditTitle.getText().toString()+",are you sure?")
                     .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                         @Override
                         public void onClick(DialogInterface dialog, int which) {
                             utilities.deleteNote(getApplication(),mLoadedNote.getDateTime()+utilities.FILE_EXTENSION);
                             Toast.makeText(getApplicationContext(),mEditTitle.getText().toString()+"is deleted",Toast.LENGTH_SHORT).show();
                             finish();
                         }
                     })
                     .setNegativeButton("No",null)
                     .setCancelable(false);
             dialog.show();



         }
    }

}
