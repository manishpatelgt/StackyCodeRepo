package com.order.network;

import android.net.ParseException;

import org.apache.http.HeaderElement;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

public class CommonHttpClient {

    private static final int NETWORK_CONNECTION_POOL_TIMEOUT = 1000;
    private static final int NETWORK_CONNECTION_CONNECT_TIMEOUT = 10000;
    private static final int NETWORK_CONNECTION_READ_TIMEOUT = 10000;
    private static final int NETWORK_SOCKET_CONNECT_TIMEOUT = 10000;
    private static final String userAgent = "Mozilla/5.0 (Linux; U; Android 2.2.1; en-us; Nexus One Build/FRG83) AppleWebKit/533.1 (KHTML, like Gecko) Version/4.0 Mobile Safari/533.1";
    private static HttpClient client;

    // Private Constructor prevents instantiation
    private CommonHttpClient() { }

    public static synchronized HttpClient getInstance() {
        if (client == null) {
            // Set some basic HTTP parameters
            HttpParams params = new BasicHttpParams();
            HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
            HttpProtocolParams.setContentCharset(params, HTTP.DEFAULT_CONTENT_CHARSET);
            HttpProtocolParams.setUseExpectContinue(params, true);
            HttpProtocolParams.setUserAgent(params, userAgent);

            // Timeout for the connection manager which defines how long we should wait to get
            // a connection out of the connection pool managed by the connection manager. About
            // the only time we might ever have to wait is if all connections from the pool are in use.
            ConnManagerParams.setTimeout(params, NETWORK_CONNECTION_POOL_TIMEOUT);

            // How long we should wait to make a connection over the network to the server on the other end
            HttpConnectionParams.setConnectionTimeout(params, NETWORK_CONNECTION_CONNECT_TIMEOUT);

            // How long we should wait to get data back for our request
            HttpConnectionParams.setSoTimeout(params, NETWORK_SOCKET_CONNECT_TIMEOUT);

            // Add support for HTTP and HTTPS
            SchemeRegistry schReg = new SchemeRegistry();
            schReg.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
            schReg.register(new Scheme("https", SSLSocketFactory.getSocketFactory(), 443));

            // We are going to use a single HttpClient for all the HTTP requests (requests
            // that could overlap if we’re using threads)
            ClientConnectionManager conMgr = new ThreadSafeClientConnManager(params, schReg);
            client = new DefaultHttpClient(conMgr, params);
        }

        return client;
    }

    public static String getResponseBody(HttpResponse response) {
        String response_text = null;
        HttpEntity entity = null;

        try {
            entity = response.getEntity();
            response_text = getResponseBody(entity);

        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            if (entity != null) {
                try {
                    entity.consumeContent();
                } catch (IOException e1) {
                }
            }
        }

        return response_text;
    }

    public static String getResponseBody(final HttpEntity entity) throws IOException, ParseException {
        if (entity == null)
            throw new IllegalArgumentException("HTTP entity may not be null");

        InputStream instream = entity.getContent();

        if (instream == null) return "";

        if (entity.getContentLength() > Integer.MAX_VALUE)
            throw new IllegalArgumentException("HTTP entity too large to be buffered in memory");

        String charset = getContentCharSet(entity);

        if (charset == null)
            charset = HTTP.DEFAULT_CONTENT_CHARSET;

        Reader reader = new InputStreamReader(instream, charset);
        StringBuilder buffer = new StringBuilder();

        try {
            char[] tmp = new char[1024];
            int l;

            while ((l = reader.read(tmp)) != -1) {
                buffer.append(tmp, 0, l);
            }
        } finally {
            reader.close();
        }

        return buffer.toString();
    }

    public static String getContentCharSet(final HttpEntity entity) throws ParseException {

        if (entity == null)
            throw new IllegalArgumentException("HTTP entity may not be null");

        String charset = null;

        if (entity.getContentType() != null) {
            HeaderElement values[] = entity.getContentType().getElements();

            if (values.length > 0) {
                NameValuePair param = values[0].getParameterByName("charset");

                if (param != null)
                    charset = param.getValue();
            }
        }

        return charset;
    }

    public Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException();
    }
}