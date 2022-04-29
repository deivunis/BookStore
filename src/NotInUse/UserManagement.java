package NotInUse;
/*
import bookds.LegalPerson;
import bookds.Individual;
import bookds.BookShop;
import bookds.User;

import java.time.LocalDate;
import java.util.Scanner;

public class UserManagement {
    public static void userMenu(Scanner scanner, BookShop shopMngSys) {

        int cmd = 0;

        while(cmd != 6) {
            System.out.println("Please choose an option:\n" +
                    "1 - create user\n" +
                    "2 - update user\n" +
                    "3 - delete user\n" +
                    "4 - view user data\n" +
                    "5 - print all users\n" +
                    "6 - return to main menu\n");
            cmd = scanner.nextInt();
            scanner.nextLine();

            switch (cmd){
                case 1:
                    System.out.println("User type? P/C: \n");
                    String userType = scanner.nextLine();
                    if(userType.equals("P")) {
                        System.out.println("Enter Person data: login;psw;name;surname;phoneNumber;YYYY-MM-DD;");
                        //String lineInfo = scanner.nextLine();
                        String[] info = scanner.nextLine().split(";");
                        Individual individual = new Individual(info[0], info[1], info[2], info[3], info[4], LocalDate.parse(info[5]));
                        shopMngSys.getAllUsers().add(individual);
                    } else if (userType.equals("C")){
                        System.out.println("Enter Company data: login;psw;title;ceo;registrationNumber;vatNumber;");
                        String[] info = scanner.nextLine().split(";");
                        LegalPerson legalPerson = new LegalPerson(info[0], info[1], info[2], info[3], info[4], info[5]);
                        shopMngSys.getAllUsers().add(legalPerson);
                    } else {
                        System.out.println("No such user type. \n");
                    }
                    break;
                case 2:
                    System.out.println("Enter login:");

                    User user = shopMngSys.getUserByLogin(scanner.nextLine());
                    if(user != null) {
                        System.out.println("Enter a new password: ");
                        user.setPassword(scanner.nextLine());
                    }

                    //old version
                    //for(User u: shopMngSys.getAllUsers()){
                        //if(u.getLogin().equals(login)) {

                        //}
                    //}
                    break;
                case 3:
                    System.out.println("Enter login:");
                    shopMngSys.deleteUserByLogin(scanner.nextLine());
                    break;
                case 4:
                    System.out.println("Enter login:");
                    shopMngSys.printUserData(scanner.nextLine());
                    break;
                case 5:
                    shopMngSys.getAllUsers().forEach(u -> System.out.println(u));
                    break;
                case 6:
                    System.out.println("Returning...\n");
                    break;
                default:
                    System.out.println("There is no such option you sneaky bastard.\n");
            }
        }
    }
}
*/