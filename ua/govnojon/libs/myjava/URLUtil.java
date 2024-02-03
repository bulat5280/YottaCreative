package ua.govnojon.libs.myjava;

import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

public class URLUtil {
   public static String postRequest(String url, String... params) {
      return (String)Try.ignore((Try.SupplierThrows)(() -> {
         HttpURLConnection conn = (HttpURLConnection)(new URL(url)).openConnection();
         conn.setReadTimeout(10000);
         conn.setConnectTimeout(15000);
         conn.setRequestMethod("POST");
         conn.setDoInput(true);
         conn.setDoOutput(true);
         List<NameValuePair> datas = new LinkedList();

         for(int i = 0; i < params.length; i += 2) {
            datas.add(new BasicNameValuePair(params[i], params[i + 1]));
         }

         OutputStream os = conn.getOutputStream();
         BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, StandardCharsets.UTF_8));
         writer.write(getQuery(datas));
         writer.flush();
         writer.close();
         os.close();
         conn.connect();
         InputStream stream = conn.getInputStream();
         byte[] bytes = new byte[stream.available()];
         stream.read(bytes);
         return new String(bytes);
      }), (Object)null);
   }

   private static String getQuery(List<NameValuePair> params) throws UnsupportedEncodingException {
      StringBuilder result = new StringBuilder();
      boolean first = true;
      Iterator var3 = params.iterator();

      while(var3.hasNext()) {
         NameValuePair pair = (NameValuePair)var3.next();
         if (first) {
            first = false;
         } else {
            result.append("&");
         }

         result.append(URLEncoder.encode(pair.getName(), "UTF-8"));
         result.append("=");
         result.append(URLEncoder.encode(pair.getValue(), "UTF-8"));
      }

      return result.toString();
   }
}
