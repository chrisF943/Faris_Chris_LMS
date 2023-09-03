import java.util.ArrayList;
import java.util.Scanner;

/*
Chris Faris
Software Development 1
9/3/2023
class name: DeleteBook
contains the methods to delete a book from the collection, called in the Main class when
the user wants to delete a book
 */
public class DeleteBook {

    /*
     method name: selectBook
     asks the user to enter an ID of a book to delete, iterates through the collection and
     if it is found deletes that book from the collection and updates the collection
     passed in the created ArrayList as a String parameter, returns the updated collection
     */
    public static ArrayList<String> selectBook(ArrayList<String> collection) {
        Scanner input = new Scanner(System.in);
        int idToDelete = -1;

        //will ask for ID until a valid number not already in the collection is entered
        while (idToDelete < 0 || !hasValidId(collection, idToDelete)) {
            System.out.print("Please enter the ID number of the book you want to delete: ");

            try {
                idToDelete = Integer.parseInt(input.nextLine());
            //ensures the user is entering a number value
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid ID number.");
                idToDelete = -1; // Reset idToDelete
            }//end try-catch

            //cannot find ID in collection
            if (!hasValidId(collection, idToDelete)) {
                System.out.println("Invalid book ID or book not found in the collection.");
            }//end if
        }//end while

        boolean deleted = false;
        //updates the collection
        ArrayList<String> updatedCollection = new ArrayList<>();

        /*iterates through collection and searches for ID to delete, excludes that book and keeps all other
        books in the updated collection
        */
        for (String book : collection) {
            String[] bookDetails = book.split(",");

            if (bookDetails.length >= 1) {
                int bookId;
                try {
                    bookId = Integer.parseInt(bookDetails[0]);
                } catch (NumberFormatException e) {
                    System.out.println("Invalid book ID found in the collection.");
                    updatedCollection.add(book);
                    continue;
                }//end try-catch

                if (bookId != idToDelete) {
                    updatedCollection.add(book);
                } else {
                    deleted = true;
                }//end inner if else
            }//end outer if
        }//end for

        if (deleted) {
            System.out.println("Book with ID " + idToDelete + " has been deleted.");
        } else {
            System.out.println("Book with ID " + idToDelete + " was not found in the collection.");
        }//end if else

        return updatedCollection;
    }//end selectBook

    /*
     method name: hasValidId
    checks to see if both the ID entered and the ID of the book in the collection are valid
    passed in the created ArrayList as a String parameter and the id variable created in addBook
    returns true if the ID exists and false if it does not
     */
    private static boolean hasValidId(ArrayList<String> collection, int id) {
        //iterates through collection and checks each id by parsing
        for (String book : collection) {
            String[] bookDetails = book.split(",");
            //checks if ID for each book is valid
            if (bookDetails.length >= 1) {
                int bookId;
                try {
                    bookId = Integer.parseInt(bookDetails[0]);
                } catch (NumberFormatException e) {
                    continue;
                }//end try-catch
                if (bookId == id) {
                    return true;
                }//end inner if
            }//end outer if
        }//end for
        return false;
    }//end hasValidId


}//end class





