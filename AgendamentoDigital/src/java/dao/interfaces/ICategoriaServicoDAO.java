/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao.interfaces;

import java.util.ArrayList;
import modelos.CategoriaServico;

/**
 *
 * @author profe
 */
public interface ICategoriaServicoDAO {

    public String cadastraCategoria(CategoriaServico categoriaServico);
    
    public CategoriaServico buscarNome(CategoriaServico categoriaServico);

    public String cadastraSubCategoria(CategoriaServico categoriaServico);
    
    public ArrayList<CategoriaServico> listar();
    
    public void buscar(CategoriaServico categoriaServico);
    
    public CategoriaServico buscaCompleta(CategoriaServico categoriaServico);
   
    public String alterar(CategoriaServico categoriaServico);

    public boolean excluir(CategoriaServico categoriaServico);
    

}
