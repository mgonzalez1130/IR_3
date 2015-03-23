import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class Main {

    public static void main(String[] args) {

        // webpages left to crawl, in-links seen so far
        HashMap<String, Integer> frontier = new HashMap<String, Integer>();
        // webpages already crawled, all in-links
        HashMap<String, ArrayList<String>> inLinks = new HashMap<String, ArrayList<String>>();

        // add seed urls

        // frontier
        while (!frontier.isEmpty()) {

            // get first element in frontier

            // canonicalize url

            // check if url has already been crawled

            // if it hasn't, get the raw html, text, and outlinks

            // index those values

        }
    }

    public String canonicalize(String urlString) {
        String result = null;
        try {
            URL url = new URL(urlString.toLowerCase());
            result = url.getProtocol() + "://www." + url.getHost()
                    + url.getPath();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return result;
    }
}
