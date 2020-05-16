/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Rafael Pereira
 */

import dao.UsuarioDAO;
import modelos.PerfilDeAcesso;
import modelos.Usuario;
import org.junit.Test;
import static org.junit.Assert.*;


public class deveAutenticarUsuario {
    
    @Test
    public void VamosTestar(){
        String email = "admin@admin.com";
        String senha = "YWRtaW4="; //admin criptrogafado
        String cel = "11912341234";

        Usuario userEntrada = new Usuario();
        
        userEntrada.setEmail(email);
        userEntrada.setSenha(senha);
        UsuarioDAO dao = new UsuarioDAO();
        
        userEntrada=dao.autenticaUsuario(userEntrada);
        Usuario userEsperado = new Usuario();
        userEsperado.setEmail("admin@admin.com");
        userEsperado.setSenha("YWRtaW4=");
        userEsperado.setPerfil(PerfilDeAcesso.FUNCIONARIOADMIN);
        userEsperado.setCelular(Long.parseLong(cel)); //11912341234
        
        if (userEsperado == userEntrada)
        {
            System.out.println("true");
        }
        else
        {
            System.out.println("false");
        }
        assertEquals(userEsperado, userEntrada);
        //assertEquals("teste", userEsperado, userEntrada);
}
  
}
