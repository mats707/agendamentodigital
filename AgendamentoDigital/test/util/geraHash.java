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
     * Codifica string na base 64 (Encoder)
     */
    public static String codificaBase64(String msg) {
        return new String( new Base64().encode(msg.getBytes()));
    }
}
