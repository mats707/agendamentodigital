/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package builder.funcionario;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelos.Funcionario;
import modelos.PerfilDeAcesso;
import modelos.Usuario;
import util.geraHash;

public class FuncionarioBuilder {

    private Usuario usuario = new Usuario();
    private Funcionario funcionario = new Funcionario();

    private FuncionarioBuilder() {

    }

    public static FuncionarioBuilder novoFuncionarioBuilder() {
        return new FuncionarioBuilder();
    }

    //##########################################################################
    //# Usuário
    //##########################################################################
    public FuncionarioBuilder comUsuario(String email, String senha, String celular, String perfil) {
        usuario.setEmail(email);
        usuario.setSenha(geraHash.hashPassword(senha));
        usuario.setCelular(Long.parseLong(celular.replace("(", "").replace(")", "").replace("-", "").replace(" ", "")));
        if (perfil.equalsIgnoreCase("administrador")) {
            usuario.setPerfil(PerfilDeAcesso.FUNCIONARIOADMIN);
        } else if (perfil.equalsIgnoreCase("funcionariocomum")) {
            usuario.setPerfil(PerfilDeAcesso.FUNCIONARIOCOMUM);
        }
        return this;
    }

    //##########################################################################
    //# Funcionario é uma Pessoa
    //##########################################################################
    public FuncionarioBuilder comNome(String nome) {
        funcionario.setNome(nome);
        return this;
    }

    public FuncionarioBuilder nascidoEm(String data) {

        try {

            try {
                SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yyyy");

                java.util.Date date = sdf1.parse(data);

                funcionario.setDataNascimento(date);

            } catch (ParseException ex) {

                SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");

                java.util.Date date = sdf1.parse(data);

                funcionario.setDataNascimento(date);
            }

        } catch (ParseException ex) {
            Logger.getLogger(FuncionarioBuilder.class.getName()).log(Level.SEVERE, null, ex);
        }

        return this;
    }

    //##########################################################################
    //# Construir Funcionario
    //##########################################################################
    public Funcionario constroi() {
        //Dados Funcionario
        funcionario.setUsuario(usuario);

        return funcionario;
    }
}
