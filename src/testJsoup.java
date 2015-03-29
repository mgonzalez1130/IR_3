import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class testJsoup {

    public static void main(String args[]) {

        Document doc = null;
        try {
            doc = Jsoup.connect(
                    "http://www.huffingtonpost.com/news/best-food-blogs/")
                    .get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Elements links = doc.select("a[href]");
        for (Element link : links) {
            System.out.println("\nlink : " + link.attr("abs:href"));
            System.out.println("text : " + link.text());
        }

    }

}
