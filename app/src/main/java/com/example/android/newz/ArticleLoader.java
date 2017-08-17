package com.example.android.newz;

import android.support.v4.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static android.R.attr.tag;
import static android.os.Build.VERSION_CODES.M;

/**
 * ArticleLoader searches the given url for new content and returns it in the MainActivity
 */

public class ArticleLoader extends AsyncTaskLoader<List<ArticleEntry>> {
    static final String LOG = "ArticleLoader";
    private final static String WORLD_NEWS =
            "http://content.guardianapis.com/search?order-by=relevance&show-tags=contributor&from-date=2017-08-14&section=world&page-size=50&page=1&api-key=test";
    private final static String SCIENCE_NEWS =
            "http://content.guardianapis.com/search?order-by=newest&show-tags=contributor&section=science&page-size=25&page=1&api-key=test";
    private final static String TECH_NEWS =
            "http://content.guardianapis.com/search?order-by=newest&show-tags=contributor&section=technology&page-size=25&page=1&api-key=test";
    static String url;

    /**
     * ArticleLoader is the constructor.
     *
     * @param context is the current activity
     * @param url     represents one of the _NEWS.
     *                0 = WORLD_NEWS
     *                1 = SCIENCE_NEWS
     *                2 = TECH_NEWS
     */
    public ArticleLoader(Context context, String url) {
        super(context);
        this.url = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<ArticleEntry> loadInBackground() {
        return new Sync().syncNow();
    }

    static class Sync {
        private static final int READ_TIMEOUT_IN_MS = 10000;
        private static final int CONNECT_TIMEOUT_IN_MS = 15000;

        // empty Constructor
        Sync() {
        }

        // syncNow accesses the url-String of the ArticleLoader
        List<ArticleEntry> syncNow() {
            // 1. fetching data
            URL url = createURL(ArticleLoader.url);

            // 2. Getting inputStream (JSON-Response)
            InputStream inputStream = null;
            try {
                inputStream = makeHttpRequest(url);
            } catch (IOException e) {
                Log.e(LOG, "There has been an error while calling makeHttpRequest");
            }

            // 3. Parsing the inputStream into a String
            String jsonRespone = null;
            try {
                jsonRespone = readFromStream(inputStream);
            } catch (IOException e) {
                Log.e(LOG, "There has been an error while reading from Stream");
            }

            // 4. extract data from String
            return extractData(jsonRespone);
        }

        // 1.
        private URL createURL(String url) {
            URL newURL = null;
            try {
                newURL = new URL(url);
            } catch (MalformedURLException e) {
                Log.e(LOG, e.getMessage());
            }
            return newURL;
        }

        // 2.
        private InputStream makeHttpRequest(URL url) throws IOException {
            HttpURLConnection urlConnection = null;
            InputStream inputStream = null;

            try {
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setReadTimeout(READ_TIMEOUT_IN_MS);
                urlConnection.setConnectTimeout(CONNECT_TIMEOUT_IN_MS);
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();
                // If the request was successful (response code 200 or 400),
                // then read the input stream
                if (urlConnection.getResponseCode() == 200 || urlConnection.getResponseCode() == 400) {
                    inputStream = urlConnection.getInputStream();
                } else {
                    Log.e("QueryUtils", "Error response code: " + urlConnection.getResponseCode());
                }
            } catch (IOException e) {
                Log.e("QueryUtils", e.getMessage());
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (inputStream != null) {
                    inputStream.close();
                }
            }
            return inputStream;
        }

        // 3.
        private String readFromStream(InputStream is) throws IOException {
            StringBuilder output = new StringBuilder();

            if (is != null) {
                InputStreamReader inputStreamReader = new InputStreamReader(is, Charset.forName("UTF-8"));
                BufferedReader reader = new BufferedReader(inputStreamReader);
                String line = reader.readLine();
                while (line != null) {
                    output.append(line);
                    line = reader.readLine();
                }
                reader.close();
                inputStreamReader.close();
            }
            return output.toString();
        }

        // 4.
        private List<ArticleEntry> extractData(String unfilteredString) {
            ArrayList<ArticleEntry> finalData = new ArrayList<>();

            try {
                JSONObject baseJsonResponse = new JSONObject(unfilteredString);
                JSONObject metaData = baseJsonResponse.getJSONObject("response");

                JSONArray itemArray = metaData.getJSONArray("results");
                JSONObject item = null;

                for (int i = 0; i < metaData.getInt("pageSize"); i++) {
                    // for an easier overview, we get the publication date
                    // and author name seperately

                    String published = "unknown";
                    if (item.has("webPublicationDate")) {
                        try {
                            published = item.getString("webPublicationDate");
                            SimpleDateFormat freshDate = new SimpleDateFormat("yyyy-MM-ddTHH:mm:ssZ");
                            Date date = freshDate.parse(published);
                            SimpleDateFormat newDate = new SimpleDateFormat("M, dd");
                            published = newDate.format(date);

                        } catch (java.text.ParseException e) {
                            Log.e(LOG, "Error with date parsing");
                        } catch (JSONException e) {
                            Log.e(LOG, "Error with date parsing with JSON");
                        }
                    }

                    String author = "unknown";
                    if (item.has("tags")) {
                        StringBuilder authorCreator = new StringBuilder();
                        try {
                            JSONArray tags = item.getJSONArray("tags");

                            for (int n = 0; n < tags.length(); n++) {
                                String newAuthor = tags.getJSONObject(n).getString("webTitle");
                                newAuthor = newAuthor + ", ";
                                authorCreator.append(newAuthor);
                            }

                            authorCreator.delete(authorCreator.length() - 2, author.length() - 1);

                        } catch (JSONException e) {
                            Log.e(LOG, "Error getting tag array (i.e. authors)");
                        }
                        author = authorCreator.toString();
                    }

                    finalData.add(new ArticleEntry(
                            item.getString("webTitle"),
                            item.getString("sectionName"),
                            published,
                            item.getString("webUrl"),
                            author
                    ));
                }

            } catch (JSONException e) {
                Log.e(LOG, "There has been an error while extracting the data into a list");
            }

            return finalData;
        }
    }
}
