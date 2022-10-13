/**
 * ---Here is the file header---
 * File name: TestAuditfileCPL
 * Author: Jerry Nie
 * Description: Since we did some modifications to the Audit File generating function,
 * we created these test functions to test if the new Audit File generating function
 * works. Each functions has description comments above itself. The main function
 * calls the test functions in different conditions.
 */

import java.io.File;
import java.io.IOException;

public class TestAuditfileCPL {
    static String csvfile = "../testing/CPLexample3.csv";
    //static String csvfile =  "../testing/CPLexample.csv";
    //static String csvfile = "../testing/CPLexample_0seats.csv";
    //static String csvfile = "../testing/CPLexample_overflow.csv";
    //static String csvfile = "../testing/CPLexample2.csv";
    static String path = ".";//audit file path, It is different on different computers.

    /**
     * Test can we generate audit file successfully.
     * If we could detect the audit file, then return true. Otherwise, return false.
     *
     * @param csv the input csv file path
     * @return true/false
     */
    public static boolean AuditFileCPLTest1(String csv){
        boolean result = true;
        try {
            CPLranking.closeListRanking(csv);
        }
        catch(IOException e){
            result = false;
        }
        try{
            CPLranking.auditfilecpl();
        }
        catch(IOException e){
            result = false;
        }
        File file = new File(path);
        if(!file.exists()){
            result = false;
        }
        return result;
    }

//     /**
//      * Test can we generate throw an exception successfully.
//      * But before test, we must block the function working working correctly,
//      * which needs to be finished outside of code.
//      * If we could detect the exception, then return true. Otherwise, return false.
//      */
//     public static boolean AuditFileCPLTest2(){
//         boolean result = false;
//         try {
//             CPLranking.closeListRanking(csvfile);
//         }
//         catch(IOException e){
//             result = false;
//         }
//         try{
//             CPLranking.auditfilecpl();
//         }
//         catch(IOException e){
//             result = true;
//         }
//         return result;
//     }

    public static void main(String[] args){
        boolean result1 = AuditFileCPLTest1(csvfile);
        if(result1){System.out.println("\nTest pass\n");}
        else{System.out.println("\nTest failed");}
//         boolean result2 = AuditFileCPLTest2();
//         if(result2){System.out.println("\nTest2 pass\n");}
//         else{System.out.println("\nTest2 failed");}
    }
}
