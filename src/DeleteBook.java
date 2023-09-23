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
        String userInput;
        boolean deleted = false;
        ArrayList<String> updatedCollection = new ArrayList<>();

        do {
            System.out.println("Please enter the ID number or title of the book you want to delete: ");
            userInput = input.nextLine().trim();

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

        return updatedCollection;
    }//end selectBook

    /*
     method name: deleteById
     Deletes a book from the collection based on its ID.
     Returns true if the book was found and deleted; otherwise, returns false.
     */

    public static boolean deleteById(ArrayList<String> collection, ArrayList<String> updatedCollection, int id) {
        boolean found = false;
        for (String book: collection) {
            String[] bookDetails = book.split(",");
            if (bookDetails.length >= 1) {
                int bookId;
                try {
                    bookId = Integer.parseInt(bookDetails[0]);
                }catch (NumberFormatException e) {
                    updatedCollection.add(book);
                    continue;
                }
                if (bookId == id) {
                    found = true;
                }else {
                    updatedCollection.add(book);
                }
            }
        }
        return found;
    }//end deleteById

    /*
     method name: deleteByTitle
     Deletes a book from the collection based on its title.
     Returns true if the book was found and deleted; otherwise, returns false.
     */

    public static boolean deleteByTitle(ArrayList<String> collection, ArrayList<String> updatedCollection, String title) {
        boolean found = false;
        for (String book: collection) {
            String[] bookDetails = book.split(",");
            if (bookDetails.length >= 2) {
                String bookTitle = bookDetails[1].trim();
                if (bookTitle.equalsIgnoreCase(title)) {
                    found = true;
                }else {
                    updatedCollection.add(book);
                }
            }
        }
        return found;
    }//end deleteByTitle


}//end class




