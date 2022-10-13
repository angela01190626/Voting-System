# Voting System

## Instructions
1. Run voting_system.java. You can either do this in IntelliJ or in Terminal. To run it in terminal, enter commmand "javac voting_system.java" under the src repository to compile, and then enter command "java voting_system" to run.
2. After the program is run, you will have the two options, which are "r" for registration and "l" for login. If you enter any other options, you will need to re-enter one of the two correct options in order to proceed. Also, if you are a first time user, you must register before logging in.
3. You have 6 attempts of entering a valid password for registration. If you fail to do so, the program will exit. The valid username-password combination will be saved to userinfo.txt for further use. After you register, you will be automatically logged in.
4. Alternatively, if you opt to log in, you will have 6 attempts of entering a valid username and a correct password respectively. If you fail to do so, the program will exit. The program will read from userinfo.txt to test the correctness of your combination.
5. Once you are logged in, you will be asked to select a .csv file.
6. Depending on your input file, the system will check which algorithm will be used by reading the first line of the .csv file.
7. The correct algorithm will be run, which read the .csv file, gather all the information needed, and sort the data to produce the desired output.
8. After the algorithm is run, the result will be displayed to the terminal.
9. After that, you will be given several options, such as send to media, generate an audit file, and log out.
10. After all is done, you will be logged out.

## Documentation
1. install IntelliJ in your laptop
2. open our project file
3. Go to Tools -> Generate JavaDoc
4. Please follow the image for settings!
   ![generate-documentation-alt tag](https://media.github.umn.edu/user/8706/files/cd77e480-07d0-11ea-95ff-ccfd9a9214cd)
5. Aftering clicking OK, the documentation is generated. Your browser will open our index.html

## Testing
1. There are three unit test files for the bug fix part, which are TestAuditfileCPL.java, TestCloseListRanking.java and TestOpenListRanking.java, and they are in src folder.
2. To run them, do "javac xxx.java" then "java xxx" in terminal. To run test for a particular csv file, you might need to comment out unnecessary parts. In TestAuditfileCPL.java, only one of line 15-19 should not be commented. In TestCloseListRanking.java, there are 5 cases in the main function and you should test one of them at a time by commenting out the others.
3. The test log for the unit tests and system test are all in testinglogs.pdf in testing folder. Notice that the unit tests for two new GUIs are also in this file.
4. testinglogs.docx is the testing documents we copied from Project 1.
5. testinglogs.pdf is the testing documents for project 2.
6. There are total of 10 csv files for testing, with 5 for CPL election, and 5 for OPL election.

## New Functionalities
A. Bug fix
1. We fixed our OPL and CPL algorithms.

B. CSV file search GUI
1. This GUI helps users search and select csv files.
2. There are four options, which are "Previous", "Cancel", "Goto" and "Select".
3. The GUI displays the current directory with the information of the files it contains.
4. There are two scroll bars which enable users to view contents that do not fit into the screen.
5. There are three file types, which are "F-Files", "DIR-Directories", and "CSV-CSV files".
6. "Previous" directs users to the parent directory. Clicking "Previous" at the system root prompts "There is no previous directory!" to be displayed in terminal.
7. "Cancel" cancels the most recent operation. Clicking "Cancel" on the default page prompts "Cancellation is not available!" to be displayed in terminal.
8. Entering a number in the textfield of "Goto" directs users to the corresponding directory. If the number does not correspond to a directory, or is out of range, this operation will fail with an error message displayed in terminal.
9. Entering a number in the textfield of "Select" selects the csv file and terminates the GUI. If the number does not correspond to a csv file, this operation will fail with an error message displayed in terminal.

C. Audit file saving and renaming GUI
1. This GUI helps users rename the generated audit file, and save it to other place in the computer.
2. When this GUI opens, users have to touch the corner of the frame and adjust the size of frame, then munu "save file" can appear.
3. In "save file" munu, there is a munubar "Select place and rename File", By click the menu bar, users can rename and save file to anywhere in the computer. 
4. If users want to save the same file in another name to another path, just click "Select place and rename File" again, then type your file name and select path. 
6. After finishing saving, click "close" on the top left corner. Then go back to terminal and choose what users want to do next.
7. If users just want to save the file into default place with default name, user can just click "close" on the top left corner.

