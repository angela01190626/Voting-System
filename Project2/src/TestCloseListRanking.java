/**
 * ---Here is the file header---
 * File name: TestcloseListRanking
 * Author: Yanjun Cui
 * Description: Since we did some modifications to the CPL ranking algorithm,
 * we created these test functions to test if the new CPL ranking algorithm
 * works. Each functions has description comments above itself. The main function
 * calls the test functions in different conditions.
 */

import java.util.*;
import java.io.File;
import java.io.IOException;

public class TestCloseListRanking{
    static String file1 =  "../testing/CPLexample.csv";
    static String file2 = "../testing/CPLexample_0seats.csv";
    static String file3 = "../testing/CPLexample_overflow.csv";
    static String file4 = "../testing/CPLexample2.csv";
    static String file5 = "../testing/CPLexample3.csv";

    /**
     * Test whether the CPL can get correct result and can be displayed correctly.
     * If the closeListRanking function returns the same results as expected,
     * then test function return true. Otherwise, return false.
     * <p>
     *
     * @param path the input file
     * @return boolean
     */

    public static boolean CPLtest1(String path) {//throws IOException{
        //  boolean result = true;
          try {
              CPLranking.closeListRanking(path);
            //  System.out.println(CPLranking.partyFinal.get("R").contains("Deutsch"));
              if (CPLranking.partyFinal.get("R").contains("Deutsch") && CPLranking.partyFinal.get("R").contains("Wong")
              && CPLranking.partyFinal.get("R").contains("Walters") && CPLranking.partyFinal.get("G").contains("Jones")
              && CPLranking.partyFinal.get("G").contains("Smith") && CPLranking.partyFinal.get("D").contains("Pike")
              && CPLranking.partyFinal.get("D").contains("Foster")) {
                  return true;
              }
              else{
                return false;
              }
          }
          catch(IOException e){
              return false;
          }
      }

    /**
     * Test whether the CPL works well for 0 seats condition.
     * If the hashmap is empty, return true, else false
     * <p>
     *
     * @param path the input file
     * @return boolean
     *
     */

     public static boolean CPLtest2(String path) {
           try {
               CPLranking.closeListRanking(path);
               // System.out.println(CPLranking.partyFinal);
               if (CPLranking.partyFinal.isEmpty()) return true;
               else return false;
           }
           catch(IOException e){
               return false;
           }
       }

     /**
      * Test whether the CPL works well for number of seats is larger than number of candidates condition.
      * If the result hashmap is the same as initial hashmap we read in, return true, else false
      * <p>
      *
      * @param path the input file
      * @return boolean
      */

     public static boolean CPLtest3(String path) {
           try {
               CPLranking.closeListRanking(path);
               // System.out.println(CPLranking.partyFinal);
               if (CPLranking.partyFinal == CPLranking.partyInit) return true;
               else return false;
           }
           catch(IOException e){
               return false;
           }
       }

     /**
      * Test whether the CPL works well for this case
      * If returns the same results as expected,, return true, else false
      * <p>
      *
      * @param path the input file
      * @return boolean
      *
      */
     public static boolean CPLtest4(String path) {
           try {
               CPLranking.closeListRanking(path);
               // System.out.println(CPLranking.partyFinal);
               if (CPLranking.partyFinal.get("R").contains("Deutsch") && CPLranking.partyFinal.get("R").contains("Wong")
               && CPLranking.partyFinal.get("R").contains("Walters") && CPLranking.partyFinal.get("G").contains("Jones")
               && CPLranking.partyFinal.get("G").contains("Smith") && CPLranking.partyFinal.get("I").contains("Perez")
               && CPLranking.partyFinal.get("K").contains("Amy")) return true;
               else return false;
           }
           catch(IOException e){
               return false;
           }
       }


       /**
        * Test whether the CPL works well for this case
        * If returns the same results as expected,, return true, else false
        * <p>
        *
        * @param path the input file
        * @return boolean
        *
        */
       public static boolean CPLtest5(String path) {
             try {
                 CPLranking.closeListRanking(path);
                 // System.out.println(CPLranking.partyFinal);
                 if (CPLranking.partyFinal.get("R").contains("Deutsch") && CPLranking.partyFinal.get("I").contains("Perez") && CPLranking.partyFinal.get("G").contains("Jones")) return true;
                 else return false;
             }
             catch(IOException e){
                 return false;
             }
         }

       /**
        * main function to run each Test case
        * When run one test case, you may need to comment other test cases
        *
        * @param args the main function arguments
        */

    public static void main(String[] args) {
            // System.out.println("Testcase1: test CPLexmaple.csv file");
            // boolean res1 = CPLtest1(file1);
            // if (res1){
            //     System.out.println("Test1 pass.");
            // }
            // else{
            //     System.out.println("Test1 fail");
            // }
            ///////////////////////////////////////////////////////////////////////////
            // System.out.println("Testcase2: test CPLexmaple_0seats.csv file");
            // boolean res2 = CPLtest2(file2);
            // if (res2){
            //     System.out.println("Test2 pass.");
            // }
            // else{
            //     System.out.println("Test2 fail");
            // }
            ///////////////////////////////////////////////////////////////////////////
            // System.out.println("Testcase3: test CPLexmaple_overflow.csv file");
            // boolean res3 = CPLtest3(file3);
            // if (res3){
            //     System.out.println("Test3 pass.");
            // }
            // else{
            //     System.out.println("Test3 fail");
            // }
            // ///////////////////////////////////////////////////////////////////////////
            // System.out.println("Testcase4: test CPLexmaple2.csv file");
            // boolean res4 = CPLtest4(file4);
            // if (res4){
            //     System.out.println("Test4 pass.");
            // }
            // else{
            //     System.out.println("Test4 fail");
            // }
            ///////////////////////////////////////////////////////////////////////////
            System.out.println("Testcase5: test CPLexmaple3.csv file");
            boolean res5 = CPLtest5(file5);
            if (res5){
                System.out.println("Test5 pass.");
            }
            else{
                System.out.println("Test5 fail");
            }
    }
}
