/**
 * ---Here is the file header---
 * File name: CPLranking
 * Author: Hanzhang Wu(major author), Yanjun Cui, Jerry Nie and Yangjiawen Xu
 * Description: Here is the sub system of close list ranking. It contains: 
 * closeListRanking(): algorithm of CPL ranking;
 * auditfilecpl(): generate audit file of a CPL election;
 * generateSummaryCPL(): generate a basic summary of a CPL election;
 * displayResultcpl(): display basic result of a CPL election;
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.*;

public class CPLranking {
    static int numofseats;
    static int numofballots;
    static int numofcandidates;
    static int numberofparties;
    static HashMap<String, String> inner = new HashMap<>(); // candidates and party
    static String[] partyList = {};
    static HashMap<String, Integer> voteCount = new HashMap<>();
    static HashMap<String, List<String>> partyInit = new HashMap<>();
    static List<String> orderlist = new ArrayList<String>();
    static HashMap<String, Double> remVotes = new HashMap<>();
    static HashMap<String, Integer> seatCount = new HashMap<>();
    static HashMap<String, List<String>> partyFinal = new HashMap<>();

    /**
     * the sorting algorithm of close list ranking, which generates audit file<br>
     * the function does not return anything<br>
     * <p>
     * @param csvfile the input file
     * @throws IOException When the file coulndt be created
     *
     */
    public static void closeListRanking(String csvfile) throws IOException{
        BufferedReader br = new BufferedReader(new FileReader(csvfile));
        int count = 0;
        String line = br.readLine();//read the file
        while (line != null) {
            if (count == 1) {//take in the number of parties
                numberofparties = Integer.parseInt(line);
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
            }
            else if (count == 4){//take in the number of ballots
                numofballots = Integer.parseInt(line);
            }
            else if (count == 5){//take in the number of candidates
                numofcandidates = Integer.parseInt(line);
            }
            else if (line.charAt(0) == '['){//take in the candidates
                line = line.replaceAll("[\\[\\]]","");
                String[] cand = line.split(",");
                cand = line.split(",");
                List<String> nameList = partyInit.get(cand[1]);
                nameList.add(cand[0]);
                partyInit.put(cand[1], nameList);
                inner.put(cand[0],cand[1]);
                orderlist.add(cand[2]);
            }
            else{//take in the votes
                for (int i = 0; i < partyList.length; i++){
                    if (line.charAt(i) != ','){
                        Integer num = voteCount.get(partyList[i]);
                        num += 1;
                        voteCount.put(partyList[i], num);
                    }
                }
            }
            count++;
            line = br.readLine();//read a new line
        }
        Integer votesum = 0;
        Integer seatsum = 0;
        for (int i = 0; i < partyList.length; i++) votesum += voteCount.get(partyList[i]);
        if (numofseats == 0)
          return;
        Double quota = (double)votesum / numofseats;
        for (int i = 0; i < partyList.length; i++){
            Integer seats = numofseats * voteCount.get(partyList[i]) / votesum;
            Double rem = voteCount.get(partyList[i]) - seats * quota;
            seatsum += seats;
            remVotes.put(partyList[i], rem);
            seatCount.put(partyList[i], seats);
        }
        //use selection sort to sort the remaining votes
        for (int i = 0; i < partyList.length - 1; i++){
            int min = i;
            for (int j = i; j < partyList.length; j++){
                if (remVotes.get(partyList[j]) > remVotes.get(partyList[min])) min = j;
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
        if (numofseats > numofcandidates){
          partyFinal = partyInit;
          return;
        }
          for (int i = 0; i < numofseats - seatsum; i++){
              seatCount.put(partyList[i], seatCount.get(partyList[i]) + 1);
          }//adding the remaining seats to the corresponding parties
        for (int i = 0; i < partyList.length; i++){
            List<String> candFinal = new ArrayList<String>();
            Integer totalseats = seatCount.get(partyList[i]);
//            if (totalseats == 1) System.out.println("Party " + partyList[i] +
//                    " has " + totalseats + " seat in the end.");
//            else System.out.println("Party " + partyList[i] + " has " +
//                    totalseats + " seats in the end.");
            for (int j = 0; j < totalseats; j++){
                candFinal.add(partyInit.get(partyList[i]).get(j));
            }
            partyFinal.put(partyList[i], candFinal);
        }
        br.close();
    }

    /**
     * generate an audit file for CPL voting<br>
     * The general information will be in the audit file:<br>
     * 1). The number of seats, ballots, candidates,parties<br>
     * 2). The raw data<br>
     * 3). The sorted data<br>
     * 4). The candidates who get the seats<br>
     *
     * @throws IOException When the file coulndt be created
     *
     */

    public static void auditfilecpl() throws IOException {
        //try{
            PrintStream initOut = System.out;
            PrintStream fileOut = new PrintStream("audit.txt");
            System.setOut(fileOut);
            System.out.println("The type of voting is close list ranking");
            System.out.println("The number of parties is " + numberofparties);
            System.out.println("The number of seats is " + numofseats);
            System.out.println("The number of candidates is " + numofcandidates);
            System.out.println("The number of ballots is " + numofballots);
            int count = 0; // count
            for (Map.Entry<String, String> entry : inner.entrySet()) {
                System.out.println("Candidate " + entry.getKey() + " is in Party " +
                        entry.getValue()+ " with order " + orderlist.get(count));
                count ++;
            }
            for (Map.Entry<String, Integer> entry : voteCount.entrySet()) {
                System.out.println("Party " + entry.getKey() + " receives " +
                        entry.getValue() + " votes in total ");
            }
            Integer votesum = 0;
            Integer seatsum = 0;
            for (int i = 0; i < partyList.length; i++) votesum += voteCount.get(partyList[i]);
            Double quota = (double)votesum / numofseats;
            for (int i = 0; i < partyList.length; i++){
                Integer seats = numofseats * voteCount.get(partyList[i]) / votesum;
                Double rem = voteCount.get(partyList[i]) - seats * quota;
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
                for (int j = 0; j < partyInit.get(partyList[i]).size(); j++){
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
            System.out.println("\nThe audit file has been successfully generated!");
        //}
//        catch (IOException e) {
//            System.out.println("The summary fails to be generated!");
//        }
    }

    /**
     * the function generates the summary of the election<br>
     * the function does not return anything<br>
     *
     *
     */
    public static void generateSummaryCPL(){
        try{
            PrintStream initOut = System.out;
            PrintStream fileOut = new PrintStream("summary.txt");
            System.setOut(fileOut);
            System.out.println("The type of voting is close list ranking");
            System.out.println("The number of seats is " + numofseats);
            System.out.println("The number of ballots is " + numofballots);
            System.out.println("The number of candidates is " + numofcandidates);
            System.out.print("The parties are ");
            for(int i=0;i<partyList.length;i++){
                System.out.print(partyList[i] + " ");
            }
            System.setOut(initOut);
            System.out.println("The summary has been successfully generated!");
        } catch (IOException e) {
            System.out.println("The summary fails to be generated!");
        }
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
        for (int i = 0; i < partyList.length; i++){
            Integer totalvotes = voteCount.get(partyList[i]);
            Integer totalseats = seatCount.get(partyList[i]);
            if (numofseats == 0){
              System.out.println("Party " + partyList[i] + " has " +
                      voteCount.get(partyList[i]) + " vote and 0 seats.");
            } else if (totalvotes == 1 && totalseats == 1){
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
            if (numofseats == 0){
              System.out.println("In party " + partyList[i] + " no one got elected!");
            } else if (seatCount.get(partyList[i]) > 0){
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
}
