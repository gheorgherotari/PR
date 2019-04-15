import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class App {

    private final String USER_AGENT = "Mozilla/5.0";

    public static void main(String[] args) throws Exception {

        App http = new App();

        System.out.println("Testing 1 - Send Http GET request");
        http.sendGet();

        System.out.println("\nTesting 2 - Send Http HEAD request");
        http.sendHead();

        System.out.println("\nTesting 3 - Send Http POST request");
        http.sendPost();
    }

    // HTTP GET request
    private void sendGet() throws Exception {

        String url = "http://httpbin.org/ip";

        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet request = new HttpGet(url);

        // add request header
        request.addHeader("User-Agent", USER_AGENT);

        HttpResponse response = httpClient.execute(request);

        System.out.println("\nSending 'GET' request to URL : " + url);
        System.out.println("Response Code : " +
                response.getStatusLine().getStatusCode());

        BufferedReader rd = new BufferedReader(
                new InputStreamReader(response.getEntity().getContent()));

        StringBuffer result = new StringBuffer();
        String line = "";
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }

        System.out.println(result.toString());

    }

    // HTTP HEAD request
    private void sendHead() throws Exception {

        String url = "http://httpbin.org/headers";

        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpHead request = new HttpHead(url);

        // add request header
        HttpResponse response = httpClient.execute(request);

        System.out.println("\nSending 'HEAD' request to URL : " + url);
        System.out.println("Response Code : " + response.getStatusLine().getStatusCode());
        Header[] headers = response.getAllHeaders();
        for (Header header : headers) {
            System.out.println(" --> " + header.getName() + ":" + header.getValue());
        }
    }

    // HTTP POST request
    private void sendPost() throws Exception {

        String url = "http://httpbin.org/post";

        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost request = new HttpPost(url);

        // add header
        request.setHeader("User-Agent", USER_AGENT);

        List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
        urlParameters.add(new BasicNameValuePair("name", "Gheorghe"));
        urlParameters.add(new BasicNameValuePair("username", "krashcorp"));


        request.setEntity(new UrlEncodedFormEntity(urlParameters));

        HttpResponse response = httpClient.execute(request);
        System.out.println("\nSending 'POST' request to URL : " + url);
        System.out.println("Response Code : " +
                response.getStatusLine().getStatusCode());

        BufferedReader rd = new BufferedReader(
                new InputStreamReader(response.getEntity().getContent()));

        StringBuffer result = new StringBuffer();
        String line = "";
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }

        System.out.println(result.toString());

    }
}