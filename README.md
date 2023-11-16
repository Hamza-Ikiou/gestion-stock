# Stock Management Application

This application was developed in Java 11 using PostgreSQL 14 and the JDBC 42.6 library for PostgreSQL.

The following configuration instructions are for an IDE. Command-line configuration is not available at the moment.

## Prerequisites

Make sure you have installed the following:

- PostgreSQL on your system
- A recent version of Java (Java 11 or higher)
- The [JDBC](https://jdbc.postgresql.org/download/) library for PostgreSQL

## Database Configuration

After cloning the project and installing PostgreSQL, follow these steps:

1. Create a PostgreSQL database with a name of your choice.
2. Create a user with a name of your choice and a password, or use the default user (postgres).
3. Execute the provided SQL script in [script-postgres](src/sql/script-postgres.sql) to create the necessary tables.
You can use the following command to run the SQL script (make sure to have superuser privileges and to replace the italic variables with your own):
```markdown
psql -h _HOST_ -p _PORT_ -U _USER_ -d _DATABASE_NAME_ -a -f _PATH_TO_SCRIPT_
```

## Project Configuration

Once the database is configured, perform the following steps:

1. Run the project on your IDE.
2. Configure your project to use the previously downloaded JDBC library.
3. Configure the first 5 variables in the [ConnexionDB](src/dal/ConnexionDB.java) class with your database information.

## Starting the Application

Once the previous steps are completed, you can start the application by running the [FenetreAccueil](src/presentation/FenetreAccueil.java) class.