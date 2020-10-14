/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao.interfaces;

import java.util.ArrayList;
import java.sql.Time;
import java.util.Map;
import modelos.Empresa;

/**
 *
 * @author profe
 */
public interface IEmpresaDAO {

    public void buscar(Empresa objEmpresa);
    
    public boolean alterar(Empresa objEmpresa);

}
