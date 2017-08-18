package com.example.android.newz;

import android.content.Intent;
import android.net.Uri;
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
        ScienceFragment.OnListFragmentInteractionListener {

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

        AllArticles.setNewsArticleList(one);
        AllArticles.setTechArticleList(two);
        AllArticles.setScienceArticleList(three);


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
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(item.getUrl()));
        startActivity(i);
    }

}
