package com.rishabh.livenewsapi;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {
    JSONObject jsonObject;
    JSONArray jsonArray;
    ListView listView;
    NewsAdaptor newsAdaptor;
    ProgressDialog loading;
    String json_url = "https://newsapi.org/v2/top-headlines?q=cricket&apiKey=19986c6f66bb420d9e457caa46501c39";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.listView);
        newsAdaptor = new NewsAdaptor(this,R.layout.row_layout);
        listView.setAdapter(newsAdaptor);
        new BackgroundTask().execute();
    }
    class BackgroundTask extends AsyncTask<Void, Void, String> {
        String JSON_STRING;

        @Override
        protected void onPreExecute() {
            loading = ProgressDialog.show(MainActivity.this, "Requesting Server...", null,true,true);
        }

        @Override
        protected String doInBackground(Void... voids) {
            try {
                URL url = new URL(json_url);
                HttpsURLConnection httpURLConnection = (HttpsURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
//                OutputStream outputStream = httpURLConnection.getOutputStream();
//                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
//                String data = URLEncoder.encode("user_mobile","UTF-8") + "=" + URLEncoder.encode(MainActivity.user_phone,"UTF-8");
//                bufferedWriter.write(data);
//                bufferedWriter.flush();
//                bufferedWriter.close();
//                outputStream.close();
                InputStream inputStream  = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));

                StringBuilder stringBuilder = new StringBuilder();
                while ((JSON_STRING = bufferedReader.readLine()) != null){
                    stringBuilder.append(JSON_STRING+"\n");
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return stringBuilder.toString().trim();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String result) {
            //MDToast.makeText(ViewPortfolio.this,result,Toast.LENGTH_LONG,MDToast.TYPE_WARNING).show();
            parseJson(result);
        }
    }
    public void parseJson(String result){
        try {
            jsonObject = new JSONObject(result);
            jsonArray = jsonObject.getJSONArray("articles");
            int count=0;
            String author,title,urlToImage,url,des;
            while (count<jsonArray.length())
            {
                JSONObject JO = jsonArray.getJSONObject(count);
                author = JO.getString("author");
                title = JO.getString("title");
                des = JO.getString("description");
                urlToImage = JO.getString("urlToImage");
                url = JO.getString("url");
                NewsData newsData = new NewsData(author,title,urlToImage,url,des);
                newsAdaptor.add(newsData);
                count=count+1;
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }catch (Exception e){

        }
        loading.dismiss();
    }
}
