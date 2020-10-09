/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao.interfaces;

import java.util.ArrayList;
import modelos.Usuario;

/**
 *
 * @author profe
 */
public interface IUsuarioDAO {

    public String cadastraNovoUsuario(Usuario usuario);

    public Usuario autenticaUsuario(Usuario usuario);

    public ArrayList<Usuario> listar();

    public void buscar(Usuario usuario);

    public Usuario buscaCompleta(Usuario usuario);

    public String alterarUsuario(Usuario usuario);

    public String alterarCelular(Usuario usuario);
    
    public String alterarFotoPerfil(Usuario usuario);

    public String deletar(Usuario usuario);

}
