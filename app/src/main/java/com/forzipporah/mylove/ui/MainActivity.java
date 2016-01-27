package com.forzipporah.mylove.ui;

import android.content.res.Configuration;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.forzipporah.mylove.R;
import com.forzipporah.mylove.adapters.PoemViewPagerAdapter;
import com.forzipporah.mylove.database.Database;
import com.forzipporah.mylove.database.contracts.PoemContract;
import com.forzipporah.mylove.fragments.poem.PoemFragment;

public class MainActivity extends AppCompatActivity implements PoemFragment.RefetchTotalPoems {

    // for navigation drawer
    private ListView              mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout          mDrawerLayout;
    private String                mActivityTitle;

    // layout items
    private TextView tvPoemCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvPoemCount = (TextView) findViewById(R.id.poemCountTV);
        fetchTotalRecords();


        //  get a reference to items
        mDrawerList = (ListView) findViewById(R.id.navList);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        // get the title of the action bar
        mActivityTitle = getTitle().toString();

        // check if the action bar exists and and remove it's logo icon
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
        }


        // Give the TabLayout the ViewPager
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        ViewPager            pager                = (ViewPager) findViewById(R.id.pager);
        PoemViewPagerAdapter poemViewPagerAdapter = new PoemViewPagerAdapter(getSupportFragmentManager());
        pager.setAdapter(poemViewPagerAdapter);
        tabLayout.setupWithViewPager(pager);

        // add navigation items & set up drawer
        addDrawerItems();
        setupDrawer();
        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        // on click listener for navigation draw for each individual item
        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
    }

    private void fetchTotalRecords() {
        TotalPoemRecords totalPoemRecords = new TotalPoemRecords();
        totalPoemRecords.execute();
    }

    private void setNumPoems(String s) {
        tvPoemCount.setText(s);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id) {
            case R.id.action_settings:
                return true;
        }
        // draw toggle
        //noinspection SimplifiableIfStatement
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    // set up navigation drawer
    private void setupDrawer() {
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.string.drawer_open, R.string.drawer_close) {

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);

                if (getSupportActionBar() != null) {
                    getSupportActionBar().setTitle("Menu");
                }
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                if (getSupportActionBar() != null) {
                    getSupportActionBar().setTitle(mActivityTitle);
                }
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };
    }

    // add items to the navgation drawer
    private void addDrawerItems() {
        String[] menuItems = {
                "I Love",
                "A Memory",
                "Quizzes",
                "Love Meter",
                "Reassurance",
                "I Promise",
                "Photo Gallery",
                "Why I Love You",
                "Ask A Question",
                "Settings",
        };
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, menuItems);
        mDrawerList.setAdapter(adapter);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    /**
     * If the user comes back to main activity from the PoemViewActivity and data was changed fetch back the data
     */
    @Override
    protected void onResume() {
        super.onResume();
    }


    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void refetch() {
        fetchTotalRecords();
    }


    private class TotalPoemRecords extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... params) {
            Database       database   = new Database(getBaseContext());
            SQLiteDatabase db         = database.getReadableDatabase();
            long           numEntries = DatabaseUtils.queryNumEntries(db, PoemContract.TABLE_NAME);
            return numEntries + "";
        }

        @Override
        protected void onPostExecute(String s) {
            setNumPoems(s);
        }
    }
}
