/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import static org.apache.commons.io.Charsets.ISO_8859_1;
import static org.apache.commons.io.Charsets.UTF_8;

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

    public static boolean isValidName(String nome) {
        return nome != null && nome.matches("^[a-zA-Z]+(([',. -][a-zA-Z ])?[a-zA-Z]*)*$");
    }

    public static Date getValidDate(String strDate, String formatDate) {
        try {
            SimpleDateFormat df = new SimpleDateFormat(formatDate);
            df.setLenient(false);
            df.parse(strDate);
            return df.parse(strDate);
        } catch (ParseException ex) {
            Logger.getLogger(Util.class.getName()).log(Level.SEVERE, null, ex);
            return (Date) null;
        }
    }

    public static String stringToUTF8(String text) {
        if (text != null) {
            text = new String(text.getBytes(ISO_8859_1), UTF_8);
        }
        return text;
    }

}
