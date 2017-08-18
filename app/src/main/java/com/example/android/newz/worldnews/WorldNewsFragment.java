package com.example.android.newz.worldnews;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.newz.AllArticles;
import com.example.android.newz.ArticleEntry;
import com.example.android.newz.ArticleLoader;
import com.example.android.newz.R;

import java.util.List;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class WorldNewsFragment
        extends Fragment
        implements LoaderManager.LoaderCallbacks<List<ArticleEntry>> {

    private static MyWorldNewsRecyclerViewAdapter adapter;
    private OnListFragmentInteractionListener mListener;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public WorldNewsFragment() {
    }

    @SuppressWarnings("unused")
    public static WorldNewsFragment newInstance() {
        return new WorldNewsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_worldnews_list, container, false);
        view = view.findViewById(R.id.list_worldnews);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            recyclerView.setLayoutManager(new LinearLayoutManager(context));

            adapter = new MyWorldNewsRecyclerViewAdapter(AllArticles.getNewsArticleList(), mListener);
            recyclerView.setAdapter(adapter);
        }

        getLoaderManager().initLoader(0, null, this);

        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onResume() {
        super.onResume();
        adapter.updateWorldNewsAdapter();
    }

    // below are the abstract methods for the LoaderManager
    @Override
    public Loader<List<ArticleEntry>> onCreateLoader(int id, Bundle args) {
        return new ArticleLoader(getContext(), 0);
    }

    @Override
    public void onLoadFinished(Loader<List<ArticleEntry>> loader, List<ArticleEntry> data) {
        AllArticles.setNewsArticleList(data);
        adapter.updateWorldNewsAdapter();
    }

    @Override
    public void onLoaderReset(Loader<List<ArticleEntry>> loader) {
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        void onListFragmentInteraction(ArticleEntry item);
    }

}
