import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/*
Chris Faris
Software Development 1
11/10/2023
class name: DeleteBook
contains the method prompting the user to enter a barcode or title of a book they want to delete
as well as the methods for deleting a book from the database for both of those scenarios
and the method for executing the SQL statement
 */
public class DeleteBook {

    /*asks the user to enter a barcode or title of a book to delete, iterates through the database and
     if it is found the appropriate method is called and SQL statement is executed
     has no parameters and no return
     */
    public static void selectBook() {
        String userInput;
        String url = "jdbc:sqlite:/Users/chrisfaris/collection.db";
        boolean deleted;
        //checks if user input is a integer and calls the deleteById method, if not calls the deleteByTitle method
        do {
            userInput = JOptionPane.showInputDialog("Please enter the ID number or title of the book you want to delete: ");

            if (userInput.matches("\\d+")) {
                int barcodeToDelete = Integer.parseInt(userInput);
                deleted = deleteByBarcode(barcodeToDelete, url);
            }else {
                deleted = deleteByTitle(userInput, url);
            }
            if (!deleted) {
                JOptionPane.showMessageDialog(null, "That book does not exist in the database, please try again");
            }
        }while (!deleted);
        JOptionPane.showMessageDialog(null, "Book has successfully been deleted from the database. \n");
    }//end selectBook

    /*is called if an instance of an integer is detected, contains the SQL statement to delete a book by barcode
    the barcode of the book is passed in as well as the url of the database, returns the executeDeleteSQL method
    with the parameters of the barcode and the SQL statement
     */
    public static boolean deleteByBarcode(int barcode, String url) {
        String sql = "DELETE FROM books WHERE barcode = ?";
        return executeDeleteSQL(barcode, sql);
    }//end deleteByBarcode

    /*is called if an instance of a string is detected, contains the SQL statement to delete a book by title
    the title of the book is passed in as well as the url of the database, returns the executeDeleteSQL method
    with the parameters of the title and the SQL statement
    */
    public static boolean deleteByTitle(String title, String url) {
        String sql = "DELETE FROM books WHERE title = ?";
        return executeDeleteSQL(title, sql);
    }//end deleteByTitle


    /*connects to the database and executes the appropriate SQL statement for deleting a book, based off
    the value parameter passed in which will either be a barcode or title
     */
    public static boolean executeDeleteSQL(Object value, String sql) {
        String url = "jdbc:sqlite:/Users/chrisfaris/collection.db";

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = DriverManager.getConnection(url);
            preparedStatement = connection.prepareStatement(sql);

            //if the value is an integer, set the value as an integer, else set the value as a string
            if (value instanceof Integer) {
                preparedStatement.setInt(1, (Integer) value);
            } else if (value instanceof String) {
                preparedStatement.setString(1, (String) value);
            }

            int rowsDeleted = preparedStatement.executeUpdate();
            return rowsDeleted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }//end try catch
        //close resources
        finally {
                try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }//end try catch
        }//end finally
    }//end executeDeleteSQL

}//end class





