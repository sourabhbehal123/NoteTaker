package com.example.hp_pc.notetaker;

import android.content.Context;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by hp-pc on 2/21/2018.
 */

public class Note implements Serializable {
    private long mDateTime;
    private String mTitle;
    private String mcontent;

    public Note(Long dateTime, String title, String mcontent) {
        mTitle = title;
        this.mcontent = mcontent;
        mDateTime = dateTime;
    }

    public void setDateTime(long dateTime) {
        mDateTime = dateTime;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public void setMcontent(String mcontent) {
        this.mcontent = mcontent;
    }

    public long getDateTime() {
        return mDateTime;
    }


    /**
     * Get date time as a formatted string
     *
     * @param context The context is used to convert the string to user set locale
     * @return String containing the date and time of the creation of the note
     */
    public String getDateTimeFormatted(Context context) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss"
                , context.getResources().getConfiguration().locale);
        formatter.setTimeZone(TimeZone.getDefault());
        return formatter.format(new Date(mDateTime));
    }


    public String getTitle() {
        return mTitle;
    }

    public String getMcontent() {
        return mcontent;
    }


}
