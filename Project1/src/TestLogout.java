public class TestLogout {
    static voting_system instance = new voting_system();

    /**
     * Test can user logout if the loginStatus is already false.
     * If the logout return value is false,then return true.
     * Otherwise, return false
     * <p>
     * @param none
     * @return boolean
     *
     */
    public static boolean logoutTest1(){
        boolean result = true;
        boolean retValue = instance.logout();
        if(retValue){
            result = false;
        }
        return result;
    }

    /**
     * Test can user logout successfully.
     * At first the loginStatus is true, use logout function to logout.
     * If loginStatus successfully changed from true to false, and the return value of logout function is true,
     * then return true. Otherwise, return false.
     * <p>
     * @param none
     * @return boolean
     *
     */
    public static boolean logoutTest2(){
        boolean result = true;
        instance.loginStatus = true;
        Boolean retValue = instance.logout();
        if(instance.loginStatus){
            result = false;
        }
        if(!retValue){
            result = false;
        }
        return result;
    }

    public static void main(String[] args) {
        // Test 1
        boolean res1 = logoutTest1();
        if(res1){
            System.out.println("\nTest1 pass\n");
        }else{
            System.out.println("\nTest1 failed\n");
        }
        // Test 2
        boolean res2 = logoutTest2();
        if(res2){
            System.out.println("\nTest2 pass");
        }else{
            System.out.println("\nTest2 failed");
        }
    }
}
