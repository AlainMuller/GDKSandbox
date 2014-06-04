package fr.alainmuller.gdksandbox.app.tools;

/**
 * Created by Alain MULLER on 04/06/2014.
 */
public class StringUtils {

    public static String getFormattedNumber(String number) {
        String displayedPhone = "";
        char phone[] = number.toCharArray();

        for (int i = 0; i < number.length(); i++) {
            displayedPhone += phone[i];
            // Display phone number in "XX XX XX XX XX" format
            if (i % 2 != 0)
                displayedPhone += " ";
        }
        return displayedPhone;
    }

}
