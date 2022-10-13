import java.io.*;
import java.util.*;
import static java.util.stream.Collectors.*;
import static java.util.Map.Entry.*;

public class voting_system {
    static HashMap<String, Integer> candidatesmap = new HashMap<>();
    static HashMap<String, String> inner = new HashMap<>();
    static HashMap<String, List<String>> partyInit = new HashMap<>();
    static HashMap<String, List<String>> partyFinal = new HashMap<>();
    static HashMap<String, Integer> voteCount = new HashMap<>();
    static HashMap<String, Integer> seatCount = new HashMap<>();
    static boolean loginStatus = false;
    static int numofseats;
    static int numofballots;
    static int numofcandidates;
    static int numberofparties;
    static String[] partyList = {};
    static String[] arrayparty;
    static int c;
    static boolean exceptionCatch = false;

    /**
     * Create a user account.<br>
     * If the user successfully created an accout, return true<br>
     * Otherwise, return false<br>
     * <p>
     * @param username perferred username
     * @param password perferred password
     * @return boolean
     *
     */
    public static boolean register(String username, String password) {
        Scanner in = new Scanner(System.in);
        int count = 0;
        //users have 5 attempts of entering passwords
        while(count < 5){
          if (password.length() >= 6 && password.length() <= 20) break;
          else if(count < 4) {
            System.out.print("Your password is incorrect! ");
            System.out.println("You have " + (5-count) + " attempts left");
          } else {
            System.out.print("Your password is incorrect! ");
            System.out.println("You have only one attempt left!");
          } System.out.println("Please set your password(length 6-20):");
          password = in.nextLine();//re-enter password
          count++;
          if(count == 5) return false;
        }
        //open the file that stores user information
        File file = new File("userinfo.txt");
        try {//create a new file if it does not exist
          if (!file.exists()) file.createNewFile();
        } catch (Exception e) {e.printStackTrace();}
        try(PrintWriter output = new PrintWriter(new FileWriter(file,true))){
          output.print(username);//write username and password into the file
          output.print(" ");
          output.println(password);
        } catch (Exception e) {e.printStackTrace();}
        loginStatus = true;
        System.out.println("Your registration was successful!");
        System.out.println("You have been redirected and is now logged in!");
        return true;
    }

    /**
     * Have the user log into the system<br>
     * If the user successfully logged into the system, return true<br>
     * Otherwise, return false<br>
     * <p>
     * @param username stored username
     * @param password stored password
     * @return boolean
     *
     */
    public static boolean login(String username, String password) {
      Scanner in = new Scanner(System.in);
      int count = 0;
      File file = new File("userinfo.txt");
      while(count < 5){//users have 5 attempts of entering usernames
        try(Scanner filescan = new Scanner(file)){
          while (filescan.hasNextLine()){//read the userinfo file
            String[] userinfo = filescan.nextLine().split(" ");
            if (userinfo[0].equals(username)){
              count = 5;
              break;//valid username
            }
            else if(count < 4) {
              System.out.print("Your username is invalid! ");
              System.out.println("You have " + (5-count) + " attempts left");
            } else {
              System.out.print("Your username is invalid! ");
              System.out.println("You have only one attempt left!");
            } System.out.println("Please enter your username:");
            username = in.nextLine();//take in new username
            count++;
            if(count == 5) return false;
          }
        } catch (Exception e) {e.printStackTrace();}
      }
      System.out.println("Valid username!");
      count = 0;
      while(count < 5){//users have 5 attempts of entering passwords
        try(Scanner filescan = new Scanner(file)){
          while (filescan.hasNextLine()){//read the userinfo file
            String[] userinfo = filescan.nextLine().split(" ");
            if (userinfo[0].equals(username) && userinfo[1].equals(password)){
                System.out.println("You are now logged in!");
                loginStatus = true;//valid password
                return true;
            } else if(count < 4){
              System.out.print("Your password is incorrect! ");
              System.out.println("You have " + (5-count) + " attempts left");
            } else {
              System.out.print("Your password is incorrect! ");
              System.out.println("You have only one attempt left!");
            } System.out.println("Please enter your password:");
              password = in.nextLine();//take in new password
            count++;
          }
        } catch (Exception e) {e.printStackTrace();}
      }
      return false;
    }

