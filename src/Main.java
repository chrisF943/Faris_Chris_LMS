import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import java.util.Scanner;

/*
Chris Faris
Software Development 1
10/20/2023
class name: Main
this class contains the main method that runs the LMS GUI, the program loads data from a text file
into an ArrayList, and users can add, delete, checkout, and checkin books and view the collection
all changes are written to the text file
*/

public class Main {
    private JFrame frame;
    private JTextArea outputTextArea;
    private ArrayList<String> collection;
    private JPanel mainPanel;

    public Main() {
        collection = ReadFile.getBooks("BookCollection.txt");

       /* try{
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        }catch(Exception e){
            e.printStackTrace();
        } */

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
                displayCollection(collection);
            }//end actionPerformed
        });
        viewButton.setFont(buttonFont);

        JButton addButton = new JButton("Add Book");
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addBook(collection);
                ReadFile.writeFile(collection, "BookCollection.txt");
            }//end actionPerformed
        });
        addButton.setFont(buttonFont);

        JButton deleteButton = new JButton("Delete Book");
        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                collection = DeleteBook.selectBook(collection);
                ReadFile.writeFile(collection, "BookCollection.txt");
            }//end actionPerformed
        });
        deleteButton.setFont(buttonFont);

        JButton checkoutButton = new JButton("Checkout Book");
        checkoutButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                collection = DeleteBook.selectBook(collection);
                ReadFile.writeFile(collection, "BookCollection.txt");
            }//end actionPerformed
        });
        checkoutButton.setFont(buttonFont);

        JButton checkinButton = new JButton("Checkin Book");
        checkinButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addBook(collection);
                ReadFile.writeFile(collection, "BookCollection.txt");
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


    /*altered for use with GUI, prints out current contents of the collection
    ArrayList type String collection is parameter with no return
    */
    public void displayCollection(ArrayList<String> collection) {
        outputTextArea.setText("Here is the current book collection:\n");
        outputTextArea.append("----------------------------------------------\n");
        for (String book : collection) {
            outputTextArea.append(book + "\n");
            outputTextArea.append("----------------------------------------------\n");
        }//end for
    }//end displayCollection

    /*also altered for use with GUI, asks user for book details, creates it and adds it to the collection
    if details are valid, also has ArrayList type String collection as a parameter with no return
    */
    public void addBook(ArrayList<String> collection) {
        int id;
        String title;
        String author;
        id = Integer.valueOf(JOptionPane.showInputDialog("Please enter an ID number:"));
        if (doesIdExist(collection, id)) {
            JOptionPane.showMessageDialog(null, "Cannot use an ID number that already exists.");
            id = Integer.valueOf(JOptionPane.showInputDialog("Please enter an ID number:"));
        }//end if
        title = JOptionPane.showInputDialog("Please enter the title of the book:");
        author = JOptionPane.showInputDialog("Please enter the author of the book:");
        String book = id + "," + title + "," + author;
        collection.add(book);
        ReadFile.writeFile(collection, "BookCollection.txt");
        JOptionPane.showMessageDialog(null, "Book has successfully been checked in to the collection.");
    }//end addBook


    /*iterates through the collection to see if the id entered by user already exists, returns true if it does
    else returns false, also has ArrayList type String collection as a parameter and the id as an int
    */
    public boolean doesIdExist(ArrayList<String> collection, int id) {
        for (String book : collection) {
            String[] bookDetails = book.split(",");
            int existingId = Integer.parseInt(bookDetails[0]);
            if (existingId == id) {
                return true;
            }//end if
        }//end for
        return false;
    }//end doesIdExist

    //creates a new instance of the Main class using SwingUtilities to ensure the GUI functions properly
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Main();
            }
        });
    }//end main
}//end class