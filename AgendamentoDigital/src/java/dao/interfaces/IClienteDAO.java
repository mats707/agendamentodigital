/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao.interfaces;

import java.util.ArrayList;
import modelos.Cliente;

/**
 *
 * @author profe
 */
public interface IClienteDAO {

    public ArrayList<Cliente> listar();

    public ArrayList<Cliente> listarCompleto();

    public void listarCompletoId(Cliente cliente);

    public void buscar(Cliente cliente);

    public boolean excluir(Cliente cliente);

    public String cadastrar(Cliente cliente);

}
