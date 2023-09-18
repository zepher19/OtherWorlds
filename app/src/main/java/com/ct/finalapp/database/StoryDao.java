package com.ct.finalapp.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface StoryDao {
    @Query("SELECT * FROM Story")
    List<Story> getStories();

    @Query("SELECT * FROM story WHERE storyname IN (:storyNames)")
    List<Story> loadAllByNames(String[] storyNames);


    @Query("SELECT * FROM story WHERE storyname LIKE :storyName")
    Story getStory(String storyName);



    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addStory(Story story);

    @Delete
    int deleteStory(Story story);

    @Update
    int updateStory(Story story);
}
