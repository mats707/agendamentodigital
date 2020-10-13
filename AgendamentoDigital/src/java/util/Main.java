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

        
        //cadastro
        String senha = "admin";

        String senha_hashed = geraHash.hashPassword(senha);
        System.out.println("[" + senha + "] = [" + senha_hashed + "]");

        senha_hashed = "$2a$12$nzsvpa//0fmHrQQPFk8bR.wqRds.L6og3jCwXfYRRv3x41Cv2YFZS";
        
        
        //login
        String senha_hashed_dao = "admin";
 
        if (geraHash.checkPassword(senha_hashed_dao, senha_hashed)) {
            System.out.println("Matched");
        } else {
            System.out.println("Deu ruim");
        }
    }
}
