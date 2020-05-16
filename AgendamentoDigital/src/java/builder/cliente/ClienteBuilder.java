/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package builder.cliente;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelos.Cliente;
import modelos.PerfilDeAcesso;
import modelos.Usuario;
import util.geraHash;

/**
 *
 * @author wolley
 */
public class ClienteBuilder {

    private Usuario usuario = new Usuario();
    private Cliente cliente = new Cliente();

    private ClienteBuilder() {

    }

    public static ClienteBuilder novoClienteBuilder() {
        return new ClienteBuilder();
    }

    //##########################################################################
    //# Usuário
    //##########################################################################
    public ClienteBuilder comUsuario(String email, String senha) {
        usuario.setEmail(email);
        usuario.setSenha(geraHash.codificaBase64(senha));
        usuario.setPerfil(PerfilDeAcesso.CLIENTECOMUM);
        return this;
    }

    //##########################################################################
    //# Cliente é uma Pessoa
    //##########################################################################
    public ClienteBuilder comNome(String nome) {
        cliente.setNome(nome);
        return this;
    }

    public ClienteBuilder nascidoEm(String data) {

        try {

            try {
                SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yyyy");

                java.util.Date date = sdf1.parse(data);

                cliente.setDataNascimento(date);

            } catch (ParseException ex) {
                
                SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");

                java.util.Date date = sdf1.parse(data);

                cliente.setDataNascimento(date);
            }

        } catch (ParseException ex) {
            Logger.getLogger(builder.cliente.ClienteBuilder.class.getName()).log(Level.SEVERE, null, ex);
        }

        return this;
    }

    //##########################################################################
    //# Construir Cliente
    //##########################################################################
    public Cliente constroi() {
        //Dados Cliente
        cliente.setUsuario(usuario);

        return cliente;
    }
}
