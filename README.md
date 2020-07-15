# Illegal-Acitivity-Detection-in-a-Chat-Application
This was my 6th semester MINI Project which I did using Java and MySQL. The GUI was made using Swing in Java


Backend Connectivity:
In each of the files which require a database connection, the programmer will first need to establish the connection by providing her/her database deatails and password.Once that is done, you can proceed by running the programs.The Database used is MySQL.
You will need to create two basic tables one for user details and second for storing the flagged messages.
userdetails(uid,username,password,phonenumber)
flagged(username,date,time,message);

FrontPage: The Frontpage is connected to everyother page except the Server Page as that runs separately. If you have not registered a user, first you need to register before you login. 
RegisterPage: This allows you to register a user to the database.
LoginPage: Once logged in you can can enter the ClientChat Window.
ClientPage: The Client page will have the option of establishing a new connection to the server, but the server needs to be running.

ServerPage: Run the server page separately, if it is in localhost, no changed other than maybe port number needs to be made, if it is not IP address also needs to be changed.
Once the client establishes the connection, messages can be start to sent.

In the client and server page, there are a list of words stored, these act as teh flagged words, they can be modified for different purposes. If any of these words are mentioned then the string matching algoritm, KMP will run on each word of each message to check if it exists, if yes the details of the sender and the message will be stored in the flagged database.
