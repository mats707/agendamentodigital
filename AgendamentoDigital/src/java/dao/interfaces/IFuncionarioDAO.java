/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao.interfaces;

import java.util.ArrayList;
import modelos.Funcionario;

/**
 *
 * @author profe
 */
public interface IFuncionarioDAO {

    public ArrayList<Funcionario> listar();

    public ArrayList<Funcionario> listarCompleto();

    public void listarCompletoId(Funcionario funcionario);

    public void buscar(Funcionario funcionario);

    public boolean alterar(Funcionario funcionario);

    public boolean excluir(Funcionario funcionario);

    public boolean cadastrar(Funcionario funcionario);

}
