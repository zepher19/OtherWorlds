package com.ct.finalapp.ui.write;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.ct.finalapp.MainActivity;
import com.ct.finalapp.R;
import com.ct.finalapp.database.Story;
import com.ct.finalapp.database.StoryRepository;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;


public class WriteFragment extends Fragment {

    private static final int OPEN_STORY_CODE = 1;
    private static final int DELETE_STORY_CODE = 2;

    private static final int CREATE_REQUEST_CODE = 40;
    private static final int SAVE_REQUEST_CODE = 41;
    private EditText wordEditor;

    public static WriteFragment newInstance() {
        return new WriteFragment();
    }

    private Button saveStoryButton;
    private Button openStory;
    private Button uploadButton;
    private EditText titleEditText;
    private StoryRepository mStoryRepo;
    private Button deleteButton;

    public List<Story> getStories() {
        return mStoryRepo.getStories();
    }

    public void addStory(Story story) {
        mStoryRepo.addStory(story);
    }

    public int updateStory(Story story) {
        return mStoryRepo.updateStory(story);
    }

    public int deleteStory(Story story) {
        return mStoryRepo.deleteStory(story);
    }



    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        mStoryRepo = StoryRepository.getInstance(getContext());
        View view = inflater.inflate(R.layout.fragment_write, container, false);
        titleEditText = view.findViewById(R.id.title_edit_text);
        saveStoryButton = view.findViewById(R.id.save_story_button);
        deleteButton = view.findViewById(R.id.button_delete_story);
        openStory = view.findViewById(R.id.open_story_button);
        uploadButton = view.findViewById(R.id.upload_story_button);
        wordEditor = (EditText) view.findViewById(R.id.edit_text_multiline);
        wordEditor.setText(MainActivity.getStoryContentStorage());
        titleEditText.setText(MainActivity.getStoryNameStorage());

        titleEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                MainActivity.setStoryNameStorage(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        wordEditor.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                MainActivity.setStoryContentStorage(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });


        saveStoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (titleEditText.getText().toString().length() >= 1) {
                    Story localStory = new Story();
                    String localStoryName = titleEditText.getText().toString();
                    String localStoryContent = wordEditor.getText().toString();

                    localStory.setStoryName(localStoryName);
                    localStory.setStoryContent(localStoryContent);

                    mStoryRepo.addStory(localStory);
                    Toast.makeText(getContext(), "Story Saved", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getContext(), "Story Must Have a Title to Save", Toast.LENGTH_SHORT).show();
                }

            }
        });


        openStory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                StoriesListFragment.setSelectUploadOrDelete(OPEN_STORY_CODE);

                Fragment frag = new StoriesListFragment();

                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container_view, frag)
                        .addToBackStack(null)
                        .commit();
            }
        });


        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newFile(uploadButton);
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteEntry();

            }
        });



        return view;
    }

    private void deleteEntry() {
        StoriesListFragment.setSelectUploadOrDelete(DELETE_STORY_CODE);
        Fragment frag = new StoriesListFragment();

        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container_view, frag)
                .commit();
    }


    public void newFile(View view)
    {
        if (titleEditText.getText().toString().equals("")) {
            Toast.makeText(getContext(), "Please Name your Story to Continue", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = new Intent(Intent.ACTION_CREATE_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TITLE, MainActivity.getStoryNameStorage() +".txt");

        startActivityForResult(intent, CREATE_REQUEST_CODE);
    }


    public void onActivityResult(int requestCode, int resultCode,
                                 Intent resultData) {

        super.onActivityResult(requestCode, resultCode, resultData);
        Uri currentUri = null;

        if (requestCode == CREATE_REQUEST_CODE) {
            currentUri = resultData.getData();
            writeFileContent(currentUri);
        }
    }


    private void writeFileContent(Uri uri)
    {
        try{
            ParcelFileDescriptor pfd = getContext().getContentResolver().openFileDescriptor(uri, "w");

            FileOutputStream fileOutputStream = new FileOutputStream(pfd.getFileDescriptor());

            String textContent = MainActivity.getStoryContentStorage();

            fileOutputStream.write(textContent.getBytes());

            fileOutputStream.close();
            pfd.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
