  
/**
 * ---Here is the file header---
 * File name: voting_system
 * Author: Hanzhang Wu, Jerry Nie, Yanjiawen Xu and Yanjun Cui.
 * Description: Here is the main function of the voting system. It contains: 
 * selectFile(): a helper function for selecting input files. 
 * main(): The main function of the voting system, calls all other functions to complete the voting system functionalities.  
 */

import java.io.*;
import java.util.*;
import java.util.List;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class voting_system {
    static boolean exceptionCatch = false;
    static login login_inst = new login();
    static SearchCSV search_inst = new SearchCSV();

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
     * main is the master function<br>
     * the function does not return anything<br>
     * <p>
     * @param args the input arguments
     *
     */
    public static void main(String[] args){
        System.out.println("Welcome to the voting system!");
        while (!login_inst.loginStatus) {//users opt to register or log in
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
                boolean r = login_inst.register(username, password);
                if (r) login_inst.loginStatus = true;
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
                    boolean l = login_inst.login(username, password);
                    if (l) login_inst.loginStatus = true;
                    else {
                        System.out.println("Login failed!");
                        System.exit(1);
                    }
                }
            } else {
                System.out.println("Invalid option! Enter r or l only!");
            }
        }
        while (login_inst.loginStatus){
            try {
                SearchCSV.csv_search();
            } catch(IOException err) {err.printStackTrace();}
            try {
                synchronized (search_inst.lock) {
                    search_inst.lock.wait();
                }
            } catch(InterruptedException err) {err.printStackTrace();}
            if(selectFile(search_inst.path)){
                try {//read the first line of the file to decide the type
                    BufferedReader br = new BufferedReader(new FileReader(search_inst.path));
                    String line;
                    if ((line = br.readLine()) != null) {
                        if (line.equals("OPL")) {
                            OPLranking.openListRanking(search_inst.path);
                            System.out.println("Enter 1: Generate summary");
                            Scanner output1 = new Scanner(System.in);
                            String e1 = output1.nextLine();
                            if(e1.equals("1")){
                                OPLranking.generateSummaryOPL();
                            }
                            OPLranking.displayResultopl();
                            System.out.println("Enter 1: Show winners");
                            System.out.println("Enter 2: Get audit file");
                            //System.out.println("Enter 3: Get shorter audit file");
                            System.out.println("Enter 4: Send to media");
                            System.out.println("Enter 5: Log out");
                            while (login_inst.loginStatus) {
                                Scanner output = new Scanner(System.in);
                                String e = output.nextLine();
                                if (e.isEmpty()) {
                                    login_inst.logout();
                                    if(!login_inst.loginStatus){
                                        System.exit(0);
                                    }
                                }
                                else if (e.equals("1")) {
                                    System.out.println("The winner is :" + OPLranking.showWinner());
                                } else if (e.equals("2")) {
                                    OPLranking.auditfileopl();
                                    System.out.println("Please choose where you want to save the file.");
                                    myjframe1 x = new myjframe1();
                                    System.out.println("Audit file is successfully generated and saved, you can do other operations. ");
                                }
                                //else if (e.equals("3")) compactauditfile();
                                else if (e.equals("4")) OPLranking.sendtomediaopl();
                                else if (e.equals("5")) {
                                    login.logout();
                                    if(!login_inst.loginStatus){
                                        System.exit(0);
                                    }
                                }
                                else System.out.println("Please enter the right number!");
                            }
                        } else if (line.equals("CPL")) {
                            CPLranking.closeListRanking(search_inst.path);
                            // System.out.println("Please choose where you want to save the file.");
                            // myjframe1 y = new myjframe1();
                            System.out.println("Enter 1: Generate summary");
                            Scanner output1 = new Scanner(System.in);
                            String e1 = output1.nextLine();
                            if(e1.equals("1")){
                                CPLranking.generateSummaryCPL();
                            }
                            CPLranking.displayResultcpl();
                            System.out.println("Enter 1: Get audit file");
                            System.out.println("Enter 2: Send to media");
                            System.out.println("Enter 3: Log out");
                            while (login.loginStatus){
                                Scanner output = new Scanner(System.in);
                                String e = output.nextLine();
                                if (e.equals("1")) {
                                    CPLranking.auditfilecpl();
                                    System.out.println("Please choose where you want to save the file.");
                                    myjframe1 x = new myjframe1();
                                    System.out.println("Audit file successfully generated and saved, you can do other operations. ");
                                }
                                if (e.equals("2")) CPLranking.sendToMediacpl();
                                else if (e.equals("3")) {
                                    login.logout();
                                    if(!login_inst.loginStatus){
                                        System.exit(0);
                                    }
                                }
                            }
                        } else System.out.println("Invalid file format!");
                    }
                } catch(IOException e) {e.printStackTrace();}
            } else System.out.println("File does not exist!");
        }
    }
}
