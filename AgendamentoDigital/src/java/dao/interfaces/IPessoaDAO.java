/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao.interfaces;

import java.util.ArrayList;
import modelos.Pessoa;
import modelos.Cliente;

/**
 *
 * @author profe
 */
public interface IPessoaDAO {

    public ArrayList<Pessoa> listar();

    public void buscar_usuario(Cliente cliente);

    public void buscar(Pessoa pessoa);

    public String alterar(Pessoa Pessoa);

    public boolean excluir(Pessoa pessoa);

    public String cadastrar(Pessoa Pessoa);

}
