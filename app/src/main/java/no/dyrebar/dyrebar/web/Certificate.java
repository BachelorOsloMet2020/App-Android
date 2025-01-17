package no.dyrebar.dyrebar.web;

import android.app.AlertDialog;
import android.content.Context;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManagerFactory;

import no.dyrebar.dyrebar.R;

public class Certificate
{
    private final String TAG = this.getClass().getName();
    public static SSLContext sslContext;

    public void InitializeCetificate(Context context)
    {
        try {
            CertificateFactory cf = CertificateFactory.getInstance("X.509");
            int certId = R.raw.cert; // R.raw.meet_vlab_cs_hioa_no
            InputStream caInput = context.getResources().openRawResource(certId);
            java.security.cert.Certificate ca;
            ca = cf.generateCertificate(caInput);
            System.out.println("ca=" + ((X509Certificate) ca).getSubjectDN());
            showWarningForCert(((X509Certificate)ca).getNotAfter());
// Create a KeyStore containing our trusted CAs
            String keyStoreType = KeyStore.getDefaultType();
            KeyStore keyStore = KeyStore.getInstance(keyStoreType);
            keyStore.load(null, null);
            keyStore.setCertificateEntry("ca", ca);

// Create a TrustManager that trusts the CAs in our KeyStore
            String tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
            TrustManagerFactory tmf = TrustManagerFactory.getInstance(tmfAlgorithm);
            tmf.init(keyStore);

// Create an SSLContext that uses our TrustManager
            sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, tmf.getTrustManagers(), null);

        } catch (CertificateException e) {
            e.printStackTrace();
            //caInput.close();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void showWarningForCert(Date date)
    {

        Calendar c = Calendar.getInstance(Locale.getDefault());

    }

    public static HostnameVerifier hostnameVerifier()
    {
        return new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, SSLSession session)
            {
                return true;
            }
        };
    }
}
