package com.ct.finalapp.ui.write;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ct.finalapp.MainActivity;
import com.ct.finalapp.R;
import com.ct.finalapp.database.Story;
import com.ct.finalapp.database.StoryRepository;

import java.util.ArrayList;
import java.util.List;

public class StoriesListFragment extends Fragment implements MyRecyclerViewAdapter.ItemClickListener {
    MyRecyclerViewAdapter adapter;
    StoryRepository mStoryRepo;

    String storyName0;
    private static int selectOpenOrDelete = 0;

    private static final int OPEN_STORY_CODE = 1;
    private static final int DELETE_STORY_CODE = 2;


    public static StoriesListFragment newInstance() {
        return new StoriesListFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_stories_list, container, false);
        mStoryRepo = StoryRepository.getInstance(getContext());


        // data to populate the RecyclerView with
        List<Story> stories = new ArrayList<>();
        stories = mStoryRepo.getStories();

        List<String> localStoryNames = new ArrayList<String>();
        int j = 0;
        while (stories.listIterator().hasNext()) {
            storyName0 = stories.get(0).getStoryName();
            localStoryNames.add(storyName0);
            stories.remove(0);
            j++;
        }

        // set up the RecyclerView
        RecyclerView recyclerView = view.findViewById(R.id.stories_list_recycle_view);
        LinearLayoutManager manager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(manager);

        adapter = new MyRecyclerViewAdapter(view.getContext(), localStoryNames);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), manager.getOrientation());
        dividerItemDecoration.setDrawable(new ColorDrawable(getResources().getColor(R.color.gray)));
        recyclerView.addItemDecoration(dividerItemDecoration);

        return view;
    }


    @Override
    public void onItemClick(View view, int position) {

        if (selectOpenOrDelete == 1) {
            MainActivity.setStoryNameStorage(mStoryRepo.getStory(adapter.getItem(position)).getStoryName());
            MainActivity.setStoryContentStorage(mStoryRepo.getStory(adapter.getItem(position)).getStoryContent());
            getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container_view, new WriteFragment())
                    .commit();

        }

        if(selectOpenOrDelete == 2) {
            getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container_view, new DeleteFragment())
                    .commit();

            DeleteFragment.deleteStoryName = mStoryRepo.getStory(adapter.getItem(position)).getStoryName();


        }

    }


    public static void setSelectUploadOrDelete(int selectUploadOrDelete) {
        StoriesListFragment.selectOpenOrDelete = selectUploadOrDelete;
    }


    public static int getSelectUploadOrDelete() {
        return selectOpenOrDelete;
    }
}