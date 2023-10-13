import java.util.ArrayList;
import java.util.Scanner;

/*
Chris Faris
Software Development 1
9/25/2023
class name: DeleteBook
contains the methods to delete a book from the collection, called in the Main class when
the user wants to delete a book
 */
public class DeleteBook {

    /*
     method name: selectBook
     asks the user to enter an ID or title of a book to delete, iterates through the collection and
     if it is found deletes that book from the collection and updates the collection
     passed in the created ArrayList as a String parameter, returns the updated collection
     */
    public static ArrayList<String> selectBook(ArrayList<String> collection) {
        Scanner input = new Scanner(System.in);
        String userInput;
        boolean deleted = false;
        ArrayList<String> updatedCollection = new ArrayList<>();
        //checks if user input is a integer and calls the deleteById method, if not calls the deleteByTitle method
        do {
            System.out.println("Please enter the ID number or title of the book you want to delete: ");
            userInput = "test2";

            if (userInput.matches("\\d+")) {
                int idToDelete = Integer.parseInt(userInput);
                deleted = deleteById(collection, updatedCollection, idToDelete);
            }else {
                deleted = deleteByTitle(collection, updatedCollection, userInput);
            }
            if (!deleted) {
                System.out.println("That book does not exist in the collection, please try again");
            }
        }while (!deleted);
        System.out.println("Book has successfully been checked out from the collection. \n");
        return updatedCollection;
    }//end selectBook

    /*
     method name: deleteById
     Deletes a book from the collection based on its ID.
     Returns true if the book was found and deleted; otherwise, returns false.
     Parameters are both the collection and the updated collection ArrayLists and the id
     */

    public static boolean deleteById(ArrayList<String> collection, ArrayList<String> updatedCollection, int id) {
        boolean found = false;
        /*searches through the collection for ID number entered by looking at first value before first comma
          if the book exists then found is set to true and the book is deleted, else the collection is kept unchanged
        */
        for (String book: collection) {
            String[] bookDetails = book.split(",");
            if (bookDetails.length >= 1) {
                int bookId;
                try {
                    bookId = Integer.parseInt(bookDetails[0]);
                }catch (NumberFormatException e) {
                    updatedCollection.add(book);
                    continue;
                }//end try catch
                if (bookId == id) {
                    found = true;
                }//end inner if
                else {
                    updatedCollection.add(book);
                }//end else
            }//end outer if
        }//end for
        return found;
    }//end deleteById

    /*
     method name: deleteByTitle
     Deletes a book from the collection based on its title.
     Returns true if the book was found and deleted; otherwise, returns false.
     Parameters are both the collection and the updated collection ArrayLists and the title
     */

    public static boolean deleteByTitle(ArrayList<String> collection, ArrayList<String> updatedCollection, String title) {
        boolean found = false;
        /*searches through the collection for title entered by looking at value after first comma
          if the book exists then found is set to true and the book is deleted, else the collection is kept unchanged
          equalsIgnoreCase is used to avoid case sensitivity between user input and book title in the collection
        */
        for (String book: collection) {
            String[] bookDetails = book.split(",");
            if (bookDetails.length >= 2) {
                String bookTitle = bookDetails[1].trim();
                if (bookTitle.equalsIgnoreCase(title)) {
                    found = true;
                }//end inner if
                else {
                    updatedCollection.add(book);
                }//end else
            }//end outer if
        }//end for
        return found;
    }//end deleteByTitle


}//end class





