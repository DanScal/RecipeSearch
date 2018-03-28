package com.example.danielscal.recipesearch;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.nio.channels.Channel;
import java.util.ArrayList;

/**
 * Created by DanielScal on 3/21/18.
 */


public class Results extends AppCompatActivity {
    ListView mListView;
    Context mContext;
    Button cookIt;
    TextView found;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result);
        mContext = this;
        cookIt = findViewById(R.id.cook_it);
        found = findViewById(R.id.recipes_found);


        //data to display
        final ArrayList<Recipe> recipeList = this.getIntent().getExtras().getParcelableArrayList("fullRecipeList");

        //create the adapter
        RecipeAdapter adapter = new RecipeAdapter(this, recipeList);

        found.setText(recipeList.size() + " Recipes Found!");

        //find listview in the layout
        //set the adapter to listview
        mListView = findViewById(R.id.recipe_list_view);
        mListView.setAdapter(adapter);



    }
}

