package com.ct.finalapp.database;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Story {
    @PrimaryKey @NonNull
    public String storyName;

    @ColumnInfo
    public String storyContent;


    public void setStoryName(String storyName) {
        this.storyName = storyName;
    }

    public void setStoryContent(String storyContent) {
        this.storyContent = storyContent;
    }

    @NonNull
    public String getStoryName() {
        return storyName;
    }

    public String getStoryContent() {
        return storyContent;
    }
}
