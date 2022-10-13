# Voting System

## Instructions
1. Run voting_system.java. You can either do this in IntelliJ or in Terminal. To run it in terminal, enter commmand "javac voting_system.java" under the src repository to compile, and then enter command "java voting_system" to run.
2. After the program is run, you will have the two options, which are "r" for registration and "l" for login. If you enter any other options, you will need to re-enter one of the two correct options in order to proceed. Also, if you are a first time user, you must register before logging in.
3. You have 6 attempts of entering a valid password for registration. If you fail to do so, the program will exit. The valid username-password combination will be saved to userinfo.txt for further use. After you register, you will be automatically logged in.
4. Alternatively, if you opt to log in, you will have 6 attempts of entering a valid username and a correct password respectively. If you fail to do so, the program will exit. The program will read from userinfo.txt to test the correctness of your combination.
5. Once you are logged in, you will be asked to provide the path of the .csv file.
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
1. The unit tests are all in the src folder.
2. To run each unit test, please run the main function. 
3. The test log for the unit tests and system test are all in testing folder. 
4. The testing .csv files are all in testing folder. 
