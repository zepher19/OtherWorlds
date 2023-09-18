package com.ct.finalapp.ui.read;

import static android.content.Context.DOWNLOAD_SERVICE;

import android.app.AlertDialog;
import android.app.DownloadManager;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.DownloadListener;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.ct.finalapp.MainActivity;
import com.ct.finalapp.R;

public class ReadFragment extends Fragment {


    public static ReadFragment newInstance() {
        return new ReadFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_read, container, false);



        // initialising the web view
        WebView wv = (WebView) view.findViewById(R.id.webview);

        wv.loadUrl("https://drive.google.com/drive/folders/1e3SyM7oQBRpB5qmygKcN79Bhjz-dn8u-?usp=drive_link");
        wv.setWebViewClient(new Client());
        WebSettings ws = wv.getSettings();

        // Enabling javascript
        ws.setJavaScriptEnabled(true);
        wv.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        wv.clearCache(true);
        wv.clearHistory();

        // download manager is a service that can be used to handle downloads
        wv.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String url, String s1, String s2, String s3, long l) {
                DownloadManager.Request req = new DownloadManager.Request(Uri.parse(url));
                req.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                DownloadManager dm = (DownloadManager) getActivity().getSystemService(DOWNLOAD_SERVICE);
                dm.enqueue(req);
                Toast.makeText(getContext(), "Downloading....", Toast.LENGTH_SHORT).show();
            }
        });




        return view;
    }


    private class Client extends WebViewClient {
        // on page started load start loading the url
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }

        // load the url of our drive
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
        }

        public void onReceivedError(WebView webView, int errorCode, String description, String failingUrl) {
            // if stop loading
            try {
                webView.stopLoading();
            } catch (Exception e) {
            }

            if (webView.canGoBack()) {
                webView.goBack();
            }

            // if loaded blank then show error
            // to check internet connection using
            // alert dialog
            webView.loadUrl("about:blank");
            AlertDialog alertDialog = new AlertDialog.Builder(getContext()).create();
            alertDialog.setTitle("Error");
            alertDialog.setMessage("Check your internet connection and Try again.");
            alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "Try Again", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    getActivity().finish();
                    startActivity(getActivity().getIntent());
                }
            });

            alertDialog.show();
            super.onReceivedError(webView, errorCode, description, failingUrl);
        }
    }


}