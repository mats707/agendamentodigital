/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package command.categoriaservico;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modelos.CategoriaServico;

/**
 *
 * @author alunocmc
 */
public interface ICommand {

    public String executar(HttpServletRequest request, HttpServletResponse response) throws Exception;

}
