package com.example.android.newz.sciencenews;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.newz.ArticleEntry;
import com.example.android.newz.R;

import java.util.ArrayList;

import static com.example.android.newz.R.id.view;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class ScienceFragment extends Fragment {
    private View rootView;
    private OnListFragmentInteractionListener mListener;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ScienceFragment() {
    }

    @SuppressWarnings("unused")
    public static ScienceFragment newInstance() {
        return new ScienceFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_science_list, container, false);
        View view = rootView.findViewById(R.id.list);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            recyclerView.setLayoutManager(new LinearLayoutManager(context));

            /**
             * DELETE SAMPLE-ENTRY-List
             */
            ArrayList<ArticleEntry> tmp = new ArrayList<>();
            tmp.add(new ArticleEntry("Test3", "news", "22-3-2918", "www.google.com", "Daniel"));
            tmp.add(new ArticleEntry("Test3", "news", "22-3-2918", "www.google.com", "Daniel"));
            tmp.add(new ArticleEntry("Test3", "news", "22-3-2918", "www.google.com", "Daniel"));
            tmp.add(new ArticleEntry("Test3", "news", "22-3-2918", "www.google.com", "Daniel"));
            tmp.add(new ArticleEntry("Test3", "news", "22-3-2918", "www.google.com", "Daniel"));
            tmp.add(new ArticleEntry("Test3", "news", "22-3-2918", "www.google.com", "Daniel"));
            tmp.add(new ArticleEntry("Test3", "news", "22-3-2918", "www.google.com", "Daniel"));

            recyclerView.setAdapter(new MyScienceRecyclerViewAdapter(tmp, mListener));
        }
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