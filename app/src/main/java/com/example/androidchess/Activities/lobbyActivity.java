package com.example.androidchess.Activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import com.example.androidchess.R;

import java.util.ArrayList;

public class lobbyActivity extends Activity {
    private static final String TAG ="MainAcitivty";

    private ArrayList<String> mNames=new ArrayList<>();
    private ArrayList<String> mImageUrls=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lobby);
        Log.d(TAG, "onCreate: started.");
        initImageBitmaps();


    }
    private void initImageBitmaps(){
        Log.d(TAG, "initImageBitmaps: preparing bitmaps.");

        mImageUrls.add("https://upload.wikimedia.org/wikipedia/commons/9/9b/Chess_bishop_0970.jpg");
        mNames.add("Lobby 1");

        mImageUrls.add("https://upload.wikimedia.org/wikipedia/commons/9/9b/Chess_bishop_0970.jpg");
        mNames.add("Lobby 2");

        mImageUrls.add("https://upload.wikimedia.org/wikipedia/commons/9/9b/Chess_bishop_0970.jpg");
        mNames.add("Lobby 3");

        mImageUrls.add("https://upload.wikimedia.org/wikipedia/commons/9/9b/Chess_bishop_0970.jpg");
        mNames.add("Lobby 4");

        mImageUrls.add("https://upload.wikimedia.org/wikipedia/commons/9/9b/Chess_bishop_0970.jpg");
        mNames.add("Lobby 5");

        initRecyclerView();
    }
    private void initRecyclerView(){
        Log.d(TAG,"initRecyclerView: init recyclerView. ");
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        RecycleViewAdapter adapter = new RecycleViewAdapter(this,mNames,mImageUrls);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }






}
