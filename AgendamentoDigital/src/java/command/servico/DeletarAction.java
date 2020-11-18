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
import util.Util;
import util.geraHash;

/**
 *
 * @author alunocmc
 */
public class DeletarAction implements ICommand {

    @Override
    public String executar(HttpServletRequest request, HttpServletResponse response) {

        request.setAttribute("pagina", "/Administrador/Servico/Listar");

        Integer id = Integer.parseInt(request.getParameter("idServicoDeleted"));
        String deletedNome = request.getParameter("deletedNome");
        String deletedDescricao = request.getParameter("deletedDescricao");

        String sqlState = "0";
        String funcaoMsgOperation = "";
        String funcaoStatusOperation = "";

        if (id != null && deletedNome != null && deletedDescricao != null) {
            Servico servico = new Servico();
            servico.setIdServico(id);
            servico.setNome(Util.stringToUTF8(deletedNome));
            servico.setDescricao(Util.stringToUTF8(deletedDescricao));

            ServicoDAO servicoDAO = new ServicoDAO();
            Servico servicoSolicitado = servicoDAO.buscaCompleta(servico);

            if (servicoSolicitado == servico) {
                //Chamada da DAO para Deletar
                sqlState = servicoDAO.deletar(servicoSolicitado);
                //Verifica o retorno da DAO (banco de dados)
                if (sqlState == "0") {
                    funcaoMsgOperation = "Deletado com sucesso!";
                    funcaoStatusOperation = "success";
                } else if (sqlState.equalsIgnoreCase("ERROR: update or delete on table \"servico\" violates foreign key constraint \"fkservico\" on table \"agendamento\"")) {
                    funcaoMsgOperation = "Você possui um agendamento com esse serviço!\\n Não é possível deletar!";
                    funcaoStatusOperation = "error";
                } else {
                    funcaoMsgOperation = "Não foi possível deletar o serviço, tente novamente mais tarde!";
                    funcaoStatusOperation = "error";
                }
            } else {
                funcaoMsgOperation = "Serviço inválido!";
                funcaoStatusOperation = "error";
            }
        } else {
            funcaoMsgOperation = "Serviço inválido!";
            funcaoStatusOperation = "error";
        }
        request.setAttribute("funcaoMsgOperation", funcaoMsgOperation);
        request.setAttribute("funcaoStatusOperation", funcaoStatusOperation);
        return funcaoMsgOperation;

    }

}
