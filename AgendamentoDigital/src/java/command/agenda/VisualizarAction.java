/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package command.agenda;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Rafael Pereira
 */
public class VisualizarAction implements ICommand {

    public String executar(HttpServletRequest request, HttpServletResponse response) throws Exception {

        request.setAttribute("pagina", "pages/admin/VizualizarAgenda.jsp");
        return null;

    }
}
