/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package command.empresa;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dao.EmpresaDAO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modelos.Empresa;

/**
 *
 * @author Rafael Pereira
 */
public class BuscarEmpresaAction implements ICommand {

    @Override
    public String executar(HttpServletRequest request, HttpServletResponse response) throws Exception {
        request.setAttribute("pagina", "pages/admin/empresa/menuEmpresa.jsp");

        Empresa objEmpresa = new Empresa();
        EmpresaDAO empresaDAO = new EmpresaDAO();

        empresaDAO.buscar(objEmpresa);

        Gson objgson = new GsonBuilder().setPrettyPrinting().create();

        String funcaoMsg = null;
        String funcaoStatus = null;

        funcaoMsg = (String) request.getAttribute("funcaoMsg");
        funcaoStatus = (String) request.getAttribute("funcaoStatus");

        if (funcaoMsg == null || funcaoStatus == null) {
            funcaoMsg = "Carregando...";
            funcaoStatus = "info";
        }

        request.setAttribute("obj", objEmpresa);
        request.setAttribute("funcaoMsg", funcaoMsg);
        request.setAttribute("funcaoStatus", funcaoStatus);

        return objgson.toJson(objEmpresa);
    }

}
