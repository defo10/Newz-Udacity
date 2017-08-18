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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * ArticleLoader searches the given url for new content and returns it in the MainActivity
 */

public class ArticleLoader extends AsyncTaskLoader<List<ArticleEntry>> {
    private static final String LOG = "ArticleLoader";
    private final int urlLinks;

    public ArticleLoader(Context context, int url) {
        super(context);
        this.urlLinks = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<ArticleEntry> loadInBackground() {
        return new Sync().syncNow();
    }

    class Sync {
        private static final int READ_TIMEOUT_IN_MS = 10000;
        private static final int CONNECT_TIMEOUT_IN_MS = 15000;

        // empty Constructor
        Sync() {
        }

        // syncNow accesses the url-String of the ArticleLoader
        List<ArticleEntry> syncNow() {
            // 1. creating url
            URL url = createURL(urlLinks);

            // 2. Getting inputStream (JSON-Response)
            String inputStream = null;
            try {
                inputStream = makeHttpRequest(url);
            } catch (IOException e) {
                Log.e(LOG, "There has been an error while calling makeHttpRequest");
            }

            // 3. extract data from String
            return extractData(inputStream);
        }

        // 1.
        private URL createURL(int url) {
            String link = "";
            if (url == 0) link = AllArticles.WORLD_NEWS;
            if (url == 1) link = AllArticles.SCIENCE_NEWS;
            if (url == 2) link = AllArticles.TECH_NEWS;

            URL newURL = null;
            try {
                newURL = new URL(link);
            } catch (MalformedURLException e) {
                Log.e(LOG, e.getMessage());
            }
            return newURL;
        }

        // 2.
        private String makeHttpRequest(URL url) throws IOException {
            HttpURLConnection urlConnection = null;
            InputStream inputStream = null;

            String jsonResponse = "";

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
                    jsonResponse = readFromStream(inputStream);
                } else {
                    Log.e("makeHttpRequest", "Error response code: " + urlConnection.getResponseCode());
                }
            } catch (IOException e) {
                Log.e("makeHttpRequest", e.getMessage());
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (inputStream != null) {
                    inputStream.close();
                }
            }
            return jsonResponse;
        }

        // 3.
        private String readFromStream(InputStream is) throws IOException {
            StringBuilder output = new StringBuilder();

            InputStreamReader inputStreamReader = new InputStreamReader(is, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
            reader.close();
            inputStreamReader.close();

            return output.toString();
        }

        // 4.
        private List<ArticleEntry> extractData(String unfilteredString) {
            ArrayList<ArticleEntry> finalData = new ArrayList<>();

            try {
                JSONObject baseJsonResponse = new JSONObject(unfilteredString);
                JSONObject metaData = baseJsonResponse.getJSONObject("response");
                JSONArray itemArray = metaData.getJSONArray("results");

                for (int i = 0; i < metaData.getInt("pageSize"); i++) {
                    JSONObject item = itemArray.getJSONObject(i);

                    // for an easier overview, we get the publication date
                    // and author name seperately

                    String published = "unknown";
                    if (item.has("webPublicationDate")) {
                        try {
                            published = item.getString("webPublicationDate");
                            SimpleDateFormat freshDate = new SimpleDateFormat("yyyy-MM-dd'T'kk:mm:ss'Z'");
                            Date date = freshDate.parse(published);
                            SimpleDateFormat newDate = new SimpleDateFormat("dd.MM kk:mm");
                            published = newDate.format(date) + " o'clock ";

                        } catch (java.text.ParseException e) {
                            Log.e(LOG, "Error with date parsing");
                        } catch (JSONException e) {
                            Log.e(LOG, "Error with date parsing with JSON");
                        }
                    }

                    String author = " Unknown";
                    if (item.has("tags") && item.getJSONArray("tags").length() != 0) {
                        StringBuilder authorCreator = new StringBuilder(" ");
                        try {
                            JSONArray tags = item.getJSONArray("tags");

                            for (int n = 0; n < tags.length(); ++n) {
                                String newAuthor = tags.getJSONObject(n).getString("webTitle");
                                authorCreator.append(newAuthor);
                                authorCreator.append(", ");
                            }
                        } catch (JSONException e) {
                            Log.e(LOG, "Error getting tag array (i.e. authors)");
                        } finally {
                            authorCreator.deleteCharAt(authorCreator.length() - 1);
                            authorCreator.deleteCharAt(authorCreator.length() - 1);
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
