import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class TestLogin {
    static voting_system instance = new voting_system();

    /**
     * Test can we login successfully. Register first, then login.
     * Test whether user could login successfully. If succeed, then return true. Otherwise, return false.
     * <p>
     *
     * @param none
     * @return boolean
     */
    public static boolean loginTest1() {
        boolean result = true;
        String username = "HanzhangWu";
        String password = "OneShotForSeven";
        boolean register = instance.register(username, password);
        if (register) {
            //System.out.println("login Status："+instance.loginStatus);
            instance.logout();
            boolean retValue = instance.login(username, password);
            if (!retValue) {
                result = false;
            }
            if (!instance.loginStatus) {
                result = false;
            }
        } else {
            result = false;
        }
        return result;
    }

    /**
     * Test if login function could block incorrect password. Register first, then login by incorrect password five times.
     * Test if we could not login, then return true. Otherwise, return false.
     * <p>
     *
     * @param none
     * @return boolean
     */
    public static boolean loginTest2() {
        boolean result = true;
        String username = "HanzhangWu";
        String password = "OneShotForSeven";
        boolean register = instance.register(username, password);
        if (register) {
            instance.logout();
            System.out.println("login Status：" + instance.loginStatus);
            InputStream sysInBackup = System.in; // backup System.in to restore it later
            ByteArrayInputStream in = new ByteArrayInputStream(("HanzhangWu" + System.lineSeparator() + "One" +
                    System.lineSeparator() +
                    "Shot" + System.lineSeparator() + "For" + System.lineSeparator() +
                    "Seven" + System.lineSeparator() + "Seconds").getBytes());
            System.setIn(in);
            boolean retValue = instance.login(username, "OneShotForSevenSeconds");
            System.setIn(sysInBackup);
            if (retValue) {
                result = false;
            }
            if (instance.loginStatus) {
                result = false;
            }
        } else {
            result = false;
        }
        return result;
    }

    /**
     * Test if login function could block incorrect password and pass the correct password.
     * Register first, then login by incorrect password for one time. Then use correct password to login.
     * Test if we could login, then return true. Otherwise, return false.
     * <p>
     *
     * @param none
     * @return boolean
     */
    public static boolean loginTest3() {
        boolean result = true;
        String username = "HanzhangWu";
        String password = "OneShotForSeven";
        boolean register = instance.register(username, password);
        if (register) {
            instance.logout();
            InputStream sysInBackup = System.in; // backup System.in to restore it later
            ByteArrayInputStream in = new ByteArrayInputStream(("OneShotForSeven").getBytes());
            System.setIn(in);
            boolean retValue = instance.login(username, "OneShotForSevenSeconds");
            System.setIn(sysInBackup);
            if (!retValue) {
                result = false;
            }
            if (!instance.loginStatus) {
                result = false;
            }
        } else {
            result = false;
        }
        return result;
    }

    public static void main(String[] args) {
        boolean result1 = loginTest1();
        if (result1) {
            System.out.println("\nTest1 pass\n");
        } else {
            System.out.println("\nTest1 failed");
        }

        boolean result2 = loginTest2();
        if (result2) {
            System.out.println("\nTest2 pass\n");
        } else {
            System.out.println("\nTest2 failed");
        }

        boolean result3 = loginTest3();
        if (result3) {
            System.out.println("\nTest3 pass\n");
        } else {
            System.out.println("\nTest3 failed");
        }
    }
}
