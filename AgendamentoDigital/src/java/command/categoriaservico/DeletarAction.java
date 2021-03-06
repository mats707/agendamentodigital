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
import static org.apache.commons.io.Charsets.ISO_8859_1;
import static org.apache.commons.io.Charsets.UTF_8;
import util.Util;

/**
 *
 * @author alunocmc
 */
public class DeletarAction implements ICommand {

    @Override
    public String executar(HttpServletRequest request, HttpServletResponse response) {

        request.setAttribute("pagina", "pages/admin/categoria/home.jsp");

//        String editedCategoria = request.getParameter("deletarCategoria");
//        String editedCategoriaPai = request.getParameter("deletarCategoriaPai");

        String funcaoMsg = "Por enquanto essa função está sendo implantada!\\nFique ligado que em breve traremos novidades!";
        String funcaoStatus = "warning";

//        if (editedCategoria != null && editedCategoriaPai != null) {
//            CategoriaServico categoriaservico = new CategoriaServico();
//            categoriaservico.setIdCategoriaServico(Integer.parseInt(editedCategoria));
//            categoriaservico.setCategoriaPai(new CategoriaServico(Integer.parseInt(editedCategoriaPai)));
//
//            CategoriaServicoDAO categoriaServicoDAO = new CategoriaServicoDAO();
//
//            String sqlState = categoriaServicoDAO.deletar(categoriaservico);
//
//            if (sqlState == "0") {
//                funcaoMsg = "Deletado com sucesso!";
//                funcaoStatus = "success";
//            } else {
//                funcaoMsg = "Não foi possível deletar o serviço, tente novamente!";
//                funcaoStatus = "error";
//            }
//        } else {
//            funcaoMsg = "Serviço inválido!";
//            funcaoStatus = "error";
//        }
        request.setAttribute("funcaoMsg", funcaoMsg);
        request.setAttribute("funcaoStatus", funcaoStatus);
        return funcaoMsg;

    }

}
