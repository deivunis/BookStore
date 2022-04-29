/*import bookds.BookShop;
import utils.RW;
import utils.UserManagement;

import java.util.Scanner;

public class StartProgram {
    public static void main(String[] args) {

        BookShop shopMngSys = RW.readFromFile(); //read from file
        if(shopMngSys == null) {
            //shopMngSys = new BookShop("");
        }
        Scanner scanner = new Scanner(System.in);
        String cmd = "";

        while(!cmd.equals("q")) {
            System.out.println("Please choose an option:\n" +
                    "u - manage users\n" +
                    "b - manage books\n" +
                    "c - manage comments\n" +
                    "o - manage orders\n" +
                    "s - save to file\n" +
                    "q - finish work\n");
            cmd = scanner.nextLine();

            switch (cmd){
                case "u":
                    UserManagement.userMenu(scanner,shopMngSys);
                    break;
                case "b":
                    //ka veikiu su knygomis;
                    break;
                case "c":
                    //ka veikiu su komentarais;
                    break;
                case "o":
                    //ka veikiu su uzsakymais;
                    break;
                case "s":
                    //write to file;
                    RW.writeToFile(shopMngSys);
                    break;
                case "q":
                    System.out.println("See you later alligator.\n");
                    break;
                default:
                    System.out.println("There is no such option you sneaky bastard.\n");
            }
        }


       //Person person = new Person();
        //person.setLogin("admin");
        // System.out.println("Person login is: " + person.getLogin());
        // Company company = new Company("");
    }
}*/
