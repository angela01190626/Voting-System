/**
 * ---Here is the file header---
 * File name: SearchCSV
 * Author: Hanzhang Wu(major author), Jerry Nie, Yanjiawen Xu and Yanjun Cui.
 * Description: Here is the sub system of searching CSV file in GUI. It contains: 
 * csv_search(): It is the core function to achieve the searching/selecting csv file
 * through a GUI. Several actionPerformed() functions are design init.   
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SearchCSV {
    static String path;
    static String fname;
    static File cfile;
    static File[] files;//queue of files
    static String s;
    static List<String> paths = new ArrayList<>();
    static final Object lock = new Object();
    
     /**
     * the function that allows users to search for csv files
     * the function does not return anything<br>
     * <p>
     *
     * @throws IOException When the file could not be created
     *
     */
    public static void csv_search() throws IOException {
        JFrame frame = new JFrame("CSV File Search");//frame initialized
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);
        JPanel panel = new JPanel();
        JTextArea ta = new JTextArea();//test area displaying contents
        JScrollPane scrollPane = new JScrollPane(ta);//scroll bar initialized
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);//vertical bar
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);//horizontal bar
        scrollPane.setBounds(0, 0, 500, 500);
        JPanel contentPane = new JPanel(null);
        contentPane.setPreferredSize(new Dimension(500, 500));
        contentPane.add(scrollPane);
        JButton prev = new JButton("Previous");//button "Previous"
        JButton cancel = new JButton("Cancel");//button "Cancel"
        JButton next = new JButton("Goto");//button "Goto"
        JButton select = new JButton("Select");//button "Select"
        JTextField tf = new JTextField(2);//test file corresponding to "Goto"
        JTextField ts = new JTextField(2);//test file corresponding to "Select"
        panel.add(prev);
        panel.add(cancel);
        panel.add(next);
        panel.add(tf);
        panel.add(select);
        panel.add(ts);
        path = ".";//path of the current file
        cfile = new File(path);
        files = cfile.listFiles();
        try {
            path = cfile.getCanonicalPath();
        } catch(IOException err) {err.printStackTrace();}
        String root = path;
        //initialize contents on the default page
        s = "";
        s = s.concat("Current directory: ");
        s = s.concat(path);
        s = s.concat("\n");
        for (int i = 0; i < files.length; i++){
            fname = files[i].getName();
            if (files[i].isDirectory()) s = s.concat("DIR ");
            else {
                if (fname.length() >= 4){
                    if (fname.substring(fname.length()-4, fname.length()).equals(".csv")){
                        s = s.concat("CSV ");
                    } else s = s.concat("F ");
                } else s = s.concat("F ");
            }
            s = s.concat(Integer.toString(i));
            s = s.concat(" ");
            s = s.concat(fname);
            s = s.concat("\n");
        }
        ta.setText(s);
        ActionListener prev_action = new ActionListener(){
            /**
            * the function directs users to the parent directory
            * the function does not return anything<br>
            * <p>
            *
            *
            */
            public void actionPerformed(ActionEvent e){
                if (path.equals("/")){//when the user is at the root of the system
                    System.out.println("There is no previous directory!");
                } else {
                    paths.add(path);
                    path = path.concat("/..");
                    cfile = new File(path);//change the path to correspond to the parent directory
                    try {
                        path = cfile.getCanonicalPath();
                    } catch(IOException err) {err.printStackTrace();}
                    files = cfile.listFiles();
                    s = "";
                    s = s.concat("Current directory: ");
                    s = s.concat(path);
                    s = s.concat("\n");
                    for (int i = 0; i < files.length; i++){
                        fname = files[i].getName();
                        if (files[i].isDirectory()) s = s.concat("DIR ");
                        else {
                            if (fname.length() >= 4){
                                if (fname.substring(fname.length()-4, fname.length()).equals(".csv")){
                                    s = s.concat("CSV ");
                                } else s = s.concat("F ");
                            } else s = s.concat("F ");
                        }
                        s = s.concat(Integer.toString(i));
                        s = s.concat(" ");
                        s = s.concat(fname);
                        s = s.concat("\n");
                    }
                    ta.setText(s);
                }
            }
        };
        ActionListener cancel_action = new ActionListener(){
            /**
            * the function cancels the most recent operation
            * the function does not return anything<br>
            * <p>
            *
            *
            */
            public void actionPerformed(ActionEvent e){
                String temp = path;
                if (paths.size() == 0 || temp.equals(root)){//at the default page
                    System.out.println("Cancellation is not available!");
                } else{
                    String pfile = paths.get(paths.size()-1);
                    paths.remove(paths.size()-1);//retrieve the most recent path
                    cfile = new File(pfile);
                    try {
                        path = cfile.getCanonicalPath();
                    } catch(IOException err) {err.printStackTrace();}
                    files = cfile.listFiles();
                    s = "";
                    s = s.concat("Current directory: ");
                    s = s.concat(cfile.getAbsolutePath());
                    s = s.concat("\n");
                    for (int i = 0; i < files.length; i++){
                        fname = files[i].getName();
                        if (files[i].isDirectory()) s = s.concat("DIR ");
                        else {
                            if (fname.length() >= 4){
                                if (fname.substring(fname.length()-4, fname.length()).equals(".csv")){
                                    s = s.concat("CSV ");
                                } else s = s.concat("F ");
                            } else s = s.concat("F ");
                        }
                        s = s.concat(Integer.toString(i));
                        s = s.concat(" ");
                        s = s.concat(fname);
                        s = s.concat("\n");
                    }
                    ta.setText(s);
                }
            }
        };
        ActionListener next_action = new ActionListener(){
            /**
            * the function directs users to their desired directory based on the inputs
            * the function does not return anything<br>
            * <p>
            *
            *
            */
            public void actionPerformed(ActionEvent e){
                if (tf.getText().isEmpty()){//the input is empty
                    System.out.println("Please enter a directory index!");
                } else{
                    Integer index = Integer.parseInt(tf.getText());//takes in the index
                    if (index < 0 || index >= files.length){//the index is negative or out of bound
                        System.out.println("Invalid directory index!");
                    } else{
                        File input = files[index];
                        if (input.isDirectory()){
                            paths.add(path);
                            path = path.concat("/");
                            path = path.concat(input.getName());
                            files = new File(path).listFiles();
                            cfile = new File(path);
                            try {
                                path = cfile.getCanonicalPath();
                            } catch(IOException err) {err.printStackTrace();}
                            s = "";
                            s = s.concat("Current directory: ");
                            s = s.concat(cfile.getAbsolutePath());
                            s = s.concat("\n");
                            for (int i = 0; i < files.length; i++){
                                fname = files[i].getName();
                                if (files[i].isDirectory()) s = s.concat("DIR ");
                                else {
                                    if (fname.length() >= 4){
                                        if (fname.substring(fname.length()-4, fname.length()).equals(".csv")){
                                            s = s.concat("CSV ");
                                        } else s = s.concat("F ");
                                    } else s = s.concat("F ");
                                }
                                s = s.concat(Integer.toString(i));
                                s = s.concat(" ");
                                s = s.concat(fname);
                                s = s.concat("\n");
                            }
                            ta.setText(s);
                        } else System.out.println("The file selected is not a directory!");
                    }
                }
            }
        };
        ActionListener select_action = new ActionListener(){
            /**
            * the function directs users to select the csv file
            * the function does not return anything<br>
            * <p>
            * @param none
            *
            */
            public void actionPerformed(ActionEvent e){
                if (ts.getText().isEmpty()){//the input is empty
                    System.out.println("Please enter a file index!");
                } else {
                    File input = files[Integer.parseInt(ts.getText())];//takes in the input
                    fname = input.getName();
                    if (fname.length() >= 4 && !input.isDirectory()){
                        if (fname.substring(fname.length()-4, fname.length()).equals(".csv")){
                            try {
                                path = input.getCanonicalPath();
                            } catch(IOException err) {err.printStackTrace();}
                            System.out.println("The csv file has been selected!");
                            frame.setVisible(false);// the frame is not visible now
                            synchronized (lock) {//unlock, the system stops waiting
                                lock.notifyAll();
                            }
                        }
                        else System.out.println("The file selected is not a csv file!");
                    } else System.out.println("The file selected is not a csv file!");
                }
            }
        };
        prev.addActionListener(prev_action);
        cancel.addActionListener(cancel_action);
        next.addActionListener(next_action);
        select.addActionListener(select_action);
        frame.getContentPane().add(BorderLayout.CENTER, contentPane);
        frame.getContentPane().add(BorderLayout.SOUTH, panel);
        frame.pack();
        frame.setVisible(true);
    }
}
