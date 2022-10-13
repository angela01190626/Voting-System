/**
 * ---Here is the file header---
 * File name: myjframe1
 * Author: Yanjun Cui(major author), Jerry Nie, Hanzhang Wu and Yangjiawen Xu.
 * Description: Here is the sub system of finding a path for saving audit file through a GUI. It contains: 
 * myjframe1(): It is the core function to achieve finding a path for saving audit file through GUI
 * through a GUI. Several actionPerformed() functions are designed in it. 
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

//class for rename and saving file using gui
public class myjframe1 extends JFrame implements ActionListener {

    JMenuBar menuBar = new JMenuBar();
    JMenu file = new JMenu();
    JMenuItem open = new JMenuItem();


    public myjframe1() {
        setTitle("Saving Audit File");
        setSize(400, 400);
        setVisible(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        menuBar.add(file);
        file.setText("Save File");
        file.add(open);
        setJMenuBar(menuBar);
        open.setText("Select place and rename File");
        setLayout(new FlowLayout(FlowLayout.CENTER));
        open.addActionListener(this);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == open) {
            JFileChooser save = new JFileChooser();
            int option = save.showSaveDialog(this);
            File file = new File(save.getSelectedFile().getPath());
            try {
                // String source = textArea.getText();
                InputStream is = new FileInputStream("audit.txt");
                BufferedReader buf = new BufferedReader(new InputStreamReader(is));
                String line = buf.readLine();
                StringBuilder sb = new StringBuilder();
                while (line != null) {
                    sb.append(line).append("\n");
                    line = buf.readLine();
                }
                //  String fileAsString = sb.toString();
                // String source = textArea.getText();
                String source = sb.toString();
                // System.out.println(source);
                char buffer[] = new char[source.length()];
                source.getChars(0, source.length(), buffer, 0);

                FileWriter f1 = new FileWriter(file + ".txt");
                for (int i = 0; i < buffer.length; i++) {
                    f1.write(buffer[i]);
                }
                f1.close();
            } catch (Exception ae) {
            }
        }
    }
}
