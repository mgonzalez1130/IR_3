import java.util.Comparator;
import java.util.Iterator;
import java.util.TreeSet;

public class testingStuff {

    public static void main(String[] args) {

        MyUrl url1 = new MyUrl("www.google.com", 1);
        MyUrl url2 = new MyUrl("www.facebook.com", 2);
        MyUrl url3 = new MyUrl("www.yahoo.com", 3);
        MyUrl url4 = new MyUrl("www.yahoo.com", 4);

        TreeSet<MyUrl> testSet = new TreeSet<MyUrl>(new Comparator<MyUrl>() {
            @Override
            public int compare(MyUrl url1, MyUrl url2) {
                int res = url1.getInLinks().compareTo(url2.getInLinks());
                return res != 0 ? res : 1;
            }
        });

        testSet.add(url3);
        testSet.add(url2);
        testSet.add(url1);
        testSet.add(url4);
        testSet.remove(url3);

        if (testSet.contains(url3)) {
            testSet.remove(url3);
            testSet.add(url4);
        }

        System.out.println("Size: " + testSet.size());

        Iterator<MyUrl> it = testSet.iterator();

        while (it.hasNext()) {
            MyUrl next = it.next();
            System.out.println("Url: " + next.getUrl() + "  InLinks: "
                    + next.getInLinks());
        }
    }

}
