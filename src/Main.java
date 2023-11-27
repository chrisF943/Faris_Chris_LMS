import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

/*
Chris Faris
Software Development 1
11/10/2023
class name: Main
this class contains the main method that runs the LMS GUI, as well as the methods for adding, checking in
and checking out books, and the method for communicating with the database when a new book is added
*/

public class Main {
    private JFrame frame;
    private JTextArea outputTextArea;
    private JPanel mainPanel;

    /**
     *
     */
    public Main() {

        //sets up the frame and panel properties, adds a header message and a message prompt to select an option

        frame = new JFrame("Welcome to the LMS!");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(700, 400);
        frame.setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        outputTextArea = new JTextArea(10, 40);
        outputTextArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(outputTextArea);

        JPanel buttonPanel = new JPanel();

        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        frame.add(mainPanel, BorderLayout.CENTER);

        JLabel welcomeLabel = new JLabel("Please select an option below.");
        welcomeLabel.setHorizontalAlignment(JLabel.CENTER);
        frame.add(welcomeLabel, BorderLayout.NORTH);

        Font welcomeFont = new Font("font", Font.BOLD, 15);
        Font buttonFont = new Font("font", Font.BOLD, 13);
        Font textFont = new Font("font", Font.ITALIC, 14);
        welcomeLabel.setFont(welcomeFont);
        outputTextArea.setFont(textFont);
        frame.setBackground(Color.BLUE);
        buttonPanel.setBackground(Color.BLACK);
        outputTextArea.setBackground(Color.LIGHT_GRAY);
        Border blueline = BorderFactory.createLineBorder(Color.BLUE);
        Border blueline2 = BorderFactory.createLineBorder(Color.BLUE);
        Border raisedbevel = BorderFactory.createRaisedBevelBorder();
        Border compound = BorderFactory.createCompoundBorder(raisedbevel, blueline);
        outputTextArea.setBorder(compound);
        buttonPanel.setBorder(blueline2);

        //buttons and listeners are created for all 5 options, appropriate methods are called for each

        JButton viewButton = new JButton("View Collection");
        viewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                displayCollection();
            }//end actionPerformed
        });
        viewButton.setFont(buttonFont);

        JButton addButton = new JButton("Add Book");
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addBook();
            }//end actionPerformed
        });
        addButton.setFont(buttonFont);

        JButton deleteButton = new JButton("Delete Book");
        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                DeleteBook.selectBook();
            }//end actionPerformed
        });
        deleteButton.setFont(buttonFont);

        JButton checkoutButton = new JButton("Checkout Book");
        checkoutButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                checkOutBook();
            }//end actionPerformed
        });
        checkoutButton.setFont(buttonFont);

        JButton checkinButton = new JButton("Checkin Book");
        checkinButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                checkInBook();
            }//end actionPerformed
        });
        checkinButton.setFont(buttonFont);

        buttonPanel.add(viewButton);
        buttonPanel.add(addButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(checkoutButton);
        buttonPanel.add(checkinButton);

        frame.setVisible(true);
    }//end Main


    /*altered for use with GUI and database, connects to the database and prints all contents via a
    SELECT statement with no parameters and returns no return
    */

    /**
     *
     */
    public void displayCollection() {
        String url = "jdbc:sqlite:/Users/chrisfaris/collection.db";

        outputTextArea.setText("");

        try (Connection connection = DriverManager.getConnection(url)) {
            // Create a Statement object for executing SQL queries
            Statement statement = connection.createStatement();

            // SQL query to select all records from the "books" table
            String query = "SELECT * FROM books";

            // Execute the query and get the result set
            ResultSet resultSet = statement.executeQuery(query);

            outputTextArea.append("Here is the current collection:\n");
            outputTextArea.append("-------------------------------------------------------------------------------------\n");

            // Loop through the result set and print the contents
            while (resultSet.next()) {
                int barcode = resultSet.getInt("barcode");
                String title = resultSet.getString("title");
                String author = resultSet.getString("author");
                String genre = resultSet.getString("genre");
                String status = resultSet.getString("status");
                String dueDate = resultSet.getString("due_date");

                outputTextArea.append("Barcode: " + barcode + " ");
                outputTextArea.append("Title: " + title + " ");
                outputTextArea.append("By: " + author + " ");
                outputTextArea.append("Genre: " + genre + " ");
                outputTextArea.append("Status: " + status + " ");
                outputTextArea.append("Due Date: " + dueDate + " ");
                outputTextArea.append("\n");
                outputTextArea.append("-------------------------------------------------------------------------------------\n");
            }//end while
        } catch (SQLException e) {
            e.printStackTrace();
        }//end try catch
    }//end displayCollection

    /*also altered for use with GUI and database, connects to the database and adds a book to it
    with details provided by the user
    */

    /**
     *
     */
    public void addBook() {
        String url = "jdbc:sqlite:/Users/chrisfaris/collection.db";

        int barcode = 0;
        String title;
        String author;
        String genre;
        String status = "checked in";
        String dueDate = "NULL";

        String barcodeInput = JOptionPane.showInputDialog("Please enter a barcode:");
        if (barcodeInput == null) {
            // User clicked Cancel or closed the input dialog
            return;
        }//end if

        // Try to parse the barcode input
        try {
            barcode = Integer.parseInt(barcodeInput);
            if (doesBarcodeExist(barcode, url)) {
                JOptionPane.showMessageDialog(null, "A book with this barcode already exists.");
                return;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Invalid barcode. Please enter a valid number.");
            return;
        }//end try catch

        title = JOptionPane.showInputDialog("Please enter the title of the book:");
        author = JOptionPane.showInputDialog("Please enter the author of the book:");
        genre = JOptionPane.showInputDialog("Please enter the genre of the book:");

        // Create a new book entry as a String
        String book = barcode + "," + title + "," + author + "," + genre + "," + status + "," + dueDate;

        // Add the book to the database
        if (insertBookToDatabase(book)) {
            JOptionPane.showMessageDialog(null, "Book has successfully been added to the collection.");
        } else {
            JOptionPane.showMessageDialog(null, "Failed to add the book to the collection.");
        }//end if else
    }//end addBook


    /*iterates through the database to see if the id entered by user already exists, returns true if it does
    else returns false, also has the barcode passed in as a parameter and the url of the database
    */

    /**
     *
     * @param barcode
     * @param url
     * @return
     */
    public boolean doesBarcodeExist(int barcode, String url) {
          Connection connection = null;
          PreparedStatement preparedStatement = null;
          ResultSet resultSet = null;

          try {
              // Establish a connection to the database using the provided databaseURL
              connection = DriverManager.getConnection(url);

              String selectSQL = "SELECT barcode FROM books WHERE barcode = ?";

              // Prepare the SQL statement
              preparedStatement = connection.prepareStatement(selectSQL);
              preparedStatement.setInt(1, barcode);

              // Execute the SELECT statement
              resultSet = preparedStatement.executeQuery();

              // If the result set contains a row, the barcode exists; otherwise, it doesn't
              return resultSet.next();
          } catch (SQLException e) {
              e.printStackTrace();
              return false; // An error occurred, treat it as if the barcode exists
          } //end try catch
          finally {
                try {
                  // Close the resources
                  if (resultSet != null) {
                      resultSet.close();
                  }
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
    }//end doesBarcodeExist


    /*is called in the addBook method to add the newly created book to the database
    the new book is passed in as a parameter returns false if the book cannot be added
    */

    /**
     *
     * @param book
     * @return
     */
    private boolean insertBookToDatabase(String book) {
        String url = "jdbc:sqlite:/Users/chrisfaris/collection.db";

        try {
            Connection connection = DriverManager.getConnection(url);

            String sql = "INSERT INTO books (barcode, title, author, genre, status, due_date) VALUES (?, ?, ?, ?, ?, ?)";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            // Parse the book details and set them in the prepared statement
            String[] bookDetails = book.split(",");
            preparedStatement.setInt(1, Integer.parseInt(bookDetails[0])); // Barcode
            preparedStatement.setString(2, bookDetails[1]); // Title
            preparedStatement.setString(3, bookDetails[2]); // Author
            preparedStatement.setString(4, bookDetails[3]); // Genre
            preparedStatement.setString(5, bookDetails[4]); // Status
            preparedStatement.setString(6, bookDetails[5]); // Due Date

            // Execute the INSERT statement
            int rowsAffected = preparedStatement.executeUpdate();
            preparedStatement.close();
            connection.close();

            // Check if the insertion was successful
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the SQL exception and provide a meaningful error message
            JOptionPane.showMessageDialog(null, "Failed to add the book to the collection. Please check your input.");
            return false;
        }//end try catch
    }//end insertBookToDatabase


    /*connects to the database and allows the user to enter a barcode of a book they wish to check back in
    where the due date is then updated to null and the status is updated to checked in
    has no parameters and no return
    */

    /**
     *
     */
    public void checkInBook() {
        String url = "jdbc:sqlite:/Users/chrisfaris/collection.db";
        int barcode = 0;

        while (true) {
            String barcodeInput = JOptionPane.showInputDialog("Please enter a barcode:");
            if (barcodeInput == null) {
                // User clicked Cancel or closed the input dialog
                return;
            }//end if

            barcode = Integer.parseInt(barcodeInput);

            // Check if the barcode exists in the database
            if (!doesBarcodeExist(barcode, url)) {
                JOptionPane.showMessageDialog(null, "Barcode does not exist in the database. Please enter a valid barcode.");
            } else {
                break; // Barcode exists, exit the loop
            }//end if else
        }//end while

        try {
            Connection connection = DriverManager.getConnection(url);
            String sql = "UPDATE books SET status = 'checked in', due_date = NULL WHERE barcode = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            // Set the barcode value as a parameter
            preparedStatement.setInt(1, barcode);

            preparedStatement.executeUpdate();
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }//end try catch
        JOptionPane.showMessageDialog(null, "Book successfully checked in.");

    }//end checkInBook


    /*connects to the database and allows the user to enter a barcode of a book they wish to check out
    where the due date is then updated to 4 weeks from the current date and the status is updated to checked out
    has no parameters and no return
     */

    /**
     *
     */
    public void checkOutBook() {
        String url = "jdbc:sqlite:/Users/chrisfaris/collection.db";
        int barcode = 0;

        while (true) {
            String barcodeInput = JOptionPane.showInputDialog("Please enter a barcode:");
            if (barcodeInput == null) {
                // User clicked Cancel or closed the input dialog
                return;
            }//end if

            barcode = Integer.parseInt(barcodeInput);

            // Check if the barcode exists in the database
            if (!doesBarcodeExist(barcode, url)) {
                JOptionPane.showMessageDialog(null, "Barcode does not exist in the database. Please enter a valid barcode.");
            } else {
                break; // Barcode exists, exit the loop
            }//end if else
        }//end while

        try {
            Connection connection = DriverManager.getConnection(url);
            String sql = "UPDATE books SET status = 'checked out', due_date = date('now', '+28 days') WHERE barcode = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            // Set the barcode value as a parameter
            preparedStatement.setInt(1, barcode);

            preparedStatement.executeUpdate();
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }//end try catch
        JOptionPane.showMessageDialog(null, "Book successfully checked out.");

    }//end checkOutBook

    //creates a new instance of the Main class using SwingUtilities to ensure the GUI functions properly

    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Main();
            }
        });
    }//end main
}//end class