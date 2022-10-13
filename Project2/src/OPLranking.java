/**
 * ---Here is the file header---
 * File name: OPLranking
 * Author: Yangjiawen Xu(major author), Jerry Nie, Hanzhang Wu and Yanjun Cui.
 * Description: Here is the sub system of open list ranking. It contains: 
 * openListRanking(): algorithm of OPL ranking;
 * auditfileopl(): generate audit file of an OPL election;
 * sendtomediaopl():  generate media file of an OPL election;
 * generateSummaryOPL(): generate a basic summary of an OPL election;
 * displayResultopl(): display basic result of an OPL election;
 * breaktieOPL(): If the OPL election has a tie, this fucntion will randomly breaks it;
 * ShowWinner(): display the individual winner of an OPL election. 
 */

import java.io.*;
import java.util.*;

import static java.util.stream.Collectors.toMap;

public class OPLranking {
    static int numofseats;
    static int numofballots;
    static int numofcandidates;
    static String[] arrayparty;
    static List<String> distinctparty;
    static int c;
    static int numberofparties;
    static HashMap<String, String> inner = new HashMap<>(); // candidates and party
    static HashMap<String, Integer> candidatesmap = new HashMap<>();
    static List<String> winner = new ArrayList<String>();

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
                if (count == 1){
                    numofseats = Integer.parseInt(line);
                    if (numofseats == 0){
                        System.out.println("There are no seats.");
                        break;
                    }
                }
                if (count == 2) numofballots = Integer.parseInt(line);
                if (count == 3) {
                    numofcandidates = Integer.parseInt(line);
                    arrayparty = new String[numofcandidates];
                    distinctparty = new ArrayList<String>(numberofparties);
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
                        if (!distinctparty.contains(candidates_party[1])){
                            distinctparty.add(candidates_party[1]);
                        }
                        c++;
                    } else {
                        int num = candidatesmap.get(arrayparty[line.indexOf("1")]);
                        candidatesmap.put(arrayparty[line.indexOf("1")], num + 1);
                    }
                }
                count++;
            }
        } catch (FileNotFoundException e) {
            //exceptionCatch = true;
            e.printStackTrace();
            System.out.println("The election failed");
        } catch (IOException e) {
            //exceptionCatch = true;
            e.printStackTrace();
            System.out.println("The election failed");
        }
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
     * @throws IOException When the file could't be created
     *
     */
    public static void auditfileopl() throws IOException{
        try {
            PrintStream initOut = System.out;
            PrintStream fileOut = new PrintStream("audit.txt");
            System.setOut(fileOut);
            System.out.println("The type of voting is open list ranking");
            System.out.println("The number of seats is " + numofseats);
            System.out.println("The number of ballots is " + numofballots);
            System.out.println("The number of candidates is " + numofcandidates);
            System.out.println("\nThe following is the raw data:");
            for (Map.Entry<String, Integer> entry : candidatesmap.entrySet()) {
                System.out.println("The candidate " + entry.getKey() + " who comes from "
                        + inner.get(entry.getKey()) + " gets " + entry.getValue() + " ballots.");
            }
            // sort the candidatesmap based on the number of ballots each candidates get in descending order
            Map<String, Integer> sorted = candidatesmap.entrySet()
                    .stream()
                    .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                    .collect(
                            toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2, LinkedHashMap::new));
            System.out.println("\nThe following is the sorted data:");
            for (Map.Entry<String, Integer> entry : sorted.entrySet()) {
                System.out.println("The candidate " + entry.getKey() + " who comes from "
                        + inner.get(entry.getKey()) + " gets " + entry.getValue() + " ballots.");
            }
            System.out.println("\nThe following candidates get the seats in this voting.");
            int count = 0;
            for (Map.Entry<String, Integer> entry : sorted.entrySet()) {
                if (count < numofseats) {
                    System.out.println(entry.getKey());
                    count++;
                }
            }

            System.out.println("\nThe winner is " + showWinner() + " who is from " + inner.get(showWinner()) +
                    " and gets " + candidatesmap.get(showWinner()) + " ballots.");
            System.setOut(initOut);
            System.out.println("The audit file has been successfully generated!");
            //compactauditfile();
        } catch (IOException e) {
            System.out.println("The audit file fails to be generated!");
        }
    }

    /**
     * generate a file which is sent to media<br>
     * The general information will be in the audit file:<br>
     * 1). The number of seats, ballots, candidates<br>
     * 2). The sorted data<br>
     * 3). The candidates who get the seats<br>
     * 4). The winner<br>
     *
     * @throws IOException When the file could't be created
     */
    public static void sendtomediaopl() throws IOException {
        try{
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

            System.out.println("\nThe winner is "+ showWinner()+" who is from "+inner.get(showWinner())+
                    " and gets " +candidatesmap.get(showWinner()) + " ballots.");
            System.setOut(initOut);
            System.out.println("The send-to-media file has been successfully generated!");
        } catch (IOException e) {
            System.out.println("The send-to-media file fails to be generated!");
        }
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
      if (numofseats == 0){
        System.out.println("There is no winner since there is no seats.");
      } else{
        System.out.println("\nThe winner is "+ showWinner()+" who is from "+inner.get(showWinner())+
                " and gets " +candidatesmap.get(showWinner()) + " ballots.");}
    }

    /**
     * the function generates the summary of the election<br>
     * the function does not return anything<br>
     *
     *
     */
    public static void generateSummaryOPL(){
        try{
            PrintStream initOut = System.out;
            PrintStream fileOut = new PrintStream("summary.txt");
            System.setOut(fileOut);
            System.out.println("The type of voting is open list ranking");
            System.out.println("The number of seats is " + numofseats);
            System.out.println("The number of ballots is " + numofballots);
            System.out.println("The number of candidates is " + numofcandidates);
            System.out.print("The parties are ");
            if (numofseats > 0){
              for(int i=0;i<distinctparty.size();i++){
                  System.out.print(distinctparty.get(i) + " ");
              }
            }
            System.setOut(initOut);
            System.out.println("The summary has been successfully generated!");
        } catch (IOException e) {
            System.out.println("The summary fails to be generated!");
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
     * for the OPL list ballot<br>
     * get the ones who get the most ballots,<br>
     * break tie if necessary<br>
     * <p>
     *
     * @return String
     *
     */
    public static String showWinner() {
      if (numofseats == 0){
        System.out.println("There is no winner since there is no seats.");
        return "N/A";
      } else{
        int max = Collections.max(candidatesmap.values());
        //String winner = "";
        //System.out.println(max);
        //int i = 0;
        // List<String> winner = new ArrayList<String>();
        for (Map.Entry<String, Integer> entry : candidatesmap.entrySet()) {
            if (entry.getValue().equals(max)) {
                // put the winner that get the max ballots in winner array
                winner.add(entry.getKey());
                //System.out.println(winners[i]);
            }
        }
        if (winner.size() > 1){
            int j = OPLranking.breaktieOPL(winner.size());
            return winner.get(j);
        }
        else{
            return winner.get(0);
        }
      }
    }
}
