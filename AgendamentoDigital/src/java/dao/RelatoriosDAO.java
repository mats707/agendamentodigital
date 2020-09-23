/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dao.interfaces.IRelatoriosDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelos.Agendamento;
import modelos.RelatorioServico;
import modelos.Servico;
import util.ConectaBanco;

/**
 *
 * @author Rafael Pereira
 */
public class RelatoriosDAO implements IRelatoriosDAO {

    public static final String MAISAGENDADO = "select ser.id as idServico, ser.nome as servico, count(ag.servico) \n"
            + " from sistema.agendamento as ag, sistema.servico as ser \n"
            + " where ag.servico=ser.id \n"
            + " group by ag.servico, ser.nome, ser.id \n"
            + " order by count(ag.servico) DESC;";
    public static final String MAISAGENDADO_PERIODO = "select ser.id as idServico, ser.nome as servico, count(ag.servico)\n"
            + " from sistema.agendamento as ag, sistema.servico as ser\n"
            + " where ag.servico=ser.id and date_part('month',ag.dataagendamento)=? and date_part('year',ag.dataagendamento)=?\n"
            + " group by ag.servico, ser.nome, ser.id \n"
            + " order by count(ag.servico) DESC;";
    
  

    private Connection conexao;

    @Override
    public ArrayList<RelatorioServico> listarMaisAgendado() {
        ArrayList<RelatorioServico> listaMaisAgendado = new ArrayList<RelatorioServico>();

        try {

            conexao = ConectaBanco.getConexao();

            PreparedStatement pstmt = conexao.prepareStatement(MAISAGENDADO);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                RelatorioServico novoRelatorio = new RelatorioServico();

                novoRelatorio.setIdServico(Integer.parseInt(rs.getString("idServico")));
                novoRelatorio.setNomeServico(rs.getString("servico"));
                novoRelatorio.setCount(Integer.parseInt(rs.getString("count")));

                listaMaisAgendado.add(novoRelatorio);
            }
            return listaMaisAgendado;
        } catch (Exception ex) {
            return listaMaisAgendado;
        } finally {
            try {
                conexao.close();
            } catch (SQLException ex) {
                Logger.getLogger(PessoaDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public ArrayList<RelatorioServico> listarMaisAgendadoPeriodo(int mes, int ano) {
        ArrayList<RelatorioServico> listaMaisAgendado = new ArrayList<RelatorioServico>();

        try {

            conexao = ConectaBanco.getConexao();

            PreparedStatement pstmt = conexao.prepareStatement(MAISAGENDADO_PERIODO);

//            if (mes < 10) {
//                pstmt.setString(1, "0" + Integer.toString(mes));
//            } else {
//                pstmt.setString(1, Integer.toString(mes));
//            }
            //           pstmt.setString(2, Integer.toString(ano));
            if (mes > 0 && mes < 13)
            {
                pstmt.setInt(1, mes);
                pstmt.setInt(2, ano);
            }

            //16/09/2020 - Erro encontrado na execução da query - String apararentemente correta
            //18/09/2020 - Solução, no pgadmin utiliza-se com aspas (String), porém pela DAO
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                RelatorioServico novoRelatorio = new RelatorioServico();

                novoRelatorio.setIdServico(Integer.parseInt(rs.getString("idServico")));
                novoRelatorio.setNomeServico(rs.getString("servico"));
                novoRelatorio.setCount(Integer.parseInt(rs.getString("count")));

                listaMaisAgendado.add(novoRelatorio);
            }
            return listaMaisAgendado;
        } catch (Exception ex) {
            return listaMaisAgendado;
        } finally {
            try {
                conexao.close();
            } catch (SQLException ex) {
                Logger.getLogger(PessoaDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
