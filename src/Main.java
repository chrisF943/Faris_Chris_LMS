import java.util.ArrayList;

import java.util.Scanner;
/*
Chris Faris
Software Development 1
9/25/2023
class name: Main
This class contains the Main method which executes the menu switch system and calls methods from this class
and the 2 other classes, the overall objective of this program is to read from a text file and create
an ArrayList, allow the user to add, view or delete from said list and ensure all changes are written to the text file.
*/

public class Main {
    static Scanner input = new Scanner(System.in);

    /*
     main method that reads from the given text file and creates an ArrayList, executes switch menu which
     allows the user to choose an action to perform with the newly created ArrayList
     no parameters and no return
     */

    public static void main(String[] args) {
        char choice;
        //reads text file and creates ArrayList using it, executes switch menu
        ArrayList<String> collection = ReadFile.getBooks("BookCollection.txt");
        System.out.println("Successfully added to collection from text file, please select an option: ");
        System.out.println("-----------------------------------------------");
        do {
            //sets choice to get whatever letter is entered by user and performs corresponding action
            choice = getChoice();
            //menu system with 4 options, will also detect invalid choice input
            switch (choice) {
            //asks user for book details and creates new book entry in the collection, writes to text file
                case 'A':
                    addBook(collection);
                    ReadFile.writeFile(collection,"BookCollection.txt");
                    break;
            //asks user for book ID and deletes book from the collection, writes to text file
                case 'D':
                    collection = DeleteBook.selectBook(collection);
                    ReadFile.writeFile(collection,"BookCollection.txt");
                    break;
            //asks user for title of book and checks it in to the collection (once database is implemented)
                case 'I':
                    addBook(collection);
                    ReadFile.writeFile(collection,"BookCollection.txt");
                    break;
            //asks user for title of book and checks it out from the collection (once database is implemented)
                case 'O':
                    collection = DeleteBook.selectBook(collection);
                    ReadFile.writeFile(collection,"BookCollection.txt");
                    break;
                //shows current collection
                case 'V':
                    System.out.println("Here is the current book collection: \n");
                    displayCollection(collection);
                    System.out.println("\n");
                    break;
            //quits
                case 'Q':
                    System.out.println("Thank you.");
                    break;
            //displays when invalid selection is entered
                default:
                    System.out.println("Invalid selection.");
                    break;
            } // end switch
        } while (choice != 'Q'); // end do while


    }//end main

    /*
        method name: displayCollection
        iterates through the collection ArrayList and displays each entry
        passed in the created ArrayList as a String parameter, no return
     */
    public static void displayCollection(ArrayList<String> collection) {
        System.out.println("Formatted as: (ID number, Title, Author)\n");
        for (int i = 0; i < collection.size(); i++) {
            System.out.println(collection.get(i));
            System.out.println("----------------------------------------------");
        }//end for
    }//end displayCollection

    /*
     method name: addBook
     initializes what makes up a book as separate variables, asks the user for the details of each variable
     and creates a new book entry in the collection
     passed in the created ArrayList as a String parameter, no return
     */
    public static void addBook(ArrayList<String> collection) {
        int id ;
        String author;
        String title;
        boolean validId;
        //ensures the user enters a valid ID that is not already in the collection by calling the doesIdExist method
        do {
            System.out.println("Please enter an ID number");
            id = 6;
            validId = !doesIdExist(collection, id);
            if (!validId) {
                System.out.println("Cannot use an ID number that already exists");
            }
        } while (!validId); //end do while
        // title and author are set
        System.out.println("Please enter the title of the book");
        title = "test1";
        System.out.println("Please enter the author of the book");
        author = "john1";
        //creates new book entry as a String with the previously entered variables
        String book = id + "," + title + "," + author;
        //adds new book to the collection
        collection.add(book);
        System.out.println("Book has successfully been checked in to the collection. \n");
    }//end addBook

    /*
     method name: doesIdExist
     checks if the ID entered already exists in the collection by parsing and looking at the
     number before the first comma which represents the ID of each book
     passed in the created ArrayList as a String parameter and the id variable created in addBook
     returns true if the ID exists and false if it does not
     */
    private static boolean doesIdExist(ArrayList<String> collection, int id) {
        //iterates through collection and checks each id by parsing
        for (String book : collection) {
            String[] bookDetails = book.split(",");
            int existingId = Integer.parseInt(bookDetails[0]);
            if (existingId == id) {
                return true;
            }//end if
        }//end for
        return false;
    }//end doesIdExist

    /*
    method name: getChoice
    creates result variable which collects user input and returns it as a char result
    prints out prompts for each menu choice
    no parameters and returns a char result
     */
    public static char getChoice() {
        char result = ' ';
        //creates prompts which are collected into a single String
        String menu = "Press 'D' to Delete a book from the collection. \n";
        menu += "----------------------------------------------\n";
        menu += "Press 'A' to Add additional book(s) to the collection.\n";
        menu += "----------------------------------------------\n";
        menu += "Press 'I' to check a book in to the collection.\n";
        menu += "----------------------------------------------\n";
        menu += "Press 'O' to check a book out from the collection.\n";
        menu += "----------------------------------------------\n";
        menu += "Press 'V' to View the collection.\n";
        menu += "----------------------------------------------\n";
        menu += "Press 'Q' to quit\n";
        menu += "----------------------------------------------\n";
        //prints menu and collects user input
        System.out.println(menu);
        result = input.nextLine().toUpperCase().charAt(0);
        return result;
    } // end getChoice
}//end class