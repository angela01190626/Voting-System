/**
 * ---Here is the file header---
 * File name: TestOpenListRanking
 * Author: Yangjiawen Xu
 * Description: Since we did some modifications to the OPL ranking algorithm, 
 * we created these test functions to test if the new OPL ranking algorithm
 * works. Each functions has description comments above itself. The main function
 * calls the test functions in different conditions. 
 */

public class TestOpenListRanking{
    static String file1 = "../testing/OPLexample.csv";
    static String file2 = "../testing/OPLexample_0seats.csv";
    static String file3 = "../testing/OPLexample_overflow.csv";
    static String file4 = "../testing/OPLexample2.csv";
    static String file5 = "../testing/OPLexample3.csv";

    /**
     * Test whether the OPL can get correct result and can be displayed correctly.
     * If the closeListRanking function returns the same results as expected,
     * then test function return true. Otherwise, return false.
     * <p>
     *
     * @param path the input file
     * @return boolean
     */

    public static boolean OPLtest1(String path) {
        //try {
        OPLranking.openListRanking(path);
        if (OPLranking.showWinner().equals("Pike") && OPLranking.winner.size() == 1) {
            return true;
        } else if (OPLranking.winner.size() > 1 && (OPLranking.numofseats < OPLranking.winner.size())) {
            System.out.println("There are candidates have equal ballots, break tie needed.");
            return true;
        } else if (OPLranking.numofseats == 0) {
            System.out.println("There are no seats");
            return true;
        } else if (OPLranking.numofseats > OPLranking.winner.size()) {
            System.out.println("There are overflow, all the candidates will be selected.");
            return true;
        } else {
            return false;
        }
    }

    /**
     * main function to run each Test case
     * When run one test case, you may need to comment other test cases
     * <p>
     *
     * @param args the main function arguments
     */

    public static void main(String[] args) {
        System.out.println("Testcase1: test OPLexmaple.csv file");
        boolean res1 = OPLtest1(file1);
        if (res1){
            System.out.println("Test1 pass.");
        }
        else{
            System.out.println("Test1 fail");
        }
        /////////////////////////////////////////////////////////////////////////
        System.out.println("Testcase2: test OPLexmaple2.csv file");
        boolean res2 = OPLtest1(file4);
        if (res2){
            System.out.println("Test2 pass.");
        }
        else{
            System.out.println("Test2 fail");
        }
        /////////////////////////////////////////////////////////////////////////
        System.out.println("Testcase3: test OPLexmaple_overflow.csv file");
        boolean res3 = OPLtest1(file5);
        if (res3){
            System.out.println("Large test file is used.");
            System.out.println("Test3 pass.");
        }
        else{
            System.out.println("Test3 fail");
        }
        System.out.println("Testcase4: test OPLexmaple3.csv file");
        boolean res4 = OPLtest1(file5);
        if (res4){
            System.out.println("Test4 pass.");
        }
        else{
            System.out.println("Test4 fail");
        }
        /////////////////////////////////////////////////////////////////////////
        System.out.println("Testcase5: test OPLexmaple_0seats.csv file");
        boolean res5 = OPLtest1(file2);
        if (res5){
            System.out.println("Test5 pass.");
        }
        else {
            System.out.println("Test5 fail");
        }
    }
}
