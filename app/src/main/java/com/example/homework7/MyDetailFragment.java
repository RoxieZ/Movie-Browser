package com.example.homework7;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.ShareActionProvider;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.HashMap;

/**
 * Created by kevin on 6/2/14.
 */
public class MyDetailFragment extends Fragment {
    private static final String ARG_MOVIE= "movie";
    private static HashMap<String,?> movie;
    private ShareActionProvider mShareActionProvider;
    private Intent intentShare;

    public static MyDetailFragment newInstance(HashMap<String,?> movie) {
        MyDetailFragment fragment = new MyDetailFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_MOVIE, movie);
        fragment.setArguments(args);
        return fragment;
    }

    public MyDetailFragment() {
    }
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        if(getArguments() != null)
            movie = (HashMap<String,?>)getArguments().getSerializable(ARG_MOVIE);
        setHasOptionsMenu(true);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View rootView = inflater.inflate(R.layout.fragment_detailview, container, false);



        TextView name = (TextView) rootView.findViewById(R.id.name);
        TextView year = (TextView) rootView.findViewById(R.id.year);
        TextView rating = (TextView) rootView.findViewById(R.id.rating);
        TextView length = (TextView) rootView.findViewById(R.id.length);
        TextView director = (TextView) rootView.findViewById(R.id.director);
        TextView cast = (TextView) rootView.findViewById(R.id.stars);
        ImageView image = (ImageView) rootView.findViewById(R.id.image);
        TextView description = (TextView) rootView.findViewById(R.id.description);



        name.setText((String) movie.get("name"));
        description.setText((String) movie.get("description"));
        image.setImageResource((Integer) movie.get("image"));
        year.setText("("+(String) movie.get("year")+")");
        rating.setText(movie.get("rating").toString());
        length .setText((String) movie.get("length"));
        director.setText((String) movie.get("director"));
        cast.setText((String) movie.get("stars"));

        final RatingBar ratingBar = (RatingBar) rootView.findViewById(R.id.ratingBar);
        ratingBar.setMax(5);
        float rating1 = Float.parseFloat(movie.get("rating").toString())/2;
        ratingBar.setStepSize(0.05f);
        ratingBar.setRating(rating1);

        return rootView;

    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflator){

        inflator.inflate(R.menu.fragment_detail_menu, menu);

        MenuItem shareItem = menu.findItem(R.id.action_share);
        mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(shareItem);

        intentShare = new Intent(Intent.ACTION_SEND);
        intentShare.setType("text/plain");
        //intentShare.setType("image/*");
        intentShare.putExtra(Intent.EXTRA_TEXT, (String) movie.get("name"));
        mShareActionProvider.setShareIntent(intentShare);


        super.onCreateOptionsMenu(menu,inflator);
    }

}
