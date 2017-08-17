package com.example.android.newz;

import android.support.design.widget.TabLayout;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.newz.sciencenews.MyScienceRecyclerViewAdapter;
import com.example.android.newz.sciencenews.ScienceFragment;
import com.example.android.newz.technews.MyTechRecyclerViewAdapter;
import com.example.android.newz.technews.TechFragment;
import com.example.android.newz.worldnews.MyWorldNewsRecyclerViewAdapter;
import com.example.android.newz.worldnews.WorldNewsFragment;

import java.util.List;

public class MainActivity extends AppCompatActivity implements
        TechFragment.OnListFragmentInteractionListener,
        WorldNewsFragment.OnListFragmentInteractionListener,
        ScienceFragment.OnListFragmentInteractionListener,
        LoaderManager.LoaderCallbacks<List<ArticleEntry>> {

    // current URL has values from 0-2, where each value represents one fragment/news-section
    private static String currentURL;
    private SectionsPagerAdapter mSectionsPagerAdapter;
    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        // Sections Adapter changes the Tabs together with the TabLayout
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
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

    /**
     * OnClickListener for the each Entry
     * @param item is the Article-Object which was clicked on
     */
    public void onListFragmentInteraction(ArticleEntry item) {
        Toast.makeText(this, item.getUrl(), Toast.LENGTH_SHORT).show();
    }

    // below are the abstract methods for the LoaderManager

    @Override
    public Loader<List<ArticleEntry>> onCreateLoader(int id, Bundle args) {
        return new ArticleLoader(this, currentURL);
    }

    @Override
    public void onLoadFinished(Loader<List<ArticleEntry>> loader, List<ArticleEntry> data) {

    }

    @Override
    public void onLoaderReset(Loader<List<ArticleEntry>> loader) {

    }
}
