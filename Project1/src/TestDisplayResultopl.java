public class TestDisplayResultopl {
    static voting_system instance = new voting_system();

    /**
     * Suppose we already get the result of opl election
     * Test whether the result can be displayed correctly.
     * Suppose we already obtain all information of this election.
     * If the displayResultopl function returns the same results as expected,
     * then test function return true. Otherwise, return false.
     */

    public static boolean dispalyResultoplTest1(String path) {
        boolean res = false;
        instance.openListRanking(path);
        int seats = 3;
        int ballots = 10;
        int candidate = 6;
        String winner1 = "Pike";
        String winner2 = "Foster";
        String party = "D";
        int tickets = 3;
        System.out.println(instance.showWinner());
        if (instance.numofseats == seats && instance.numofballots == ballots && instance.numofcandidates == candidate
                && (instance.showWinner().equals(winner1) || instance.showWinner().equals(winner2))
                && instance.inner.get(instance.showWinner()).equals(party)
                && instance.candidatesmap.get(instance.showWinner()) == tickets){
                res = true;
        }
        return res;
    }

    /**
     * Suppose user input an invalid path
     * Test whether the error shows.
     * If error shows and variables don't obtain a value, then return true.
     * Otherwise, false.
     */

    public static boolean dispalyResultoplTest2(String path) {
        boolean res = true;
        instance.openListRanking(path);
        if (instance.numofseats == 0 || instance.numofballots == 0 || instance.numofcandidates == 0){
            res = true;
        }
        return res;
    }


    public static void main(String[] args) {

        // Test1
        String path1 = "./testing/OPLexample.csv";
        System.out.println("Testcase1: successully print out");
        boolean res1 = dispalyResultoplTest1(path1);
        if (res1){
            System.out.println("Test1 pass.");
        }
        else{
            System.out.println("Test1 fail");
        }
        // Test2
        String path2 = "./fdds";
        System.out.println("\nTestcase2: invalid path");
        boolean res2 = dispalyResultoplTest2(path2);
        if (res2){
            System.out.println("Test2 pass.");
        }
        else{
            System.out.println("Test2 fail");
        }


    }
}
