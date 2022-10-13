import java.io.*;
import java.util.HashMap;

public class TestOpenListRanking {
    static voting_system instance = new voting_system();
    static String[] arrayparty;

    /**
     * This is only a helper function, which will generate the correct opl ranking result.
     * <p>
     * @param none
     * @return boolean
     *
     */
    private static HashMap<String, Integer> readOPLFileContent(String fileName) {
        HashMap<String, Integer> candidatesmapActual = new HashMap<>();
        try{
            BufferedReader br = new BufferedReader(new FileReader(fileName));
            String line = "";
            int count = 0;
            int c= 0;
            while ((line = br.readLine()) != null) {
                if(count == 3){
                    int numofcandidates = Integer.parseInt(line);
                    arrayparty = new String[numofcandidates];
                }
                if (count >= 4) {
                    if (!line.contains("1")) {
                        line = line.replaceAll("[\\[\\]]","");
                        String[] candidates_party = line.split(",");
                        candidatesmapActual.put(candidates_party[0], 0);
                        arrayparty[c] = candidates_party[0];
                        c++;
                    } else {
                        int num = candidatesmapActual.get(arrayparty[line.indexOf("1")]);
                        candidatesmapActual.put(arrayparty[line.indexOf("1")], num + 1);
                    }
                }
                count++;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return candidatesmapActual;
    }

    /**
     * Test can we run a OPL ranking successfully.
     * Input a valid OPL CSV file, test if the voting system could be generated .
     * If we could detect the correct username and password, then return true. Otherwise, return false.
     * <p>
     * @param none
     * @return boolean
     *
     */
    public static boolean OPLRankingTest1(String csvfile){
        boolean result = true;
        HashMap<String, Integer> candidatesmapActual = readOPLFileContent(csvfile);
        instance.openListRanking(csvfile);
        if(!candidatesmapActual.equals(instance.candidatesmap)){
            result = false;
        }
        return result;
    }

    /**
     * Test can the OPL ranking catch an exception.
     * Input an invalid file, test if the voting system could throw an exception .
     * If we can catch the exception thrown, then return true. Otherwise, return false.
     * <p>
     * @param none
     * @return boolean
     *
     */
    public static boolean OPLRankingTest2(String csvfile){
        boolean result = false;
        instance.openListRanking(csvfile);
        if(instance.exceptionCatch){
            result = true;
        }
        return result;
    }

    public static void main(String[] args){
//        instance.openListRanking("../testing/OPLexample2.csv");
//        HashMap<String, Integer> candidatesmapActual = readOPLFileContent("../testing/OPLexample2.csv");
//        System.out.println("===============================");
//        candidatesmapActual.entrySet().forEach(entry->{
//            System.out.println(entry.getKey() + " " + entry.getValue());
//        });
//        instance.candidatesmap.entrySet().forEach(entry->{
//            System.out.println(entry.getKey() + " " + entry.getValue());
//        });
//        System.out.println("===============================");
        boolean result1 = OPLRankingTest1("./testing/OPLexample.csv");
        if(result1){System.out.println("\nTest1 passed\n");}
        else{System.out.println("\nTest1 failed");}

        boolean result2 = OPLRankingTest1("./testing/OPLexample2.csv");
        if(result2){System.out.println("\nTest2 passed\n");}
        else{System.out.println("\nTest2 failed");}

        boolean result3 = OPLRankingTest2("aaa");
        if(result3){System.out.println("\nTest3 passed\n");}
        else{System.out.println("\nTest3 failed");}
    }
}
