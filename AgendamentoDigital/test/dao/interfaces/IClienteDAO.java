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

    public boolean cadastrarCliente(Cliente cliente);

    public boolean cadastrarPessoa(Cliente cliente);
    
    public Cliente buscarId(Cliente cliente);

    public ArrayList<Cliente> listar();
    
    public ArrayList<Cliente> listarNome(Cliente cliente);
    
}