    /**
     * Have the user log off the system<br>
     * If the user successfully logged off the system, return true<br>
     * Otherwise, return false<br>
     * <p>
     *
     * @return boolean
     *
     */
    public static boolean logout(){
        if (loginStatus){//in order to log out the user has be logged in
            loginStatus = false;
            System.out.println("You are now logged out!");
            return true;
        }
        System.out.println("Logout failed!");
        return false;
    }

    /**
     * enter the csv file pth<br>
     * check whether the file exists<br>
     * <p>
     * @param path where the file is located
     * @return boolean
     *
     */
    public static boolean selectFile(String path){
      File file = new File(path);
      if (!file.exists()) return false;
      else return true;
    }

    /**
     * get the information of the file<br>
     * the number of candidates, parties, the ballots<br>
     * store the information in the dictionary<br>
     * candidatesmap store the name of candidates and the ballots he gets<br>
     * inner store the name of candidates and the parties he is from<br>
     * arrayparty stores the party of the corresponding candidates<br>
     *
     * <p>
     * @param csvfile the input file
     *
     *
     */

    public static void openListRanking(String csvfile){ // For individual candidates
        try{
            BufferedReader br = new BufferedReader(new FileReader(csvfile));
            String line = "";
            int count = 0;
            while ((line = br.readLine()) != null) {
                if (count == 1) numofseats = Integer.parseInt(line);
                if (count == 2) numofballots = Integer.parseInt(line);
                if (count == 3) {
                    numofcandidates = Integer.parseInt(line);
                    arrayparty = new String[numofcandidates];
                    c = 0;
                }
                if (count >= 4) {
                    if (!line.contains("1")) {
                        // separate the line using [] and then ,
                        line = line.replaceAll("[\\[\\]]","");
                        String[] candidates_party = line.split(",");
                        // put candidates and party in inner
                        inner.put(candidates_party[0], candidates_party[1]);
                        //System.out.println(candidates_party[1]);
                        // put candidates and number of ballots in candidates map
                        candidatesmap.put(candidates_party[0], 0);
                        arrayparty[c] = candidates_party[0];
                        c++;
                    } else {
                        int num = candidatesmap.get(arrayparty[line.indexOf("1")]);
                        candidatesmap.put(arrayparty[line.indexOf("1")], num + 1);
                    }
                }
                count++;
            }
        } catch (FileNotFoundException e) {
            exceptionCatch = true;
            e.printStackTrace();
        } catch (IOException e) {
            exceptionCatch = true;
            e.printStackTrace();
        }
    }

    /**
     * for the OPL list ballot<br>
     * get the ones who get the most ballots,<br>
     * break tie if necessary<br>
     * <p>
     *
     * @return String
     *
     */
    public static String showWinner() {
        int max = Collections.max(candidatesmap.values());
        //String winner = "";
        //System.out.println(max);
        //int i = 0;
        List<String> winner = new ArrayList<String>();
        for (Map.Entry<String, Integer> entry : candidatesmap.entrySet()) {
            if (entry.getValue().equals(max)) {
                // put the winner that get the max ballots in winner array
                winner.add(entry.getKey());
                //System.out.println(winners[i]);
            }
        }
        if (winner.size() != 1){
            int j = breaktieOPL(winner.size());
            return winner.get(j);
        }
        else{
            return winner.get(0);
        }
    }

    /**
     * if there are candidates who get the same number of ballots<br>
     * we need to break a tie<br>
     * this function works for generate the random number<br>
     * <p>
     * @param j the length of winners' list
     * @return the random generated integer
     *
     */

    public static int breaktieOPL(int j){
      return new Random().nextInt(j);
    }

