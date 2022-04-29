package NotInUse;
/*
import bookds.BookShop;

import java.io.*;

public class RW {
    public static void writeToFile(BookShop shopMngSys){

        ObjectOutputStream out = null;
        try {
            out = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream("out.txt")));
            out.writeObject(shopMngSys);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static BookShop readFromFile() {

        BookShop shopMngSys = null;
        try {
            ObjectInputStream in = new ObjectInputStream(new BufferedInputStream(new FileInputStream("in.txt")));
            shopMngSys = (BookShop) in.readObject();
        } catch (FileNotFoundException e) {
            System.out.println("No file found.\n");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Error while reading from file.\n");
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.out.println("Structure does not match or no such class.\n");
            e.printStackTrace();
        }

        return shopMngSys;
    }
}*/
