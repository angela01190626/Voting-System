import java.util.Collections;
import java.util.Map;

public class TestShowWinner {
    static voting_system instance = new voting_system();

    /**
     * Test whether the function can find the winner correctly.
     * If the showWinner function returns the same name of the real winner,
     * then test function return true. Otherwise, return false.
     * <p>
     * @return boolean
     *
     */
    public static boolean showWinnerTest1(){
        boolean result = false;
        int max = Collections.max(instance.candidatesmap.values());
        int count = 0;
        for (Map.Entry<String, Integer> entry : instance.candidatesmap.entrySet()) {
            if (entry.getValue().equals(max)) {
                count++;
            }
        }
        String[] winnerList = new String[count];
        for(int i = 0; i<count; i++){
            for (Map.Entry<String, Integer> entry : instance.candidatesmap.entrySet()) {
                if (entry.getValue().equals(max)) {
                    winnerList[i] = entry.getKey();
                }
            }
        }
        String retValue = instance.showWinner();
        for (int i = 0; i<count; i++){
            if(retValue.equals(winnerList[i])){
                result = true;
            }
        }
        return result;
    }

    /**
     * Suppose we already knew what is winner's name,
     * Test whether the function can find the winner correctly.
     * Suppose we already knew what is winner's name,
     * If the showWinner function returns the same name of the real winner,
     * then test function return true. Otherwise, return false.
     * <p>
     * @return boolean
     *
     */
    public static boolean showWinnerTest2(String realWinner){
        boolean result = false;
        String retValue = instance.showWinner();
        if(retValue.equals(realWinner)){
            result = true;
        }
        return result;
    }

    public static void main(String[] args) {
        instance.openListRanking("./testing/OPLexample.csv");
        // while run each test case, you may need to comment the other case.
        // test1
        boolean res1 =  showWinnerTest1();
        if (res1 == true){
            System.out.println("Test1 pass.");
        }
        else{
            System.out.println("Test1 fail.");
        }
//        // test2
//        boolean res2 =  showWinnerTest2("Pike");
//        if (res2 == true){
//            System.out.println("Test2 pass.");
//        }
//        else{
//            System.out.println("Test2 fail.");
//        }


    }
}
