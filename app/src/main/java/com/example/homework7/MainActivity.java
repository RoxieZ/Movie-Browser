package com.example.homework7;

import java.util.Date;
import java.util.HashMap;

import android.support.v4.app.Fragment;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v7.widget.RecyclerView;
import android.widget.RelativeLayout;

public class MainActivity extends ActionBarActivity implements CoverFragment.OnButtonSelectedListener
                         ,MyRecyclerviewFragment.OnRecyclerViewItemSelectedListener {
    private RelativeLayout mDrawer;
    private DrawerLayout mDrawerLayout;
    private RecyclerView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    private Toolbar mToolbar;
    private  MyDrawerRecyclerViewAdapter mDrawerRecyclerViewAdapter;


    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    private String[] mPlanetTitles;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        mDrawer = (RelativeLayout) findViewById(R.id.drawer);
        mDrawerList = (RecyclerView) findViewById(R.id.drawer_list);
        mDrawerList.setLayoutManager(new LinearLayoutManager(this));
        mDrawerRecyclerViewAdapter = new MyDrawerRecyclerViewAdapter(this,  (new Drawer_Data()).getDrawerList());
        mDrawerRecyclerViewAdapter.SetOnItemClickListener(new MyDrawerRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                selectItem(position);
            }

        });
        mDrawerList.setAdapter(mDrawerRecyclerViewAdapter);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        mDrawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                mDrawerLayout,         /* DrawerLayout object */
                mToolbar,  /* nav drawer image to replace 'Up' caret */
                R.string.drawer_open,  /* "open drawer" description for accessibility */
                R.string.drawer_close  /* "close drawer" description for accessibility */
        ) {
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
            }

            public void onDrawerOpened(View drawerView) {
               super.onDrawerOpened(drawerView); // creates call to onPrepareOptionsMenu()
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        if (savedInstanceState == null) {
            selectItem(0);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void selectItem(int position) {
        // update the main content by replacing fragments
       switch(position) {
           case 0:
               getSupportFragmentManager().beginTransaction().replace(R.id.container, new CoverFragment()).commit();
               break;
           case 1:
               getSupportFragmentManager().beginTransaction().replace(R.id.container, MyRecyclerviewFragment.newInstance(0)).commit();
               break;
           case 2:
               getSupportFragmentManager().beginTransaction().replace(R.id.container, MyRecyclerviewFragment.newInstance(1)).commit();
               break;
           case 5:
               getSupportFragmentManager().beginTransaction().replace(R.id.container, PlaceholderFragment.newInstance(0)).commit();
               break;
           case 6:
               getSupportFragmentManager().beginTransaction().replace(R.id.container, PlaceholderFragment.newInstance(1)).commit();
               break;
           case 7:
               getSupportFragmentManager().beginTransaction().replace(R.id.container, PlaceholderFragment.newInstance(2)).commit();
               break;
           default:
               getSupportFragmentManager().beginTransaction().replace(R.id.container, new CoverFragment()).commit();
               break;
       }


    }
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public void onButtonItemSelected(int id){
        Intent intent;

    }
    @Override
    public void onItemSelected(int position,HashMap<String,?> movie){
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, MyDetailFragment.newInstance(movie))
                .addToBackStack(null)
                .commit();

    }
    public static class PlaceholderFragment extends Fragment {

        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }


        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView;
            int option= (Integer)getArguments().get(ARG_SECTION_NUMBER);
            switch(option) {
                case 0:
                     rootView = inflater.inflate(R.layout.about_me, container, false);
                    break;
                case 1:
                    rootView = inflater.inflate(R.layout.luffy_image, container, false);
                    break;
                case 2:
                    rootView = inflater.inflate(R.layout.logout, container, false);
                    break;
                default:
                    rootView = inflater.inflate(R.layout.luffy_image, container, false);
                    break;


            }
            return rootView;
        }
    }
}