    /**
     * generate an audit file for OPL voting<br>
     * The general information will be in the audit file:<br>
     * 1). The number of seats, ballots, candidates<br>
     * 2). The raw data<br>
     * 3). The sorted data<br>
     * 4). The candidates who get the seats<br>
     * 5). The winner<br>
     *
     * <p>
     * @throws IOException When the file coulndt be created
     */
    public static void auditfileopl() throws IOException{
        PrintStream initOut = System.out;
        PrintStream fileOut = new PrintStream("auditopl.txt");
        System.setOut(fileOut);
        System.out.println("The type of voting is open list ranking");
        System.out.println("The number of seats is " + numofseats);
        System.out.println("The number of ballots is " + numofballots);
        System.out.println("The number of candidates is " + numofcandidates);
        System.out.println("\nThe following is the raw data:");
        for (Map.Entry<String, Integer> entry : candidatesmap.entrySet()) {
            System.out.println("The candidate "+entry.getKey()+" who comes from "
                    + inner.get(entry.getKey())+ " gets " +entry.getValue() + " ballots.");
        }
        // sort the candidatesmap based on the number of ballots each candidates get in descending order
        Map<String, Integer> sorted = candidatesmap.entrySet()
                                                   .stream()
                                                   .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                                                   .collect(
                                                           toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2, LinkedHashMap::new));
        System.out.println("\nThe following is the sorted data:");
        for (Map.Entry<String, Integer> entry : sorted.entrySet()) {
            System.out.println("The candidate "+entry.getKey()+" who comes from "
                    + inner.get(entry.getKey())+ " gets " +entry.getValue() + " ballots.");
        }
        System.out.println("\nThe following candidates get the seats in this voting.");
        int count = 0;
        for (Map.Entry<String, Integer> entry : sorted.entrySet()) {
            if (count < numofseats) {
                System.out.println(entry.getKey());
                count ++;
            }
        }

