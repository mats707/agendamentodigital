/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package command.relatorios;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Rafael Pereira
 */
public interface ICommand {

    public String executar(HttpServletRequest request, HttpServletResponse response) throws Exception;
       
}
