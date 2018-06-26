package com.ibm.br;

import javax.net.ssl.*;
import java.io.*;
import java.net.URL;
import java.security.KeyStore;
import java.util.Map;


/**
 * "truststore-file=file.jks"
 * "truststore-password=password"
 * "https-url=https://localhost:8888"
 * "version=TLSv1.2"
 * "provider=SunX509"
 */
public class Main {

    private static final String TARGET = "https-url";

    private static final String PASS = "truststore-password";

    private static final String TRUSTSTORE = "truststore-file";

    private static final String PROVIDER = "provider";

    private static final String TLS_VERSION = "version";

    static {
        System.setProperty("javax.net.debug","ssl");
    }

    public static void main(String[] args) throws IOException {
        Map<String,String> properties = new ArgumentReader().readProperties(args);

        SSLContext sslContext = createSSLContext(properties);

        SSLSocketFactory sslsocketfactory = sslContext.getSocketFactory();
        URL url = new URL(properties.getOrDefault(TARGET,"https://localhost:443"));
        HttpsURLConnection conn = (HttpsURLConnection)url.openConnection();
        conn.setSSLSocketFactory(sslsocketfactory);

        try {
            System.out.println("Trying to connect to: "+properties.get(TARGET));
            InputStream inputstream = conn.getInputStream();
            InputStreamReader inputstreamreader = new InputStreamReader(inputstream);
            BufferedReader bufferedreader = new BufferedReader(inputstreamreader);

            System.out.println("\n\n\n######################################################");
            System.out.println("##               Truststore works!                  ##");
            System.out.println("######################################################\n\n\n");
            String string = null;
            while ((string = bufferedreader.readLine()) != null) {
                System.out.println("Received from server:" + string);
            }
        }
        catch (RuntimeException rex) {
            rex.printStackTrace();
        }
    }

    private static SSLContext createSSLContext(Map<String,String> args){
        String path = args.getOrDefault(TRUSTSTORE, "./truststore.jks");
        String password = args.getOrDefault(PASS, "password");
        String provider = args.getOrDefault(PROVIDER, "SunX509");
        String version = args.getOrDefault(TLS_VERSION, "TLSv1.2");

        if (!new File(path).exists()) {
            throw new RuntimeException("Truststore file: "+path+" not found.");
        }

        try{
            KeyStore keyStore = KeyStore.getInstance("JKS");
            keyStore.load(new FileInputStream(path),password.toCharArray());

            KeyManager[] km = {};
            //KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance("SunX509");
            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(provider);
            trustManagerFactory.init(keyStore);
            TrustManager[] tm = trustManagerFactory.getTrustManagers();

            // Initialize SSLContext
            SSLContext sslContext = SSLContext.getInstance(version);
            sslContext.init(km,tm, null);

            return sslContext;
        } catch (Exception ex){
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }
}
