package com.example.hp_pc.notetaker;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**
 * Created by hp-pc on 3/2/2018.
 */

public class utilities {
    public static final String FILE_EXTENSION = ".bin";


    public static Boolean saveNote(Context context, Note note) {
        String fileName = String.valueOf(note.getDateTime()) + FILE_EXTENSION;
        FileOutputStream fos;
        ObjectOutputStream oos;
        try {
            fos = context.openFileOutput(fileName, context.MODE_PRIVATE);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(note);
            fos.close();
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }


    /**
     * Read all saved
     * @param context Application's context
     * @return ArrayList of Note
     */
    static ArrayList<Note> getAllSavedNotes(Context context) {
        ArrayList<Note> notes = new ArrayList<>();

        File filesDir = context.getFilesDir();
        ArrayList<String> noteFiles = new ArrayList<>();

        //add .bin files to the noteFiles list
        for(String file : filesDir.list()) {
            if(file.endsWith(FILE_EXTENSION)) {
                noteFiles.add(file);
            }
        }

        //read objects and add to list of notes
        FileInputStream fis;
        ObjectInputStream ois;

        for (int i = 0; i < noteFiles.size(); i++) {
            try{
                fis = context.openFileInput(noteFiles.get(i));
                ois = new ObjectInputStream(fis);

                notes.add((Note) ois.readObject());
                fis.close();
                ois.close();

            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
                return null;
            }
        }

        return notes;
    }

    public static Note getNoteByFileName(Context context, String fileName) {

        File file = new File(context.getFilesDir(), fileName);
        if(file.exists() && !file.isDirectory()) { //check if file actually exist

            Log.v("UTILITIES", "File exist = " + fileName);

            FileInputStream fis;
            ObjectInputStream ois;

            try { //load the file
                fis = context.openFileInput(fileName);
                ois = new ObjectInputStream(fis);
                Note note = (Note) ois.readObject();
                fis.close();
                ois.close();

                return note;

            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
                return null;
            }

        } else {
            return null;
        }
    }


    public static void deleteNote(Context context, String fileName) {

        File dir=context.getFilesDir();
        File file=new File(dir,fileName);
        if (file.exists()){
            file.delete();
        }
    }
}
