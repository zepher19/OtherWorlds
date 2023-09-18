package com.ct.finalapp.ui.home;

import static android.content.ContentValues.TAG;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.IntentSenderRequest;
import androidx.activity.result.contract.ActivityResultContracts;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ct.finalapp.MainActivity;
import com.ct.finalapp.R;
import com.google.android.gms.auth.api.identity.GetSignInIntentRequest;
import com.google.android.gms.auth.api.identity.Identity;
import com.google.android.gms.auth.api.identity.SignInCredential;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.Scope;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.json.gson.GsonFactory;

import java.util.Collections;
import java.util.Objects;
import java.util.zip.Inflater;

public class HomeFragment extends Fragment {


    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    private static boolean loggedIn = false;
    private SignInButton signInButton;
    private EditText appDescription;
    private TextView welcomeMessage;
    private View view;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_home, container, false);
        welcomeMessage = view.findViewById(R.id.welcome_message);
        signInButton = view.findViewById(R.id.sign_in_button);
        appDescription = view.findViewById(R.id.editTextTextMultiLine_app_explanation);

        chooseLayout();

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });

        return view;
    }



    private static final int REQUEST_CODE_GOOGLE_SIGN_IN = 1; /* unique request id */

    private void signIn() {
        GetSignInIntentRequest request =
                GetSignInIntentRequest.builder()
                        .setServerClientId(getString(R.string.server_client_id))
                        .build();


        Identity.getSignInClient(getActivity())
                .getSignInIntent(request)
                .addOnSuccessListener(
                        result -> {
                            try {
                                startIntentSenderForResult(
                                        result.getIntentSender(),
                                        REQUEST_CODE_GOOGLE_SIGN_IN,
                                        /* fillInIntent= */ null,
                                        /* flagsMask= */ 0,
                                        /* flagsValue= */ 0,
                                        /* extraFlags= */ 0,
                                        /* options= */ null);

                            } catch (IntentSender.SendIntentException e) {
                                Log.e(TAG, "Google Sign-in failed");
                            }
                        })
                .addOnFailureListener(
                        e -> {
                            Log.e(TAG, "Google Sign-in failed", e);
                        });
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        loggedIn = true;
        chooseLayout();


        if(resultCode == Activity.RESULT_OK) {

            if (requestCode == REQUEST_CODE_GOOGLE_SIGN_IN) {


                try {
                    SignInCredential credential = Identity.getSignInClient(getActivity()).getSignInCredentialFromIntent(data);

                    // Signed in successfully - show authenticated UI
                    //updateUI(credential);
                } catch (ApiException e) {
                    // The ApiException status code indicates the detailed failure reason.
                }
            }
        }
    }


    private ActivityResultLauncher<IntentSenderRequest> loginResultHandler = registerForActivityResult(new ActivityResultContracts.StartIntentSenderForResult(), result -> {
        // handle intent result here
    });


    private void chooseLayout() {
        if (!loggedIn) {
            MainActivity.bottomNav.setVisibility(view.GONE);
            appDescription.setEnabled(false);

        }
        else {
            signInButton.setVisibility(View.GONE);
            welcomeMessage.setText(getString(R.string.signed_in));
            appDescription.setEnabled(true);
            appDescription.setText(getString(R.string.zoom_around));
            appDescription.setEnabled(false);
            MainActivity.bottomNav.setVisibility(view.VISIBLE);
        }

    }


}