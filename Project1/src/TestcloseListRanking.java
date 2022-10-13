import java.io.*;
import java.util.*;
import static java.util.stream.Collectors.*;
import static java.util.Map.Entry.*;

public class TestcloseListRanking{
    static voting_system instance = new voting_system();
    static String []expected_partylist = {"G", "R", "I", "D"};
    static HashMap<String, Integer> expected_voteCount = new HashMap<>();
    static HashMap<String, Integer> expected_seatCount1 = new HashMap<>();
    /**
     * Test whether the CPL can get correct result and can be displayed correctly.
     * If the closeListRanking function returns the same results as expected,
     * then test function return true. Otherwise, return false.
     * <p>
     *
     * @param path the input file
     * @return boolean
     */

    public static boolean CPLtest1(String path) throws IOException{
        expected_voteCount.put("G", 4);
        expected_voteCount.put("R", 5);
        expected_voteCount.put("I", 1);
        expected_voteCount.put("D", 3);
        expected_seatCount1.put("G", 2);
        expected_seatCount1.put("R", 3);
        expected_seatCount1.put("D", 2);
        expected_seatCount1.put("I", 0);
        instance.closeListRanking(path);
        instance.displayResultcpl();
        if (instance.numberofparties == 4 && instance.numofseats == 7 && instance.numofcandidates == 16) {
            for (int i = 0; i < expected_partylist.length; i++){
              if (instance.voteCount.get(expected_partylist[i]) != expected_voteCount.get(expected_partylist[i])){
                return false;
              }
            }
            for (int i = 0; i < expected_partylist.length; i++){
              if (instance.seatCount.get(expected_partylist[i]) != expected_seatCount1.get(expected_partylist[i])){
                return false;
              }
            }
            return true;
        }
        else{
          return false;
        }
    }

    /**
     * Test whether the CPL can get FileNotFoundException if input an invalid path
     * <p>
     *
     * @param path the input file
     * @return none
     */

    public static void CPLtest2(String path) throws IOException{
        instance.closeListRanking(path);
    }

    public static void main(String[] args) {
        try {
            String path1 = "./testing/CPLexample.csv";
            System.out.println("Testcase1: successully print out");
            boolean res1 = CPLtest1(path1);
            if (res1){
                System.out.println("Test1 pass.");
            }
            else{
                System.out.println("Test1 fail");
            }

            String path2 = "./testing/CPL.csv";
            System.out.println("\nTestcase2: invalid path");
            CPLtest2(path2);
        }catch(IOException e) { e.printStackTrace(); }
    }
}
