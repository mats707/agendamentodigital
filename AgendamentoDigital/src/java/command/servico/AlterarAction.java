/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package command.servico;

import dao.ServicoDAO;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import jdk.nashorn.internal.objects.NativeString;
import modelos.Servico;

/**
 *
 * @author alunocmc
 */
public class AlterarAction implements ICommand {

    @Override
    public String executar(HttpServletRequest request, HttpServletResponse response) {

        request.setAttribute("pagina", "ListarServico");

        Integer id = Integer.parseInt(request.getParameter("idServico"));
        String editedNome = request.getParameter("editedNome");
        String editedDescricao = request.getParameter("editedDescricao");
        String editedValor = request.getParameter("editedValor");

        String funcaoMsg;
        String funcaoStatus;

        if (id != null && editedNome != null && editedDescricao != null
                && editedValor != null) {
            Servico servico = new Servico();
            servico.setIdServico(id);
            servico.setNome(editedNome);
            servico.setDescricao(editedDescricao);
            servico.setValor(Double.parseDouble(editedValor));

            ServicoDAO servicoDAO = new ServicoDAO();

            String sqlState = servicoDAO.alterarServico(servico);

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
