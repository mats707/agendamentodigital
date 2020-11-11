/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package command.funcionario;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 *
 * @author alunocmc
 */
public class HomeAction implements ICommand {

    @Override
    public String executar(HttpServletRequest request, HttpServletResponse response) throws Exception {

        String funcaoMsg = "Carregando...";
        String funcaoStatus = "info";

        request.setAttribute("pagina", "/pages/funcionario/home.jsp");
        request.setAttribute("funcaoMsg", funcaoMsg);
        request.setAttribute("funcaoStatus", funcaoStatus);

        return funcaoMsg;

    }

}
