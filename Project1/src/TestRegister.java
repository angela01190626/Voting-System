import java.io.*;
import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class TestRegister {
    static voting_system instance = new voting_system();

    /**
     * This is only a helper function, which convert file content into a string.
     * <p>
     *
     * @param none
     * @return boolean
     */
    private static String readFileContent(String fileName) {
        File file = new File(fileName);
        BufferedReader reader = null;
        StringBuffer sbf = new StringBuffer();
        try {
            reader = new BufferedReader(new FileReader(file));
            String tempStr;
            while ((tempStr = reader.readLine()) != null) {
                sbf.append(tempStr);
            }
            reader.close();
            return sbf.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
        return sbf.toString();
    }

    /**
     * Test can we register successfully.
     * Input a valid username and password, test if it could be generated into userinfo file.
     * If we could detect the correct username and password, then return true. Otherwise, return false.
     * <p>
     *
     * @param none
     * @return boolean
     */
    public static boolean registerTest1(String username, String password) {
        boolean result = true;
        boolean retValue = instance.register(username, password);
        String expect = username + " " + password;
        String content = TestRegister.readFileContent("userinfo.txt");
        ;
        if (!content.contains(expect)) {//不包含
            result = false;
        }
        if (!retValue) {//返回值不对
            result = false;
        }
        return result;
    }

    /**
     * Test whether the test system can prevent registrations that do not meet the requirements.
     * Input invalid username and password, test whether it will be written to the user information file.
     * Then keep input invalid password, until all five times are ran out of.
     * If we could detect the invalid username and password, then return false. Otherwise, return true.
     * <p>
     *
     * @param none
     * @return boolean
     */
    public static boolean registerTest2(String username, String password) {//Basic test, test Test whether the program can successfully create a user account
        boolean result = true;
        InputStream sysInBackup = System.in; // backup System.in to restore it later
        ByteArrayInputStream in = new ByteArrayInputStream(("OneShotForSevenSeconds" + System.lineSeparator() +
                "OneShotForSevenSeconds" + System.lineSeparator() + "OneShotForSevenSeconds" + System.lineSeparator() +
                "OneShotForSevenSeconds" + System.lineSeparator() + "OneShotForSevenSeconds").getBytes());
        System.setIn(in);
        boolean retValue = instance.register(username, password);
        System.setIn(sysInBackup);
        String expect = username + " " + password;
        String content = TestRegister.readFileContent("userinfo.txt");
        if (content.contains(expect)) {
            result = false;
        }
        if (retValue) {
            result = false;
        }
        return result;
    }

    /**
     * Test whether the test system can prevent registrations that do not meet the requirements.
     * Input invalid username and password at first, test whether it will be written to the user information file.
     * Then input valid password, test if the correct password could be stored.
     * If we can not detect the invalid username and password and we can detect the correct user name with password,
     * then return false. Otherwise, return true.
     * <p>
     *
     * @param none
     * @return boolean
     */
    public static boolean registerTest3(String username, String password) {//Basic test, test Test whether the program can successfully create a user account
        boolean result = true;
        InputStream sysInBackup = System.in; // backup System.in to restore it later
        ByteArrayInputStream in = new ByteArrayInputStream(("OneShotForSeven").getBytes());
        System.setIn(in);
        boolean retValue = instance.register(username, password);
        System.setIn(sysInBackup);
        String invalid = username + " " + password;
        String valid = username + " " + "OneShotForSeven";
        String content = TestRegister.readFileContent("userinfo.txt");
        if (content.contains(invalid)) {
            result = false;
        }
        if (!retValue) {
            result = false;
        }
        if (!content.contains(valid)) {
            result = false;
        }
        return result;
    }

    public static void main(String[] args) {
        // To test each case, you may need to comment other cases.
        //test1
        String content = readFileContent("userinfo.txt");
        System.out.println(content);
        boolean result = registerTest1("HanzhangWu", "OneShotForSeven");

        System.out.println(result);
        String content2 = readFileContent("userinfo.txt");
        System.out.println(content2);
        if (result == true) {
            System.out.println("\nTest1 pass.");
        } else {
            System.out.println("\nTest1 fail.");
        }
        //==============================================================================================================
        //test2
//        String content = readFileContent("userinfo.txt");
//        System.out.println(content);
//        boolean result = registerTest2("HanzhangWu", "OneShotForSevenSeconds");
//
//        System.out.println(result);
//        String content2 = readFileContent("userinfo.txt");
//        System.out.println(content2);
//        if (result == true) {
//            System.out.println("\nTest2 pass.");
//        } else {
//            System.out.println("\nTest2 fail.");
//        }
        //==============================================================================================================
        //test3
//        String content = readFileContent("userinfo.txt");
//        System.out.println(content);
//        boolean result = registerTest3("HanzhangWu", "OneShotForSevenSeconds");
//
//        System.out.println(result);
//        String content2 = readFileContent("userinfo.txt");
//        System.out.println(content2);
//        if (result == true) {
//            System.out.println("Test3 pass.");
//        } else {
//            System.out.println("Test3 fail.");
//        }
    }
}
