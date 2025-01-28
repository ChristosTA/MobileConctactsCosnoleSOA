package gr.aueb.cf.mobilecontacts.validation;

import gr.aueb.cf.mobilecontacts.dto.MobileContactInsertDTO;

public class ValidationUtil {


    /**
     * no instances of this class should be available
     */
    private ValidationUtil(){

    }

    public static String validateDTO(MobileContactInsertDTO insertDTO) {
        String errorRespone = "";

        if (insertDTO.getPhoneNumber().length() <= 5)
            errorRespone += "Ο τηλ. αριθμός πρέπει να έχει περισσότερα από πέντε σύμβολα\n";
        if (insertDTO.getFirstname().length() < 2)
            errorRespone += "Το όνομα πρέπει να περιέχει δύο ή περισσότερους χαρακτήρες\n";
        if (insertDTO.getLastname().length() < 2)
            errorRespone += "Το επώνυμο πρέπει να περιέχει δύο ή περισσότερους χαρακτήρες\n";

        return errorRespone;
    }

}
