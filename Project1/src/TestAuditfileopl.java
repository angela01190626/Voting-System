import java.io.File;
import java.io.IOException;

public class TestAuditfileopl {
    static voting_system instance = new voting_system();
    static String csvfile = "./testing/OPLexample2.csv";

    /**
     * Test can we generate audit file successfully.
     * If we could detect the audit file, then return true. Otherwise, return false.*
     */
    public static boolean AuditFileoplTest1(String path){
        boolean result = true;
        instance.openListRanking(csvfile);
        try{
            instance.auditfileopl();
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

    /**
     * Test can we generate throw an exception successfully.
     * But before test, we must block the function working working correctly,
     * which needs to be finished outside of code.
     * If we could detect the exception, then return true. Otherwise, return false.
     */
    public static boolean AuditFileoplTest2(){
        boolean result = false;
        instance.openListRanking(csvfile);
        try{
            instance.auditfileopl();
        }
        catch(IOException e){
            result = true;
        }
        return result;
    }

    public static void main(String[] args){
        boolean result1 = AuditFileoplTest1("./auditopl.txt");
        boolean result2 = AuditFileoplTest2();
        if(result1){System.out.println("\nTest1 pass\n");}
        else{System.out.println("\nTest1 failed");}
        if(result2){System.out.println("\nTest2 pass\n");}
        else{System.out.println("\nTest2 failed");}
    }
}
