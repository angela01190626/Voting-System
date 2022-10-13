import java.io.File;
import java.io.IOException;

public class TestSendToMediacpl {
    static voting_system instance = new voting_system();
    static String csvfile = "./testing/CPLexample.csv";

    /**
     * Test can we send to media successfully.
     * If we could detect the media file, then return true. Otherwise, return false.
     */
    public static boolean SendToMediacplTest1(String path) throws IOException {
        boolean result = true;
        instance.closeListRanking(csvfile);
        try {
            instance.sendToMediacpl();
        } catch (IOException e) {
            result = false;
        }
        File file = new File(path);
        if (!file.exists()) {
            result = false;
        }
        return result;
    }

    /**
     * Test can we generate throw an exception successfully.
     * But before test, we must block the function working working correctly,
     * which needs to be finished outside of code.
     * If we could detect the exception, then return true. Otherwise, return false.
     */
    public static boolean SendToMediacplTest2() throws IOException {
        boolean result = false;
        instance.sendToMediacpl();
        if (instance.numofseats == 0 || instance.numofballots == 0 || instance.numberofparties == 0 || instance.numofcandidates == 0) {
            result = true;
        }
        return result;
    }

    public static void main(String[] args) {
        try {
            boolean result2 = SendToMediacplTest2();
            if (result2) {
                System.out.println("\nTest1 pass\n");
            } else {
                System.out.println("\nTest1 failed");
            }
            boolean result1 = SendToMediacplTest1("mediacpl.txt");
            if (result1) {
                System.out.println("\nTest2 pass\n");
            } else {
                System.out.println("\nTest2 failed");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
