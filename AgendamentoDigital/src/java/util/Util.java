/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

/**
 *
 * @author matht
 */
public class Util {

    public static boolean isInteger(String str) {
        str = str.replaceAll("([+()\\-a-z ]+)", "");
        return str != null && str.matches("[0-9]*");
    }

    public static boolean isValidEmailAddress(String email) {
        boolean result = true;
        try {
            InternetAddress emailAddr = new InternetAddress(email);
            emailAddr.validate();
        } catch (AddressException ex) {
            result = false;
            System.out.println("ERROR: isValidEmailAddress - " + ex);
        }
        return result;
    }

    public static boolean isValidPassword(String password, String chkpassword) {
        if (password.equals(chkpassword)) {
            return true;
        } else {
            return false;
        }
    }

}
