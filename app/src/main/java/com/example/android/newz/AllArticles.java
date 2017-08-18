package com.example.android.newz;

import java.util.ArrayList;
import java.util.List;

/**
 * AllArticles saves all Lists
 */

public class AllArticles {
    public final static String SCIENCE_NEWS =
            "http://content.guardianapis.com/search?order-by=newest&show-tags=contributor&section=science&page-size=25&page=1&api-key=test";
    public final static String WORLD_NEWS =
            "http://content.guardianapis.com/search?order-by=newest&show-tags=contributor&section=world&page-size=50&page=1&api-key=test";
    public final static String TECH_NEWS =
            "http://content.guardianapis.com/search?order-by=newest&show-tags=contributor&section=technology&page-size=25&page=1&api-key=test";

    private static List<ArticleEntry> newsArticleList = new ArrayList<>();
    private static List<ArticleEntry> scienceArticleList = new ArrayList<>();
    private static List<ArticleEntry> techArticleList = new ArrayList<>();

    public static List<ArticleEntry> getNewsArticleList() {
        return newsArticleList;
    }

    public static void setNewsArticleList(List<ArticleEntry> newsArticleList) {
        AllArticles.newsArticleList = newsArticleList;
    }

    public static List<ArticleEntry> getScienceArticleList() {
        return scienceArticleList;
    }

    public static void setScienceArticleList(List<ArticleEntry> scienceArticleList) {
        AllArticles.scienceArticleList = scienceArticleList;
    }

    public static List<ArticleEntry> getTechArticleList() {
        return techArticleList;
    }

    public static void setTechArticleList(List<ArticleEntry> techArticleList) {
        AllArticles.techArticleList = techArticleList;
    }
}
