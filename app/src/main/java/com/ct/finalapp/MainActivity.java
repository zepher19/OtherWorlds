package com.ct.finalapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.splashscreen.SplashScreen;
import androidx.fragment.app.Fragment;

import com.ct.finalapp.ui.home.HomeFragment;
import com.ct.finalapp.ui.read.ReadFragment;
import com.ct.finalapp.ui.write.WriteFragment;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    private Fragment selectedFragment;
    private static String storyContentStorage;
    private static String storyNameStorage;

    public static BottomNavigationView bottomNav;

    public static void setStoryContentStorage(String storyContentStorage) {
        MainActivity.storyContentStorage = storyContentStorage;
    }

    public static String getStoryContentStorage() {
        return storyContentStorage;
    }

    public static void setStoryNameStorage(String storyNameStorage) {
        MainActivity.storyNameStorage = storyNameStorage;
    }

    public static String getStoryNameStorage() {
        return storyNameStorage;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SplashScreen splashScreen = SplashScreen.installSplashScreen(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNav = findViewById(R.id.nav_view);

        bottomNav.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemID = item.getItemId();

                if (itemID == R.id.navigation_home) {
                    selectedFragment = new HomeFragment();
                }
                else if (itemID == R.id.navigation_read) {
                    selectedFragment = new ReadFragment();
                }
                else if (itemID == R.id.navigation_write) {
                    selectedFragment = new WriteFragment();
                }

                if (selectedFragment != null) {
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragment_container_view, selectedFragment)
                            .addToBackStack(null)
                            .commit();
                }
                return true;
            }
        });
    }
}