public class TestSelectFile {
    static voting_system instance = new voting_system();

    /**
     * Test the function if we input a invalid path.
     * If the selectFile returns false,then test function return true.
     * Otherwise, return false.
     * <p>
     * @param none
     * @return boolean
     *
     */
    public static boolean selectFileTest1(){
        boolean result = true;
        String path = "invaild";
        boolean retValue = instance.selectFile(path);
        if(retValue==false){
            result = false;
        }
        return result;
    }

    /**
     * Test the function if we input a valid path.
     * If the selectFile returns true,then test function return true.
     * Otherwise, return false.
     * <p>
     * @param none
     * @return boolean
     *
     */
    public static boolean selectFileTest2(){
        boolean result = true;
        String path = "../testing/OPLexample.csv";
        //We use the path from github, it might be changed if you want to run locally.
        boolean retValue = instance.selectFile(path);
        if(!retValue){
            result = false;
        }
        return result;
    }

    public static void main(String[] args) {
        // Test1
        boolean res1 = selectFileTest1();
        if (res1 == true){
            System.out.println("Test1 pass.");
        }
        else{
            System.out.println("Test1 fail.");
        }
        //==================================================
        // Test2
        boolean res2 = selectFileTest2();
        if (res2 == true){
            System.out.println("Test2 pass.");
        }
        else{
            System.out.println("Test2 fail.");
        }
    }
}
