import org.junit.Before;
import org.junit.Test;
import org.xml.sax.InputSource;

import static org.junit.Assert.*;

import java.util.ArrayList;

public class MainTest {

    ArrayList<String> collection = new ArrayList<>();
    ArrayList<String> updatedCollection = new ArrayList<>();


    @Test
    public void testAddBook() {

        //Main.addBook(collection);
        System.out.println(collection);
        // Assert: Check if the test case produces the expected result
        // Assert that the collection has increased in size, indicating that a book was added
        assertEquals(1, collection.size());

        // Get the added book from the collection (assuming it's the first one)
        String addedBook = collection.get(0);

        // Split the added book into parts (ID, Title, Author)
        String[] bookDetails = addedBook.split(",");

        // Check that the added book has the correct ID, Title, and Author
        assertEquals("6", bookDetails[0]);
        assertEquals("test1", bookDetails[1]);
        assertEquals("john1", bookDetails[2]);
    }

    @Test
    public void testSelectBook() {
        int id = 7;
        String title = "test2";
        String author = "john2";
        String book = id + "," + title + "," + author;
        collection.add(book);
        int id2 = 8;
        String title2 = "test3";
        String author2 = "john3";
        String book2 = id2 + "," + title2 + "," + author2;
        collection.add(book2);
        System.out.println("The books in the collection are " + collection);
        System.out.println("The remaining book after deletion is: " + DeleteBook.selectBook(collection));

        // Assert: Check if the test case produces the expected result
        assertEquals(1, DeleteBook.selectBook(collection).size());

    }
}
