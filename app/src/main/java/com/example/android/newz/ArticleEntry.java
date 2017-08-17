package com.example.android.newz;

import android.support.annotation.Nullable;

/**
 * Custom Object for our adapter
 */

public class ArticleEntry {
    // Attributes
    private String mTitle;
    private String mSection;
    private String mRawPublicationDate; // not converted into good readable date-format
    private String mUrl;
    private String mAuthor;

    /**
     * Constructor for the Entries of Articles in the List's
     * @param mTitle: is the title of the article
     * @param mSection: is the sections it belongs to (e.g. 'technology')
     * @param mRawPublicationDate: is the _raw_ publication date (e.g. '2017-08-14T09:41:36Z')
     * @param mUrl: is the Web-URL to the article
     */
    public ArticleEntry(String mTitle, String mSection, @Nullable String mRawPublicationDate, String mUrl, @Nullable String mAuthor) {
        this.mTitle = mTitle;
        this.mSection = mSection;
        this.mRawPublicationDate = mRawPublicationDate;
        this.mUrl = mUrl;
        this.mAuthor = mAuthor;
    }

    // Methods
    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getSection() {
        return mSection;
    }

    public void setSection(String mSection) {
        this.mSection = mSection;
    }

    public String getPublicationDate() {
        return mRawPublicationDate;
    }

    public void setPublicationDate(String mRawPublicationDate) {
        this.mRawPublicationDate = mRawPublicationDate;
    }

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String mUrl) {
        this.mUrl = mUrl;
    }

    public String getAuthor() {
        return mAuthor;
    }

    public void setAuthor(String mAuthor) {
        this.mAuthor = mAuthor;
    }
}
