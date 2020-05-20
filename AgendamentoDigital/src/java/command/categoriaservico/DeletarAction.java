/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package command.categoriaservico;

import dao.ServicoDAO;
import java.math.BigDecimal;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import jdk.nashorn.internal.objects.NativeString;
import modelos.PerfilDeAcesso;
import modelos.Servico;
import util.geraHash;

/**
 *
 * @author alunocmc
 */
public class DeletarAction implements ICommand {

    @Override
    public String executar(HttpServletRequest request, HttpServletResponse response) {

        request.setAttribute("pagina", "ListarServico");

        Integer id = Integer.parseInt(request.getParameter("idServicoDeleted"));
        String deletedNome = request.getParameter("deletedNome");
        String deletedDescricao = request.getParameter("deletedDescricao");
        String deletedValor = request.getParameter("deletedValor");

        String funcaoMsg = "";
        String funcaoStatus = "";

        if (id != null && deletedNome != null && deletedDescricao != null && deletedValor != null) {
            Servico servico = new Servico();
            servico.setIdServico(id);
            servico.setNome(deletedNome);
            servico.setDescricao(deletedDescricao);
            servico.setValor(BigDecimal.valueOf(Double.parseDouble(deletedValor)));

            ServicoDAO servicoDAO = new ServicoDAO();
            Servico servicoSolicitado = servicoDAO.buscaCompleta(servico);

            if (servicoSolicitado == servico) {
                if (servicoDAO.excluir(servicoSolicitado)) {
                    funcaoMsg = "Deletado com sucesso!";
                    funcaoStatus = "success";
                } else {
                    funcaoMsg = "Não foi possível deletar o serviço, tente novamente mais tarde!";
                    funcaoStatus = "error";
                }
            } else {
                funcaoMsg = "Serviço inválido!";
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
