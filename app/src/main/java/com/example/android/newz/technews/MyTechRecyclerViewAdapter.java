package com.example.android.newz.technews;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.newz.AllArticles;
import com.example.android.newz.ArticleEntry;
import com.example.android.newz.R;
import com.example.android.newz.technews.TechFragment.OnListFragmentInteractionListener;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link ArticleEntry} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 */
public class MyTechRecyclerViewAdapter extends RecyclerView.Adapter<MyTechRecyclerViewAdapter.ViewHolder> {

    private final List<ArticleEntry> mValues;
    private final OnListFragmentInteractionListener mListener;

    public MyTechRecyclerViewAdapter(List<ArticleEntry> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    public void updateTechNewsAdapter() {
        mValues.clear();
        mValues.addAll(AllArticles.getTechArticleList());
        this.notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mTitleId.setText(mValues.get(position).getTitle());
        holder.mPublicationDateId.setText(mValues.get(position).getPublicationDate());
        holder.mAuthorId.setText(mValues.get(position).getAuthor());
        holder.mSectionId.setText(mValues.get(position).getSection());

        // the code below sets an OnClickListener on each Entry, and calls the implemented method
        // in the MainActivity with the clicked object as argument
        holder.mViewId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mViewId;
        public final TextView mTitleId;
        @Nullable
        public final TextView mPublicationDateId;
        @Nullable public final TextView mAuthorId;
        public final TextView mSectionId;
        @Nullable public ArticleEntry mItem;

        public ViewHolder(View view) {
            super(view);
            mViewId = view;
            mTitleId = (TextView) view.findViewById(R.id.title);
            mPublicationDateId = (TextView) view.findViewById(R.id.publication_date);
            mAuthorId = (TextView) view.findViewById(R.id.author_name);
            mSectionId = (TextView) view.findViewById(R.id.section);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mPublicationDateId.getText() + "'";
        }
    }
}
