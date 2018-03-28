package com.example.danielscal.recipesearch;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.ArrayList;

/**
 * Created by DanielScal on 3/21/18.
 */

public class Search extends AppCompatActivity {
    Context mContext;
    Spinner dietSpin;
    Spinner servingSpin;
    Spinner timeSpin;
    Button search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search);
        mContext = this;

        final ArrayList<Recipe> recipeList = Recipe.getRecipesFromFile("recipes.json", this);

        dietSpin = findViewById(R.id.diet);
        servingSpin = findViewById(R.id.serving);
        timeSpin = findViewById(R.id.time);

        ArrayList<String> dietItems = new ArrayList<>();
        ArrayList<String> servingItems = new ArrayList<>();
        ArrayList<String> timeItems = new ArrayList<>();

        dietItems.add("Select One");

        for(int i = 0; i < recipeList.size(); i++){
            if(dietItems.contains(recipeList.get(i).dietLabel)){

            }
            else{
                dietItems.add(recipeList.get(i).dietLabel);
            }
        }

        servingItems.add("Select One");
        servingItems.add("Less Than 4");
        servingItems.add("4-6");
        servingItems.add("7-9");
        servingItems.add("More Than 10");

        timeItems.add("Select One");
        timeItems.add("30 Minutes or Less");
        timeItems.add("Less Than 1 Hour");
        timeItems.add("More Than 1 Hour");


        final ArrayAdapter<String> dietAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, dietItems);
        final ArrayAdapter<String> servingAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, servingItems);
        ArrayAdapter<String> timeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, timeItems);

        dietSpin.setAdapter(dietAdapter);
        servingSpin.setAdapter(servingAdapter);
        timeSpin.setAdapter(timeAdapter);

        search = findViewById(R.id.search);
        search.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                ArrayList<Recipe> spinOneList = new ArrayList<>();
                for(int i = 0; i < recipeList.size(); i++){
                    if (dietSpin.getSelectedItem().equals("Select One") || dietSpin.getSelectedItem().equals(recipeList.get(i).dietLabel)){
                        spinOneList.add(recipeList.get(i));
                    }
                }

                ArrayList<Recipe> spinTwoList = new ArrayList<>();
                for(int l = 0; l < spinOneList.size(); l++){
                    if(servingSpin.getSelectedItem().equals("Select One")){
                        spinTwoList.add(spinOneList.get(l));
                    }
                    else if (servingSpin.getSelectedItem().equals("Less Than 4") && spinOneList.get(l).servings < 4){
                        spinTwoList.add(spinOneList.get(l));
                    }
                    else if (servingSpin.getSelectedItem().equals("4-6") && spinOneList.get(l).servings >= 4 && spinOneList.get(l).servings <= 6){
                        System.out.println("within 4 to 6" + spinOneList.get(l).servings);
                        spinTwoList.add(spinOneList.get(l));
                    }
                    else if(servingSpin.getSelectedItem().equals("7-9") && spinOneList.get(l).servings >= 7 && spinOneList.get(l).servings <= 9){
                        spinTwoList.add(spinOneList.get(l));
                    }
                    else if(servingSpin.getSelectedItem().equals("More Than 10") && spinOneList.get(l).servings >= 10){
                        spinTwoList.add(spinOneList.get(l));
                    }
                }

                ArrayList<Recipe> spinThreeList = new ArrayList<>();
                for(int n = 0; n < spinTwoList.size(); n++){
                    String weirdTime = spinTwoList.get(n).prepTime;
                    String arr[] = weirdTime.split(" ");
                    if(timeSpin.getSelectedItem().equals("Select One")){
                        spinThreeList.add(spinTwoList.get(n));
                    }
                    else if(timeSpin.getSelectedItem().equals("30 Minutes or Less") && arr[1].startsWith("m") && Integer.parseInt(arr[0]) <= 30){
                        spinThreeList.add(spinTwoList.get(n));
                    }
                    else if(timeSpin.getSelectedItem().equals("Less Than 1 Hour") && arr[1].startsWith("m")){
                        spinThreeList.add(spinTwoList.get(n));
                    }
                    else if(timeSpin.getSelectedItem().equals("More Than 1 Hour") && arr[1].startsWith("h")){
                        spinThreeList.add(spinTwoList.get(n));
                    }

                }

                Intent searchIntent = new Intent(mContext, Results.class);
                searchIntent.putParcelableArrayListExtra("fullRecipeList", (ArrayList<? extends Parcelable>) spinThreeList);
                startActivity(searchIntent);
            }

        });

    }

}


