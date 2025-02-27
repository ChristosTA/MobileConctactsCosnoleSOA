package gr.aueb.cf.mobilecontacts;

import gr.aueb.cf.mobilecontacts.controller.MobileContactController;
import gr.aueb.cf.mobilecontacts.dto.MobileContactInsertDTO;

import java.util.Scanner;

public class Main {
    private static final MobileContactController controller = new MobileContactController();
    private static final Scanner in = new Scanner(System.in);

    public static void main(String[] args) {
        String choice;

        while (true){
            printMenu();
            choice = getToken();

            if (choice.equals("q")|| (choice.equals("Q"))){
                break;
            }

            handleChoice(choice);

        }

        System.out.println("Thank you for using the Mobile COntact App");
    }

    public static void handleChoice(String choice) {
        String firstname;
        String lastname;
        String phoneNumber;
        String response;

        switch (choice) {
            case "1" :
                System.out.println("Παρακαλώ εισάγετε Όνομα , Επώνυμο, Αρ. Τηλεφώνου");
                firstname = getToken();
                lastname = getToken();
                phoneNumber = getToken();
                MobileContactInsertDTO insertDTO = new MobileContactInsertDTO(firstname,lastname,phoneNumber);
                response = controller.insertContact(insertDTO);

                if (response.startsWith("OK")) {
                    System.out.println("Επιτυχη εισαγωγη");
                    System.out.println(response.substring(3));
                } else {
                    System.out.println("Ανεπιτύχης εισαγωγή");
                    System.out.println(response.substring(7));
                }
                break;
            case "2" :
                //
                break;
            case "3" :
                //
                break;
            case "4" :
                //
                break;
            case "5" :
                //
                break;
            default:
                //
                break;
        }
    }


    public static void printMenu() {
        System.out.println("Επιλεξτέ ένα από τα παρακάτω:");
        System.out.println("1. Εισαγωγή επαφής");
        System.out.println("2. Ενημερωσή επαφής");
        System.out.println("3. Διαγραφή επαφής");
        System.out.println("4. Αναζήτηση επαφής");
        System.out.println("5. Προβολή επαφών");
        System.out.println("Q/q Έξοδος");
    }

    public static String getToken() {
        return in.nextLine().trim();
    }
}
