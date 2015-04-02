import java.io.FileWriter;
import java.io.IOException;

public class LinkGraph {

    public static void main(String args[]) {
        FileWriter pw = null;
        try {
            pw = new FileWriter("LinkGraph", true);
            writeOutLinks();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                pw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    private static void writeOutLinks() {
        // TODO Auto-generated method stub

    }

}
