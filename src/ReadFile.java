import java.io.*;
import java.util.ArrayList;

/*
Chris Faris
Software Development 1
9/3/2023
class name: ReadFile
contains the methods to read from a text file and write changes to it, they are called in the main class
when initially creating the ArrayList and when changes are made to the collection
 */

public class ReadFile {

    /*
     method name: getBooks
     reads from the given text file and creates an ArrayList
     passes in the fileName as a String parameter and returns an ArrayList collection
     */
    public static ArrayList<String> getBooks(String fileName) {
        ArrayList<String> collection = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                collection.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }//end try-catch

        return collection;
    }//end getBooks

    /*
     method name: writeFile
     writes any changes that are made to the ArrayList to the text file, ensuring they are saved
     passes in the ArrayList collection and the fileName as a String parameter and has no return
     */
    public static void writeFile(ArrayList<String> collection, String fileName) {
        File tempFile = new File(fileName);
        try {
            PrintWriter output = new PrintWriter(tempFile);
            for (String e : collection) {
                output.println(e.toString());
            }//end for
            output.close();
        //catches the error if the file is not found and prints it
        } catch (FileNotFoundException e) {
            System.out.println("You have an error: " + e);
        }//end try-catch
    }//end writeFile
}//end class