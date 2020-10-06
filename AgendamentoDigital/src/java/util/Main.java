/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

/**
 *
 * @author matht
 */
public class Main {

    public static void main(String[] args) {

        String senha = "admin";

        String senha_hashed = geraHash.hashPassword(senha);
        System.out.println("[" + senha + "] = [" + senha_hashed + "]");

        senha_hashed = "$2a$12$Tkpv4hAeB2gLeI.JVn0Kx.eaN/DRw9P4L79JvNrbCSS9fQy.dgnd.";
        String senha_hashed_dao = "admi";
 
        if (geraHash.checkPassword(senha_hashed_dao, senha_hashed)) {
            System.out.println("Matched");
        } else {
            System.out.println("Deu ruim");
        }
    }
}
