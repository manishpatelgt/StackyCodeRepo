
package com.order.webservices.webapi;

import android.util.Log;

import com.google.gson.GsonBuilder;
import com.order.Utils.Util;
import com.order.network.CommonHttpClient;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicHeader;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

public class WebApiHelper {

   /**
     * Posts a JSON object to a web service
     * @param url
     * @param params Map of parameters, e.g.
     *      Map<String, String> comment = new HashMap<String, String>();
            comment.put("subject", "Using the GSON library");
            comment.put("message", "Using libraries is convenient.");
     * @return
     * @throws UnsupportedEncodingException
     * @throws ClientProtocolException
     * @throws IOException
     */
    public static String postJSON(String url, Map params) throws IOException {

        // Convert parameters to JSON object
        String json = new GsonBuilder().create().toJson(params, Map.class);
        StringEntity jsonEntity = new StringEntity(json);

        HttpPost httpPost = new HttpPost(url);
        httpPost.setEntity(jsonEntity);
        httpPost.setHeader("Accept", "application/json");
        httpPost.setHeader(HTTP.CONTENT_TYPE, "application/json; charset=utf-8");

        /*
            byte[] jsonData = json.toString().getBytes("UTF8");
            httpPost.setEntity(new ByteArrayEntity(jsonData));
         */

        // Overwrite default CommonHttpClient params
        HttpParams httpParams = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(httpParams, 60000);
        HttpConnectionParams.setSoTimeout(httpParams, 60000);

        HttpClient client = CommonHttpClient.getInstance();
        HttpResponse response = client.execute(httpPost);

        if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
            return null;
        }

        return EntityUtils.toString(response.getEntity());
    }

    /**
     * Post a {@link org.json.JSONObject} to a Web API controller and return a  JSON response.
     * @param url The remote web service URL
     * @param jsonData JSON
     * @throws ClientProtocolException
     * @throws IOException
     *
     * JSONObject json = new JSONObject();
        json.put("userName", "USER_NAME");
        json.put("password", "USER_PASSWORD");
     */
    public static String postJSON(String url, JSONObject jsonData) throws JSONException, IOException  {

    	InputStream inputStream = null;
    	String result = "";
    	
        // Override default timeout parameters for HttpClient
        HttpParams httpParams = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(httpParams, Util.INTERVAL_THIRTY_SECONDS);
        HttpConnectionParams.setSoTimeout(httpParams, Util.INTERVAL_THIRTY_SECONDS);

        HttpClient httpClient = CommonHttpClient.getInstance();
        HttpPost httpPost = new HttpPost(url);
        httpPost.setParams(httpParams);

        // Set request body and headers
        httpPost.setHeader(HTTP.CONTENT_TYPE, "application/json");
        StringEntity entity = new StringEntity(jsonData.toString());
        entity.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
        httpPost.setEntity(entity);

        HttpResponse response = httpClient.execute(httpPost);
        int statusCode = response.getStatusLine().getStatusCode();
        inputStream = getJSONStream(url);

        if (statusCode != HttpStatus.SC_OK) {
            return null;
        }
        // 10. convert inputstream to string
       /* if(inputStream != null)
            result = convertInputStreamToString(inputStream);
        else
            result = "Did not work!";*/
        
        return EntityUtils.toString(response.getEntity());
    }

    public static String getJSON(String url) throws ClientProtocolException, IOException {

        StringBuilder result = new StringBuilder();
        InputStream stream = null;

        try {
            HttpClient client = CommonHttpClient.getInstance();
            HttpGet httpGet = new HttpGet(url);

            HttpResponse response = client.execute(httpGet);
            int statusCode = response.getStatusLine().getStatusCode();

            if (statusCode != HttpStatus.SC_OK) {
                //logger.error("Failed to download JSON from " + url);
                return "{}";
            }

            stream = response.getEntity().getContent();
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));

            String line = "";
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }

        } finally {
            stream.close();
        }

        return result.toString();
    }

    public static InputStream getJSONStream(String url) throws IOException {

        HttpClient client = CommonHttpClient.getInstance();
        HttpGet httpGet = new HttpGet(url);
        HttpResponse response = client.execute(httpGet);

        int statusCode = response.getStatusLine().getStatusCode();

        if (statusCode != HttpStatus.SC_OK) {
            Log.e("BMS", "Error " + statusCode + " for URL " + url);
            return null;
        }

        return response.getEntity().getContent();
    }

    private static String convertInputStreamToString(InputStream inputStream) throws IOException{
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null)
            result += line;
 
        inputStream.close();
        return result;
 
    }   
    
    /**
     * Posts a request (with parameters) to the remote server and returns a JSON object.
     *
     * @param url    Remote server address
     * @param params Collection of HTTP protocol and framework parameters.
     * @return
     *
     * How to use:
     *
     *      List<NameValuePair> params = new ArrayList<NameValuePair>();
     *      params.add(new BasicNameValuePair("param1", data1));
     *      params.add(new BasicNameValuePair("param2", data2));
     *
     *      JSONObject json = jsonParser.getJSONFromUrl("http://server/getsomething/", params);
     */
    public static JSONObject getJSONFromUrl(String url, List<NameValuePair> params) {

        JSONObject result = null;
        InputStream stream = null;

        try {
            HttpClient client = CommonHttpClient.getInstance();
            HttpPost httpPost = new HttpPost(url);
            httpPost.setEntity(new UrlEncodedFormEntity(params));

            HttpResponse response = client.execute(httpPost);

            int status = response.getStatusLine().getStatusCode();
            if (status != 200) {
                return null;
            }

            stream = response.getEntity().getContent();
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));

            StringBuilder sb = new StringBuilder();
            String line = "";
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }

            result = new JSONObject(sb.toString());

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            try {
                stream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return result;
    }

}