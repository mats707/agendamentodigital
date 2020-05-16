/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package command.categoriaservico;

import dao.CategoriaServicoDAO;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import jdk.nashorn.internal.objects.NativeString;
import modelos.CategoriaServico;

/**
 *
 * @author alunocmc
 */

public class AlterarAction implements ICommand {

    @Override
    public String executar(HttpServletRequest request, HttpServletResponse response) {

        request.setAttribute("pagina", "ListarCategoriaServico");

        Integer id = Integer.parseInt(request.getParameter("idCategoriaServico"));
        String editedNome = request.getParameter("editedNome");
        String editedDescricao = request.getParameter("editedDescricao");
        String editedValor = request.getParameter("editedValor");

        String funcaoMsg;
        String funcaoStatus;

        if (id != null && editedNome != null && editedDescricao != null
                && editedValor != null) {
            CategoriaServico categoriaservico = new CategoriaServico();
            categoriaservico.setIdCategoriaServico(id);
            categoriaservico.setNome(editedNome);
            categoriaservico.setDescricao(editedDescricao);

            CategoriaServicoDAO categoriaServicoDAO = new CategoriaServicoDAO();

            String sqlState = categoriaServicoDAO.alterar(categoriaservico);

            if (sqlState == "0") {
                funcaoMsg = "Alterado com sucesso!";
                funcaoStatus = "success";
            } else {
                funcaoMsg = "Não foi possível alterar o serviço, tente novamente!";
                funcaoStatus = "error";
            }
        } else {
            funcaoMsg = "Serviço inválido!";
            funcaoStatus = "error";
        }
        request.setAttribute("funcaoMsg", funcaoMsg);
        request.setAttribute("funcaoStatus", funcaoStatus);
        return funcaoMsg;

    }

}