        System.out.println("\nThe winner is "+showWinner()+" who is from "+inner.get(showWinner())+
                " and gets " +candidatesmap.get(showWinner()) + " ballots.");
        System.setOut(initOut);
        System.out.println("The audit file has been successfully generated!");
    }

    /**
     * generate a file which is sent to media<br>
     * The general information will be in the audit file:<br>
     * 1). The number of seats, ballots, candidates<br>
     * 2). The sorted data<br>
     * 3). The candidates who get the seats<br>
     * 4). The winner<br>
     *
     * <p>
     * @throws IOException When the file coulndt be created
     *
     */

    public static void sendtomediaopl() throws IOException{
        PrintStream initOut = System.out;
        PrintStream fileOut = new PrintStream("mediaopl.txt");
        System.setOut(fileOut);
        System.out.println("The type of voting is open list ranking");
        System.out.println("The number of seats is " + numofseats);
        System.out.println("The number of ballots is " + numofballots);
        System.out.println("The number of candidates is " + numofcandidates);
        // sort the candidatesmap based on the number of ballots each candidates get in descending order
        Map<String, Integer> sorted = candidatesmap.entrySet()
                .stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .collect(
                        toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2, LinkedHashMap::new));
        System.out.println("\nThe following is the sorted data:");
        for (Map.Entry<String, Integer> entry : sorted.entrySet()) {
            System.out.println("The candidate "+entry.getKey()+" who comes from "
                    + inner.get(entry.getKey())+ " gets " +entry.getValue() + " ballots.");
        }
        System.out.println("\nThe following candidates get the seats in this voting.");
        int count = 0;
        for (Map.Entry<String, Integer> entry : sorted.entrySet()) {
            if (count < numofseats) {
                System.out.println(entry.getKey());
                count ++;
            }
        }

        System.out.println("\nThe winner is "+showWinner()+" who is from "+inner.get(showWinner())+
                " and gets " +candidatesmap.get(showWinner()) + " ballots.");
        System.setOut(initOut);
        System.out.println("The audit file has been successfully generated!");
    }

    /**
     * show the results to the termianl<br>
     * The general information will be in the audit file:<br>
     * 1). The number of seats, ballots, candidates<br>
     * 2). The winner<br>
     *
     *
     *
     */
    public static void displayResultopl(){
        System.out.println("The type of voting is open list ranking");
        System.out.println("The number of seats is " + numofseats);
        System.out.println("The number of ballots is " + numofballots);
        System.out.println("The number of candidates is " + numofcandidates);
        System.out.println("\nThe winner is "+showWinner()+" who is from "+inner.get(showWinner())+
                " and gets " +candidatesmap.get(showWinner()) + " ballots.");
        System.out.println("The audit file has been successfully generated!\n");
    }

    /**
     * the sorting algorithm of close list ranking, which generates audit file<br>
     * the function does not return anything<br>
     * <p>
     * @param csvfile the input file
     * @throws IOException When the file coulndt be created
     *
     */
    public static void closeListRanking(String csvfile) throws IOException{
      PrintStream initOut = System.out;
      PrintStream fileOut = new PrintStream("audit.txt");
      System.setOut(fileOut);
      System.out.println("The type of voting is close list ranking");
      HashMap<String, Integer> remVotes = new HashMap<>();
      BufferedReader br = new BufferedReader(new FileReader(csvfile));
      int count = 0;
      String line = br.readLine();//read the file
      while (line != null) {
        if (count == 1) {//take in the number of parties
          numberofparties = Integer.parseInt(line);
          System.out.println("The number of parties is " + numberofparties);
        }
        else if (count == 2) {//take in the list of parties
          line = line.replaceAll("[\\[\\]]","");
          partyList = line.split(",");
          for (int i = 0; i < partyList.length; i++){
            voteCount.put(partyList[i], 0);
            List<String> candList = new ArrayList<String>();
            partyInit.put(partyList[i], candList);
          }
        }
        else if (count == 3){//take in the number of seats
          numofseats = Integer.parseInt(line);
          System.out.println("The number of seats is " + numofseats);
        }
        else if (count == 4){//take in the number of ballots
          numofballots = Integer.parseInt(line);
          System.out.println("The number of ballots is " + numofballots);
        }
        else if (count == 5){//take in the number of candidates
          numofcandidates = Integer.parseInt(line);
          System.out.println("The number of candidates is " + numofcandidates);
        }
        else if (line.charAt(0) == '['){//take in the candidates
          line = line.replaceAll("[\\[\\]]","");
          String[] cand = line.split(",");
          cand = line.split(",");
          List<String> nameList = partyInit.get(cand[1]);
          nameList.add(cand[0]);
          partyInit.put(cand[1], nameList);
          System.out.println("Candidate " + cand[0] + " is in Party " +
          cand[1] + " with order " + cand[2]);
        }
        else{//take in the votes
          for (int i = 0; i < partyList.length; i++){
            if (line.charAt(i) != ','){
              Integer num = voteCount.get(partyList[i]);
              num += 1;
              voteCount.put(partyList[i], num);
              if (num == 1) System.out.println("Party " + partyList[i] +
              " receives one vote with " + num + " vote in total.");
              else System.out.println("Party " + partyList[i] +
              " receives one vote with " + num + " votes in total.");
            }
          }
        }
        count++;
        line = br.readLine();//read a new line
      }
      Integer votesum = 0;
      Integer seatsum = 0;
      for (int i = 0; i < partyList.length; i++) votesum += voteCount.get(partyList[i]);
      Integer quota = votesum / numofseats + 1;
      for (int i = 0; i < partyList.length; i++){
        Integer seats = voteCount.get(partyList[i]) / quota;
        Integer rem = voteCount.get(partyList[i]) - seats * quota;
        seatsum += seats;
        remVotes.put(partyList[i], rem);
        seatCount.put(partyList[i], seats);
        if (seats == 1 && rem == 1) System.out.println("Party " + partyList[i] +
        " now has " + seats + " seat in total with " + rem + " vote left.");
        else if(seats == 1) System.out.println("Party " + partyList[i] +
        " now has " + seats + " seat in total with " + rem + " votes left.");
        else if(rem == 1) System.out.println("Party " + partyList[i] +
        " now has " + seats + " seats in total with " + rem + " vote left.");
        else System.out.println("Party " + partyList[i] + " now has " + seats +
        " seats in total with " + rem + " votes left.");
      }
      //use selection sort to sort the remaining votes
      for (int i = 0; i < partyList.length - 1; i++){
        int min = i;
        for (int j = i; j < partyList.length; j++){
          if (remVotes.get(partyList[j]) < remVotes.get(partyList[min])) min = j;
          else if (remVotes.get(partyList[j]) == remVotes.get(partyList[min])){
            Random rand = new Random();//two parties have the same remaining votes
            int randInt = rand.nextInt(2);//generate a random number
            if (randInt == 0) min = j;//randomly prioritize one of them
          }
        }
        String Init = partyList[min];
        partyList[min] = partyList[i];
        partyList[i] = Init;
      }
      for (int i = 0; i < numofseats - seatsum; i++){
        seatCount.put(partyList[i], seatCount.get(partyList[i]) + 1);
      }//adding the remaining seats to the corresponding parties
      for (int i = 0; i < partyList.length; i++){
        List<String> candFinal = new ArrayList<String>();
        Integer totalseats = seatCount.get(partyList[i]);
        if (totalseats == 1) System.out.println("Party " + partyList[i] +
        " has " + totalseats + " seat in the end.");
        else System.out.println("Party " + partyList[i] + " has " +
        totalseats + " seats in the end.");
        for (int j = 0; j < totalseats; j++){
          candFinal.add(partyInit.get(partyList[i]).get(j));
        }
        partyFinal.put(partyList[i], candFinal);
      }
      for (int i = 0; i < partyList.length; i++){
        Integer totalvotes = voteCount.get(partyList[i]);
        Integer totalseats = seatCount.get(partyList[i]);
        if (totalvotes == 1 && totalseats == 1){
          System.out.print("Party " + partyList[i] + " has " +
          voteCount.get(partyList[i]) + " vote and " +
          seatCount.get(partyList[i]) + " seat. ");
        } else if (totalvotes == 1){
          System.out.print("Party " + partyList[i] + " has " +
          voteCount.get(partyList[i]) + " vote and " +
          seatCount.get(partyList[i]) + " seats. ");
        } else if (totalseats == 1){
          System.out.print("Party " + partyList[i] + " has " +
          voteCount.get(partyList[i]) + " votes and " +
          seatCount.get(partyList[i]) + " seat. ");
        } else{
          System.out.print("Party " + partyList[i] + " has " +
          voteCount.get(partyList[i]) + " votes and " +
          seatCount.get(partyList[i]) + " seats. ");
        } if (seatCount.get(partyList[i]) > 0){
          List<String> candList = partyFinal.get(partyList[i]);
          System.out.print("In party " + partyList[i]);
          for (int j = 0; j < candList.size(); j++){
            System.out.print(", " + candList.get(j));
          }
          System.out.println(" got elected.");
        } else{
          System.out.println("In party " + partyList[i] +
          ", no one got elected.");
        }
      }
      br.close();
      System.setOut(initOut);
      System.out.println("\nThe audit file has been successfully generated!");
    }

    /**
     * the function generates the file to be sent to media<br>
     * the function does not return anything<br>
     *
     * <p>
     * @throws IOException When the file coulndt be created
     */
    public static void sendToMediacpl() throws IOException{
        PrintStream initOut = System.out;
        PrintStream fileOut = new PrintStream("mediacpl.txt");
        System.setOut(fileOut);
        System.out.println("The type of voting is close list ranking");
        System.out.println("The number of parties is " + numberofparties);
        System.out.println("The number of seats is " + numofseats);
        System.out.println("The number of candidates is " + numofcandidates);
        System.out.println("The number of ballots is " + numofballots);
        for (int i = 0; i < partyList.length; i++){
          Integer totalvotes = voteCount.get(partyList[i]);
          Integer totalseats = seatCount.get(partyList[i]);
          if (totalvotes == 1 && totalseats == 1){
            System.out.print("Party " + partyList[i] + " has " +
            voteCount.get(partyList[i]) + " vote and " +
            seatCount.get(partyList[i]) + " seat. ");
          } else if (totalvotes == 1){
            System.out.print("Party " + partyList[i] + " has " +
            voteCount.get(partyList[i]) + " vote and " +
            seatCount.get(partyList[i]) + " seats. ");
          } else if (totalseats == 1){
            System.out.print("Party " + partyList[i] + " has " +
            voteCount.get(partyList[i]) + " votes and " +
            seatCount.get(partyList[i]) + " seat. ");
          } else{
            System.out.print("Party " + partyList[i] + " has " +
            voteCount.get(partyList[i]) + " votes and " +
            seatCount.get(partyList[i]) + " seats. ");
          }
          if (seatCount.get(partyList[i]) > 0){
            List<String> candList = partyFinal.get(partyList[i]);
            System.out.print("In party " + partyList[i]);
            for (int j = 0; j < candList.size(); j++){
              System.out.print(", " + candList.get(j));
            }
            System.out.println(" got elected.");
          } else {
            System.out.println("In party " + partyList[i] +
            ", no one got elected.");
          }
    }
        System.setOut(initOut);
        System.out.println("The media file has been successfully generated!");
    }

    /**
     * the function displays the result on the terminal<br>
     * the function does not return anything<br>
     *
     *
     */
    public static void displayResultcpl(){
        System.out.println("The type of voting is close list ranking");
        System.out.println("The number of parties is " + numberofparties);
        System.out.println("The number of seats is " + numofseats);
        System.out.println("The number of candidates is " + numofcandidates);
        System.out.println("The number of ballots is " + numofballots);
        for (int i = 0; i < partyList.length; i++){
          Integer totalvotes = voteCount.get(partyList[i]);
          Integer totalseats = seatCount.get(partyList[i]);
          if (totalvotes == 1 && totalseats == 1){
            System.out.print("Party " + partyList[i] + " has " +
            voteCount.get(partyList[i]) + " vote and " +
            seatCount.get(partyList[i]) + " seat. ");
          } else if (totalvotes == 1){
            System.out.print("Party " + partyList[i] + " has " +
            voteCount.get(partyList[i]) + " vote and " +
            seatCount.get(partyList[i]) + " seats. ");
          } else if (totalseats == 1){
            System.out.print("Party " + partyList[i] + " has " +
            voteCount.get(partyList[i]) + " votes and " +
            seatCount.get(partyList[i]) + " seat. ");
          } else{
            System.out.print("Party " + partyList[i] + " has " +
            voteCount.get(partyList[i]) + " votes and " +
            seatCount.get(partyList[i]) + " seats. ");
          }
          if (seatCount.get(partyList[i]) > 0){
            List<String> candList = partyFinal.get(partyList[i]);
            System.out.print("In party " + partyList[i]);
            for (int j = 0; j < candList.size(); j++){
              System.out.print(", " + candList.get(j));
            }
            System.out.println(" got elected.");
          } else{
            System.out.println("In party " + partyList[i] +
            ", no one got elected.");
          }
        }
    }

    /**
     * main is the master function<br>
     * the function does not return anything<br>
     * <p>
     * @param args the input arguments
     *
     */
    public static void main(String[] args){
      System.out.println("Welcome to the voting system!");
      while (!loginStatus) {//users opt to register or log in
        System.out.println("Please enter either r to register, or l to log in.");
        Scanner in = new Scanner(System.in);
        String input = in.nextLine();
        if (input.equals("r")) {//registration begins
          System.out.println("Your are now in the registration page");
          //take in username and password
          System.out.println("Please set your username:");
          String username = in.nextLine();
          System.out.println("Please set your password(length 6-20):");
          String password = in.nextLine();
          boolean r = register(username, password);
          if (r) loginStatus = true;
          else {
            System.out.println("Registration failed!");
            System.exit(1);
          }
        } else if (input.equals("l")) {//login begins
          if (!selectFile("userinfo.txt")){
            System.out.print("You have not registered yet. ");
            System.out.println("Please register before logging in!");
          } else{
            System.out.println("Your are now in the login page");
            System.out.println("Please enter your username:");
            String username = in.nextLine();//take in username
            System.out.println("Please enter your password:");
            String password = in.nextLine();//take in password
            boolean l = login(username, password);
            if (l) loginStatus = true;
            else {
              System.out.println("Login failed!");
              System.exit(1);
            }
          }
        } else {
          System.out.println("Invalid option! Enter r or l only!");
        }
      }
      while (loginStatus){
        Scanner in = new Scanner(System.in);
        System.out.println("Please enter the name of the csv file:");
        String path = in.nextLine();//take in the name of the file
        if(selectFile(path)){
          try {//read the first line of the file to decide the type
            BufferedReader br = new BufferedReader(new FileReader(path));
            String line;
            if ((line = br.readLine()) != null) {
              if (line.equals("OPL")) {
                openListRanking(path);
                displayResultopl();
                System.out.println("Enter 1: Show winners");
                System.out.println("Enter 2: Get audit file");
                System.out.println("Enter 3: Send to media");
                System.out.println("Enter 4: Log out");
                while (loginStatus){
                    Scanner output = new Scanner(System.in);
                    String e = output.nextLine();
                    if (e.isEmpty()) logout();
                    else if(e.equals("1")){
                        System.out.println("The winner is :");
                        System.out.print(showWinner());
                    }
                    else if (e.equals("2")) auditfileopl();
                    else if (e.equals("3")) sendtomediaopl();
                    else if (e.equals("4")) logout();
                    else System.out.println("Please enter the right number!");
                }
              } else if (line.equals("CPL")) {
                closeListRanking(path);
                displayResultcpl();
                System.out.println("Enter 1: Send to media");
                System.out.println("Enter 2: Log out");
                while (loginStatus){
                  Scanner output = new Scanner(System.in);
                  String e = output.nextLine();
                  if (e.equals("1")) sendToMediacpl();
                  else if (e.equals("2")) logout();
                }
              } else System.out.println("Invalid file format!");
            }
          } catch(IOException e) {e.printStackTrace();}
        } else System.out.println("File does not exist!");
      }
    }
}
