import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Main {

    private static HashMap<String, MyUrl> frontierContents;
    private static PriorityQueue<MyUrl> frontier;
    private static HashMap<String, MyUrl> visitedPages;
    private static final int MAX_PAGES = 10;
    private static long startTime;
    private static Index index;

    public static void main(String[] args) {
        startTime = System.currentTimeMillis();

        index = new Index();
        frontierContents = new HashMap<String, MyUrl>();
        visitedPages = new HashMap<String, MyUrl>();
        frontier = new PriorityQueue<MyUrl>(new Comparator<MyUrl>() {
            @Override
            public int compare(MyUrl url1, MyUrl url2) {
                if (url1.isSeed()) {
                    return -1;
                } else if (url2.isSeed()) {
                    return 1;
                }

                int res = url1.getNumInLinks().compareTo(url2.getNumInLinks())
                        * -1;

                if (res == 0) {
                    res = url1.getTimeStamp().compareTo(url2.getTimeStamp());
                }
                return res;
            }
        });

        // add seed urls
        // http://www.pbs.org/food/features/best-of-2013-review-food-blogs/
        // http://americanfoodbloggers.com/
        // http://www.huffingtonpost.com/news/best-food-blogs/

        MyUrl seed1 = new MyUrl(
                canonicalize("http://americanfoodbloggers.com"),
                System.currentTimeMillis(), true);
        MyUrl seed2 = new MyUrl(
                canonicalize("http://www.pbs.org/food/features/best-of-2013-review-food-blogs"),
                System.currentTimeMillis(), true);
        MyUrl seed3 = new MyUrl(
                canonicalize("http://www.huffingtonpost.com/news/best-food-blogs"),
                System.currentTimeMillis(), true);

        frontier.add(seed1);
        frontierContents.put(seed1.getUrl(), seed1);
        frontier.add(seed2);
        frontierContents.put(seed2.getUrl(), seed2);
        frontier.add(seed3);
        frontierContents.put(seed3.getUrl(), seed3);

        // crawl
        while (!frontier.isEmpty() && (visitedPages.size() < MAX_PAGES)) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }

            // get first element in frontier
            MyUrl nextElement = frontier.poll();
            String nextUrl = nextElement.getUrl();
            WebPage webpage = null;

            // Get the raw html, text, and outlinks
            try {
                Document doc = Jsoup.connect(nextUrl).get();
                Element body = doc.body();

                String html = body.outerHtml();
                String cleanText = body.text();
                ArrayList<String> outlinks = cleanUrls(doc.select("a[href]"));

                if ((html != null) && (cleanText != null)) {
                    webpage = new WebPage(nextUrl, html, cleanText, outlinks);
                } else {
                    throw new RuntimeException(
                            "Problem getting the required values (raw html, clean text) for the webpage: "
                                    + nextUrl);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

            // process outlinks
            for (String url : webpage.getOutlinks()) {
                processOutlink(url, nextUrl);
            }

            // index the webpage
            index.indexPage(webpage);

            visitedPages.put(nextUrl, nextElement);
            frontierContents.remove(nextUrl);
        }

        addInLinks();
        testResult();
    }

    private static void addInLinks() {
        for (String url : visitedPages.keySet()) {
            index.addInLinks(visitedPages.get(url));
        }
    }

    private static void testResult() {
        System.out.println();
        for (String url : visitedPages.keySet()) {
            System.out.print(visitedPages.get(url).prettyPrint());
        }
        System.out.println("Number of visitedPages: " + visitedPages.size());
        System.out.println();
        System.out.println("Number of pages still in frontier: "
                + frontier.size());
        System.out.println();
        System.out.println("Runtime: "
                + (System.currentTimeMillis() - startTime));
    }

    private static void processOutlink(String url, String sourceOfLink) {
        if (visitedPages.containsKey(url)) {
            visitedPages.get(url).addInLink(sourceOfLink);
        } else if (frontierContents.containsKey(url)) {
            MyUrl page = frontierContents.get(url);
            frontier.remove(page);
            page.addInLink(sourceOfLink);
            frontier.add(page);
        } else {
            MyUrl page = new MyUrl(url, System.currentTimeMillis(), false);
            page.addInLink(sourceOfLink);
            frontier.add(page);
            frontierContents.put(url, page);
        }
    }

    private static ArrayList<String> cleanUrls(Elements outlinks) {
        ArrayList<String> cleanOutlinks = new ArrayList<String>();

        for (Element link : outlinks) {
            String url = link.attr("abs:href");
            if (url.equals("")) {
                continue;
            }
            url = canonicalize(url);
            cleanOutlinks.add(url);
        }

        return cleanOutlinks;
    }

    public static String canonicalize(String urlString) {
        String result = null;
        try {
            URL url = new URL(urlString.toLowerCase());
            result = url.getProtocol() + "://" + url.getHost() + url.getPath();
        } catch (MalformedURLException e) {
            System.out.println("Malformed url: " + urlString);
        }
        return result;
    }
}
