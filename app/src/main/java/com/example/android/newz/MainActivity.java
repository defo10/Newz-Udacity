package com.example.android.newz;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import android.widget.Toast;

import com.example.android.newz.sciencenews.ScienceFragment;
import com.example.android.newz.technews.TechFragment;
import com.example.android.newz.worldnews.WorldNewsFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements
        TechFragment.OnListFragmentInteractionListener,
        WorldNewsFragment.OnListFragmentInteractionListener,
        ScienceFragment.OnListFragmentInteractionListener,
        LoaderManager.LoaderCallbacks<List<ArticleEntry>> {

    private SectionsPagerAdapter mSectionsPagerAdapter;
    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList<ArticleEntry> one = new ArrayList<>();
        one.add(new ArticleEntry("bla", "bla", "2014-02-17T12:05:47Z", "www.google.de", "daniel"));
        ArrayList<ArticleEntry> two = new ArrayList<>();
        two.add(new ArticleEntry("bla", "bla", "2014-02-17T12:05:47Z", "www.google.de", "daniel"));
        ArrayList<ArticleEntry> three = new ArrayList<>();
        three.add(new ArticleEntry("bla", "bla", "2014-02-17T12:05:47Z", "www.google.de", "daniel"));

        AllArticles.setNewsArticleList(new ArrayList<ArticleEntry>());
        AllArticles.setTechArticleList(new ArrayList<ArticleEntry>());
        AllArticles.setScienceArticleList(new ArrayList<ArticleEntry>());


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        // Sections Adapter changes the Tabs together with the TabLayout
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setOffscreenPageLimit(3);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        // sync all lists
        syncAllLists();
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

    // this is the sync method, which updates the science-list in the AllArticles-Class
    // NOTE: this methods init. the loader, so the return value is seen in the other methods below

    public void syncAllLists() {
        getSupportLoaderManager().initLoader(0, null, this);
        getSupportLoaderManager().initLoader(1, null, this);
        getSupportLoaderManager().initLoader(2, null, this);
    }

    // below are the abstract methods for the LoaderManager
    @Override
    public Loader<List<ArticleEntry>> onCreateLoader(int id, Bundle args) {
        switch (id) {
            case 0:
                return new ArticleLoader(this, 0);
            case 1:
                return new ArticleLoader(this, 1);
            case 2:
                return new ArticleLoader(this, 2);
        }
        return null;
    }

    @Override
    public void onLoadFinished(Loader<List<ArticleEntry>> loader, List<ArticleEntry> data) {
        switch (loader.getId()) {
            case 0:
                AllArticles.setNewsArticleList(data);
                break;
            case 1:
                AllArticles.setScienceArticleList(data);
                break;
            case 2:
                AllArticles.setTechArticleList(data);
                break;
        }

        getFragmentManager().findFragmentById(R.id.list_worldnews).onResume();
        getFragmentManager().findFragmentById(R.id.list_science).
                getFragmentManager().findFragmentById(R.id.list_tech).onResume();
    }

    @Override
    public void onLoaderReset(Loader<List<ArticleEntry>> loader) {
    }
}
