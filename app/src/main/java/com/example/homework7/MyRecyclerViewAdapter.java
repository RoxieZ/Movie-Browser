package com.example.homework7;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
import java.util.Map;


/**
 * Created by jinxing on 2/7/2015.
 */
public class MyRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final Context mcontext;
    private final List<Map<String,?>> mDataSet;
    private final int mType;
    OnItemClickListener mItemClickListener;
    public MyRecyclerViewAdapter(Context mycontext, List<Map<String, ?>> myDataSet, int type) {
       mcontext = mycontext;
      mDataSet = myDataSet;
      mType = type;
    }
    public class ListViewHolder extends RecyclerView.ViewHolder{
        public ImageView vIcon;
        public TextView vTitle;
        public TextView vDescription;
        public CheckBox vCheckbox;
        public ImageView vMenu;

        public ListViewHolder(View v){
            super(v);
            vIcon = (ImageView)v.findViewById(R.id.icon);
            vTitle = (TextView)v.findViewById(R.id.name);
            vDescription = (TextView)v.findViewById(R.id.description);
            vMenu = (ImageView)v.findViewById(R.id.menu_list);
         //   vCheckbox = (CheckBox)v.findViewById(R.id.selection);

            v.setOnClickListener(new OnClickListener(){
                @Override
                public void onClick(View v){
                    if( mItemClickListener != null){
                        mItemClickListener.onItemClick(v,getPosition());
                    }
                }
            });
            v.setOnLongClickListener(new View.OnLongClickListener(){
                @Override
                public boolean onLongClick(View v){
                    if(mItemClickListener != null){
                        mItemClickListener.onItemLongClick(v, getPosition());
                    }
                    return true;
                }
            });
            vMenu.setOnClickListener(new OnClickListener(){
                @Override
                public void onClick(View v){
                    if( mItemClickListener != null){
                        mItemClickListener.onOverFlowMenuClick(v,getPosition());
                    }
                }

            });/*
            vCheckbox.setOnClickListener(new OnClickListener(){
                @Override
                public void onClick(View v){
                    HashMap movie = (HashMap) mDataSet.get(getPosition());
                    movie.put("selection", vCheckbox.isChecked());

                }
            });*/

        }

        public void bindMovieData(Map<String, ?> movie){
            vTitle.setText((String) movie.get("name"));
            vDescription.setText((String) movie.get("description"));
            vIcon.setImageResource((Integer) movie.get("image"));
//            vCheckbox.setChecked((Boolean)movie.get("selection"));
        }


    }
    public class GridViewHolder extends RecyclerView.ViewHolder {
        public ImageView vIcon;


        public GridViewHolder(View v) {
            super(v);
            vIcon = (ImageView) v.findViewById(R.id.icon1);


            v.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mItemClickListener != null) {
                        mItemClickListener.onItemClick(v, getPosition());
                    }
                }
            });
            v.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (mItemClickListener != null) {
                        mItemClickListener.onItemLongClick(v, getPosition());
                    }
                    return true;
                }
            });

        }

        public void bindMovieData(Map<String, ?> movie) {
            vIcon.setImageResource((Integer) movie.get("image"));

        }
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        View v;
        RecyclerView.ViewHolder vh;
        if(viewType == 0) {
            v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.cardview, parent, false);
            vh = new ListViewHolder(v);
        }
        else {
            v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.cardview1, parent, false);
            vh = new GridViewHolder(v);
        }

        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Map<String, ?>movie = mDataSet.get(position);
        switch(getItemViewType(position)){
            case 0:
                ListViewHolder listviewholder = (ListViewHolder) holder;
                listviewholder.bindMovieData(movie);
                break;
            case 1:
                GridViewHolder gridviewholder = (GridViewHolder) holder;
                gridviewholder.bindMovieData(movie);
                break;
        }

    }
    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    public interface OnItemClickListener{
        public void onItemClick(View view, int position);
        public void onItemLongClick(View view, int position);
        public void onOverFlowMenuClick(View view, final int position);
    }

    public void SetOnItemClickListener(final OnItemClickListener mItemClickListener){
        this.mItemClickListener = mItemClickListener;
    }

    @Override
    public int getItemViewType(int position){
        int viewType;
        viewType = mType;
        return viewType;
    }
}
