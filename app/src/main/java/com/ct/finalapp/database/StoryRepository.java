package com.ct.finalapp.database;

import android.content.Context;
import android.widget.Toast;

import androidx.room.Room;

import java.util.List;

public class StoryRepository {
    private static StoryRepository mStoryRepo;
    private final StoryDao mStoryDao;

    public static StoryRepository getInstance(Context context) {
        if (mStoryRepo == null) {
            mStoryRepo = new StoryRepository(context);
        }
        return mStoryRepo;
    }

    private StoryRepository(Context context) {
        StoryDatabase database = Room.databaseBuilder(context, StoryDatabase.class, "story.db")
                .allowMainThreadQueries()
                .build();

        mStoryDao = database.storyDao();
    }

    public void addStory(Story story) {
        mStoryDao.addStory(story);
    }


    public List<Story> getStories() {
        return mStoryDao.getStories();
    }

    public Story getStory(String storyName) {
        return mStoryDao.getStory(storyName);
    }

    /*
    public List<String> getTitles() {
        List<Story> stories = getStories();

        List<String> titles = null;

        return titles;
    }

     */


    public int deleteStory(Story story) {
        return mStoryDao.deleteStory(story);
    }

    public int updateStory(Story story) {
        return mStoryDao.updateStory(story);
    }
}
