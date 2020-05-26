/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package command.servico;

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

        String sqlState = "0";
        String funcaoMsgDeleted = "";
        String funcaoStatusDeleted = "";

        if (id != null && deletedNome != null && deletedDescricao != null) {
            Servico servico = new Servico();
            servico.setIdServico(id);
            servico.setNome(deletedNome);
            servico.setDescricao(deletedDescricao);

            ServicoDAO servicoDAO = new ServicoDAO();
            Servico servicoSolicitado = servicoDAO.buscaCompleta(servico);

            if (servicoSolicitado == servico) {
                //Chamada da DAO para Deletar
                sqlState = servicoDAO.deletar(servicoSolicitado);
                //Verifica o retorno da DAO (banco de dados)
                if (sqlState == "0") {
                    funcaoMsgDeleted = "Deletado com sucesso!";
                    funcaoStatusDeleted = "success";
                } else if (sqlState.equalsIgnoreCase("ERROR: update or delete on table \"servico\" violates foreign key constraint \"fkservico\" on table \"agendamento\"")) {
                    funcaoMsgDeleted = "Você possui um agendamento com esse serviço! Delete o agendamento primeiro!";
                    funcaoStatusDeleted = "error";
                } else {
                    funcaoMsgDeleted = "Não foi possível deletar o serviço, tente novamente mais tarde!";
                    funcaoStatusDeleted = "error";
                }
            } else {
                funcaoMsgDeleted = "Serviço inválido!";
                funcaoStatusDeleted = "error";
            }
        } else {
            funcaoMsgDeleted = "Serviço inválido!";
            funcaoStatusDeleted = "error";
        }
        request.setAttribute("funcaoMsgDeleted", funcaoMsgDeleted);
        request.setAttribute("funcaoStatusDeleted", funcaoStatusDeleted);
        return funcaoMsgDeleted;

    }

}
