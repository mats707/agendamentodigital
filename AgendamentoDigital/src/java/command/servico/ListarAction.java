/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package command.servico;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dao.CategoriaServicoDAO;
import dao.FuncionarioDAO;
import dao.ServicoDAO;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import modelos.Funcionario;
import modelos.Servico;

/**
 *
 * @author alunocmc
 */
public class ListarAction implements ICommand {

    @Override
    public String executar(HttpServletRequest request, HttpServletResponse response) throws Exception {

        Gson objgson = new GsonBuilder().setPrettyPrinting().create();

        request.setAttribute("pagina", "pages/admin/servicos/listar.jsp");

        ServicoDAO objServicoDao = new ServicoDAO();

        ArrayList<Servico> arr = new ArrayList<Servico>();
        arr = objServicoDao.listar();

        FuncionarioDAO objFuncionarioDao = new FuncionarioDAO();
        CategoriaServicoDAO objCategoriaServicoDAO = new CategoriaServicoDAO();

        for (Servico objServico : arr) {
            if (objServico.getIdServico() != null) {
                for (Funcionario objFuncionario : objServico.getFuncionarios()) {
                    objFuncionarioDao.buscar(objFuncionario);
                }
                objCategoriaServicoDAO.buscarId(objServico.getCategoria());
            }
        }
        
        String funcaoMsg = "Carregando Serviços";
        String funcaoStatus = "info";

        request.setAttribute("funcaoMsg", funcaoMsg);
        request.setAttribute("funcaoStatus", funcaoStatus);

        return objgson.toJson(arr);

    }

}
