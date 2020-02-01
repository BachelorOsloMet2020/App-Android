package no.dyrebar.dyrebar.web;

import android.net.Uri;
import android.util.Pair;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

import no.dyrebar.dyrebar.BuildConfig;

public class Api
{
    public URL getUrl(String _url)
    {
        URL url = null;
        try { url = new URL(_url); }
        catch (MalformedURLException e) { e.printStackTrace(); }
        return url;
    }

    public String Get(String _url)
    {
        URL url = getUrl(_url);
        if (url != null)
        {
            try {
                if (Source.ovverideHTTPS)
                {
                    HttpURLConnection client = (HttpURLConnection )url.openConnection();
                /*if (BuildConfig.DEBUG)
                    client.setHostnameVerifier(Certificate.hostnameVerifier());*/

                    client.setRequestMethod("GET");
                    client.setReadTimeout(5000);
                    client.setConnectTimeout(5000);
                    client.setDoOutput(false);
                    client.connect();
                    return readStream(new InputStreamReader(url.openStream()));
                }
                else
                {
                    HttpsURLConnection client = (HttpsURLConnection )url.openConnection();
                    client.setSSLSocketFactory(Certificate.sslContext.getSocketFactory());
                /*if (BuildConfig.DEBUG)
                    client.setHostnameVerifier(Certificate.hostnameVerifier());*/

                    client.setRequestMethod("GET");
                    client.setReadTimeout(5000);
                    client.setConnectTimeout(5000);
                    client.setDoOutput(false);
                    client.connect();
                    return readStream(new InputStreamReader(url.openStream()));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return null;
    }

    public String Post(String _url, ArrayList<Pair<String, ?>> alp)
    {
        String data = getData(alp);
        String res = null;
        URL url = getUrl(_url);
        if (url != null)
        {
            try {
                if (Source.ovverideHTTPS)
                {
                    HttpURLConnection client = (HttpURLConnection )url.openConnection();

                    client.setDoOutput(true);
                    client.setRequestMethod("POST");

                    DataOutputStream dos = new DataOutputStream(client.getOutputStream());
                    dos.writeBytes(data);
                    dos.flush();
                    //osw.close();

                    InputStreamReader isr = new InputStreamReader(client.getInputStream());
                    res = readStream(isr);
                    isr.close();
                }
                else
                {
                    HttpsURLConnection client = (HttpsURLConnection )url.openConnection();
                    client.setSSLSocketFactory(Certificate.sslContext.getSocketFactory());
                /*if (BuildConfig.DEBUG)
                    client.setHostnameVerifier(Certificate.hostnameVerifier());*/

                    client.setDoOutput(true);
                    client.setRequestMethod("POST");

                    DataOutputStream dos = new DataOutputStream(client.getOutputStream());
                    dos.writeBytes(data);
                    dos.flush();
                    //osw.close();

                    InputStreamReader isr = new InputStreamReader(client.getInputStream());
                    res = readStream(isr);
                    isr.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
            }
        }
        return res;
    }

    public String getData(ArrayList<Pair<String, ?>> param)
    {
        Uri.Builder builder = new Uri.Builder();
        for(Pair<String, ?> p : param)
        {
            builder.appendQueryParameter(p.first, String.valueOf(p.second));
        }
        String query = builder.build().getEncodedQuery();
        return query;
    }


    private String readStream(InputStreamReader isr)
    {
        BufferedReader br = new BufferedReader(isr);
        StringBuilder sb = new StringBuilder();

        String line;
        try {
            while(((line = br.readLine())) != null)
            {
                sb.append(line + "\n");
            }
        } catch (IOException e)
        {
            e.printStackTrace();
        }

        return sb.toString();
    }


}
