/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import org.apache.commons.codec.binary.Base64;

/**
 *
 * @author mathm
 */
public class geraHash {

    /**
     * Codifica string
     */
    public static String hashPassword(String pass) {
        String passCrypt = "";
        if (pass != "" && pass != null) {
            passCrypt = BCrypt.hashpw(pass, BCrypt.gensalt(12));
        }
        return passCrypt;
    }

    public static boolean checkPassword(String chkPass, String hashPass) {
        if (hashPass != "") {
            return BCrypt.checkpw(chkPass, hashPass);
        } else {
            return false;
        }
    }

}
