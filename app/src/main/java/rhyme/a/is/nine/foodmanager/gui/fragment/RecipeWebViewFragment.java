package rhyme.a.is.nine.foodmanager.gui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;

import rhyme.a.is.nine.foodmanager.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class RecipeWebViewFragment extends Fragment {


    public RecipeWebViewFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        /*WebView myWebView = (WebView) findViewById(R.id.webview);
        WebSettings webSettings = myWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        myWebView.loadUrl("www.google.at");*/
        return inflater.inflate(R.layout.fragment_recipe, container, false);
    }


}
