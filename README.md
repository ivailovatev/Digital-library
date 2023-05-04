# Digital library
 
Digital library application
Functional requirements
The application should be an internet site that will allow users to make their own digital libraries. Three types of users:
1.	Readers
2.	Authors
3.	Admins
Depending of the profile of the users are available different operations.
Readers
The readers should be able to create account on the system as they provide username and a password. The readers should be able to search books (with given name or author), add them to their library and read them. They should be able to see the last books they read, sort their library by authors/genre/name and give ratings to the books. The readers should not be able to upload any books.
Authors
The authors should be able to create account on the system as they provide username and password. The authors should be able to upload their books on the system and make them available or not to the readers. The books should be able to be added to the readers libraries and should be able to be rated. The author should be able to see every book he/she uploaded in how much libraries appear and what it is ratings.
Admins
The admins should be able to lock/unlock accounts.
Technical requirements
-	Spring boot
-	Spring JDBC for a persistence
-	Unit tests
-	SQL Server/In-memory database for data storage
-	Maven
-	The code must use Java 8
-	Logging
