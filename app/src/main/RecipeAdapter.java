package com.example.danielscal.recipesearch;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by DanielScal on 2/7/18.
 */

//adapter is needed when you want to do any sort of list or table view
//gets data and decides where to display it in the activity
public class RecipeAdapter extends BaseAdapter {

    //adapter takes the app itself and a list of data to display
    private Context mContext;
    private ArrayList<Recipe> mRecipeList;
    private LayoutInflater mInflater;

    //constructor
    public RecipeAdapter(Context mContext, ArrayList<Recipe> mRecipeList){
        //initialize instance variables
        this.mContext = mContext;
        this.mRecipeList = mRecipeList;
        mInflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    //list of methods we need to override

    //gives you the number of recipes in the data source
    @Override
    public int getCount(){
        return mRecipeList.size();
    }

    //returns the item at a specific position in the data source
    @Override
    public Object getItem(int position){
        return mRecipeList.get(position);
    }

    //returns the row id associated with the specific position in the list
    @Override
    public long getItemId(int position){
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        ViewHolder holder;

        //check if the view already exists
        //if yes you don't need to inflate and findViewbyID again
        if (convertView == null){
            //inflate
            convertView = mInflater.inflate(R.layout.list_item, parent, false);

            //add the views to the holder
            holder = new ViewHolder();
            //views
            holder.titleTextView = convertView.findViewById(R.id.recipe_list_title);
            holder.servingTextView = convertView.findViewById(R.id.recipe_list_serving);
            holder.thumbnailImageView = convertView.findViewById(R.id.recipe_list_thumbnail);
            holder.cookIt = convertView.findViewById(R.id.cook_it);
            holder.timeTextView = convertView.findViewById(R.id.recipe_list_time);

            //add the holder to the view
            //for future use
            convertView.setTag(holder);
        }
        else {
            //get the view holder from convertview
            holder = (ViewHolder)convertView.getTag();
        }
        //get relevant subview of the row view
        TextView titleTextView = holder.titleTextView;
        TextView servingTextView = holder.servingTextView;
        TextView timeTextView = holder.timeTextView;
        ImageView thumbnailImageView = holder.thumbnailImageView;
        Button cookIt = holder.cookIt;

        //get corresponding recipe for each row
        final Recipe recipe = (Recipe)getItem(position);

        //update the row view's textview's and imageview to display the information

        //titleTextView
        titleTextView.setText(recipe.title);
        titleTextView.setTextColor(ContextCompat.getColor(mContext, R.color.colorAccent));
        titleTextView.setTextSize(18);

        //servingTextView
        servingTextView.setText(recipe.servings + " servings");
        servingTextView.setTextColor(ContextCompat.getColor(mContext, R.color.colorPrimary));
        servingTextView.setTextSize(14);

        //timeTextView
        timeTextView.setText(recipe.prepTime);
        timeTextView.setTextColor(ContextCompat.getColor(mContext, R.color.colorPrimary));
        timeTextView.setTextSize(14);




        //thumbnailImageView
        //use picasso library to load image from the image url

        final Intent openRecipe = new Intent(Intent.ACTION_VIEW);
        openRecipe.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        openRecipe.setData(Uri.parse(recipe.instructionURL));
        final PendingIntent pendingIntent = PendingIntent.getActivity(mContext, 0, openRecipe, 0);

        final String text = ("Click here to pull up the instructions for " + recipe.title + "!");
        cookIt.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick (View view){
                NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(mContext, "channel_ID")
                        .setSmallIcon(R.drawable.ic_launcher_background)
                        .setContentTitle("Instructions!")
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                        .setContentIntent(pendingIntent)
                        .setAutoCancel(true);
                        NotificationCompat.BigTextStyle bigText = new NotificationCompat.BigTextStyle();
                        bigText.bigText(text);
                        mBuilder.setStyle(bigText);

                NotificationManagerCompat manager = NotificationManagerCompat.from(mContext);
                manager.notify((int) System.currentTimeMillis(), mBuilder.build());

            }
        });

        Picasso.with(mContext).load(recipe.imageURL).into(thumbnailImageView);
        return convertView;

    }

    //viewHolder
    //used to customize what you want to put into the view
    //depends on the layout design of your row
    //this will be a private static class you have to define
    //purpose is to show people 'these are the views I am working with'
    private static class ViewHolder{
        public TextView titleTextView;
        public TextView servingTextView;
        public ImageView thumbnailImageView;
        public Button cookIt;
        public TextView timeTextView;
    }

    //intent is used to pass information between activities
    //intent --> package
    //sender, reciever
}
