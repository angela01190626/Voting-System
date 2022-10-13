/**
 * ---Here is the file header---
 * File name: login
 * Author: Hanzhang Wu(major author), Jerry Nie, Yangjiawen Xu and Yanjun Cui.
 * Description: Here is the sub system of login and logout. It contains: 
 * register(): users use this function to register an account;
 * login(): for users login;
 * logout(): for users logout.
 */

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Scanner;

public class login {
    static boolean loginStatus = false;

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
}
