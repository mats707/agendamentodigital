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
import modelos.StatusAgendamento;
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

    public static final String MAISAGENDADO_PERIODO_STATUS = "select ser.id as idServico, ser.nome as servico, count(ag.servico)\n"
            + " from sistema.agendamento as ag, sistema.servico as ser, sistema.statusagendamento as st \n"
            + " where ag.servico=ser.id and date_part('month',ag.dataagendamento)=? and date_part('year',ag.dataagendamento)=? and st.nome=? and st.id=ag.status \n"
            + " group by ag.servico, ser.nome, ser.id \n"
            + " order by count(ag.servico) DESC;";

    public static final String MAISAGENDADO_STATUS = "select ser.id as idServico, ser.nome as servico, count(ag.servico)\n"
            + " from sistema.agendamento as ag, sistema.servico as ser, sistema.statusagendamento as st \n"
            + " where ag.servico=ser.id and st.nome=? and st.id=ag.status \n"
            + " group by ag.servico, ser.nome, ser.id \n"
            + " order by count(ag.servico) DESC;";

    public static final String FUNCIONARIO_MAIS_TRABALHO_PERIODO = "select pe.nome as funcionario, count(ag.funcionario)\n"
            + " from sistema.agendamento as ag, sistema.pessoa as pe, sistema.funcionario as func \n"
            + " where pe.id=func.pessoa and ag.funcionario=func.id and date_part('month',ag.dataagendamento)=? and date_part('year',ag.dataagendamento)=? \n"
            + " group by ag.funcionario, pe.nome \n"
            + " order by count(ag.funcionario) DESC;";
    public static final String FUNCIONARIO_MAIS_TRABALHO_PERIODO_STATUS = "select pe.nome as funcionario, count(ag.funcionario)\n"
            + " from sistema.agendamento as ag, sistema.pessoa as pe, sistema.funcionario as func, sistema.statusagendamento as st \n"
            + " where pe.id=func.pessoa and ag.funcionario=func.id and date_part('month',ag.dataagendamento)=? and date_part('year',ag.dataagendamento)=? and st.nome=? and st.id=ag.status \n"
            + " group by ag.funcionario, pe.nome \n"
            + " order by count(ag.funcionario) DESC;";
    public static final String FUNCIONARIO_MAIS_TRABALHO_STATUS = "select pe.nome as funcionario, count(ag.funcionario)\n"
            + " from sistema.agendamento as ag, sistema.pessoa as pe, sistema.funcionario as func, sistema.statusagendamento as st \n"
            + " where pe.id=func.pessoa and ag.funcionario=func.id and st.nome=? and st.id=ag.status \n"
            + " group by ag.funcionario, pe.nome \n"
            + " order by count(ag.funcionario) DESC;";
    public static final String FUNCIONARIO_MAIS_TRABALHO = "select pe.nome as funcionario, count(ag.funcionario)\n"
            + " from sistema.agendamento as ag, sistema.pessoa as pe, sistema.funcionario as func \n"
            + " where pe.id=func.pessoa and ag.funcionario=func.id \n"
            + " group by ag.funcionario, pe.nome \n"
            + " order by count(ag.funcionario) DESC;";

    public static final String CLIENTE = "select pe.nome as cliente, count(ag.cliente) \n"
            + "from sistema.agendamento as ag, sistema.pessoa as pe, sistema.cliente as cli \n"
            + "where pe.id=cli.pessoa and ag.cliente=cli.id \n"
            + "group by ag.cliente, pe.nome \n"
            + "order by count(ag.cliente) DESC;";
    public static final String CLIENTE_PERIODO = "select pe.nome as cliente, count(ag.cliente) \n"
            + "from sistema.agendamento as ag, sistema.pessoa as pe, sistema.cliente as cli \n"
            + "where pe.id=cli.pessoa and ag.cliente=cli.id and date_part('month',ag.dataagendamento)=? and date_part('year',ag.dataagendamento)=?\n"
            + "group by ag.cliente, pe.nome \n"
            + "order by count(ag.cliente) DESC;";

    public static final String CLIENTE_PERIODO_STATUS = "select pe.nome as cliente, count(ag.cliente) \n"
            + "from sistema.agendamento as ag, sistema.pessoa as pe, sistema.cliente as cli, sistema.statusagendamento as st \n"
            + "where pe.id=cli.pessoa and ag.cliente=cli.id and date_part('month',ag.dataagendamento)=? and date_part('year',ag.dataagendamento)=? and st.nome=? and st.id=ag.status \n"
            + "group by ag.cliente, pe.nome \n"
            + "order by count(ag.cliente) DESC;";

    public static final String CLIENTE_STATUS = "select pe.nome as cliente, count(ag.cliente) \n"
            + "from sistema.agendamento as ag, sistema.pessoa as pe, sistema.cliente as cli, sistema.statusagendamento as st \n"
            + "where pe.id=cli.pessoa and ag.cliente=cli.id and st.nome=? and st.id=ag.status \n"
            + "group by ag.cliente, pe.nome \n"
            + "order by count(ag.cliente) DESC;";

    private Connection conexao;

    @Override
    public ArrayList<RelatorioServico> listarAgendamentos() {
        ArrayList<RelatorioServico> listaAgendamentos = new ArrayList<RelatorioServico>();

        try {

            conexao = ConectaBanco.getConexao();

            PreparedStatement pstmt = conexao.prepareStatement(MAISAGENDADO);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                RelatorioServico novoRelatorio = new RelatorioServico();

                novoRelatorio.setIdServico(Integer.parseInt(rs.getString("idServico")));
                novoRelatorio.setNomeServico(rs.getString("servico"));
                novoRelatorio.setCount(Integer.parseInt(rs.getString("count")));

                listaAgendamentos.add(novoRelatorio);
            }
            return listaAgendamentos;
        } catch (Exception ex) {
            return listaAgendamentos;
        } finally {
            try {
                conexao.close();
            } catch (SQLException ex) {
                Logger.getLogger(PessoaDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public ArrayList<RelatorioServico> listarAgendamentosPeriodo(int mes, int ano) {
        ArrayList<RelatorioServico> listaAgendamentos = new ArrayList<RelatorioServico>();

        try {

            conexao = ConectaBanco.getConexao();

            PreparedStatement pstmt = conexao.prepareStatement(MAISAGENDADO_PERIODO);

            if (mes > 0 && mes < 13) {
                pstmt.setInt(1, mes);
                pstmt.setInt(2, ano);
            }
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                RelatorioServico novoRelatorio = new RelatorioServico();

                novoRelatorio.setIdServico(Integer.parseInt(rs.getString("idServico")));
                novoRelatorio.setNomeServico(rs.getString("servico"));
                novoRelatorio.setCount(Integer.parseInt(rs.getString("count")));

                listaAgendamentos.add(novoRelatorio);
            }
            return listaAgendamentos;
        } catch (Exception ex) {
            return listaAgendamentos;
        } finally {
            try {
                conexao.close();
            } catch (SQLException ex) {
                Logger.getLogger(PessoaDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

<<<<<<< HEAD
    @Override
    public ArrayList<RelatorioServico> listarMaisAgendadoPeriodoStatus(int mes, int ano, StatusAgendamento status) {
        ArrayList<RelatorioServico> listaMaisAgendado = new ArrayList<RelatorioServico>();
=======
    public ArrayList<RelatorioServico> listarAgendamentosPeriodoStatus(int mes, int ano, StatusAgendamento status) {
        ArrayList<RelatorioServico> listaAgendamentos = new ArrayList<RelatorioServico>();
>>>>>>> feature-funcionario

        try {

            conexao = ConectaBanco.getConexao();

            PreparedStatement pstmt = conexao.prepareStatement(MAISAGENDADO_PERIODO_STATUS);

            if (mes > 0 && mes < 13) {
                pstmt.setInt(1, mes);
                pstmt.setInt(2, ano);
            }
            pstmt.setString(3, status.toString());

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                RelatorioServico novoRelatorio = new RelatorioServico();

                novoRelatorio.setIdServico(Integer.parseInt(rs.getString("idServico")));
                novoRelatorio.setNomeServico(rs.getString("servico"));
                novoRelatorio.setCount(Integer.parseInt(rs.getString("count")));

                listaAgendamentos.add(novoRelatorio);
            }
            return listaAgendamentos;
        } catch (Exception ex) {
            return listaAgendamentos;
        } finally {
            try {
                conexao.close();
            } catch (SQLException ex) {
                Logger.getLogger(PessoaDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public ArrayList<RelatorioServico> listarAgendamentosStatus(StatusAgendamento status) {
        ArrayList<RelatorioServico> listaAgendamentos = new ArrayList<RelatorioServico>();

        try {

            conexao = ConectaBanco.getConexao();

            PreparedStatement pstmt = conexao.prepareStatement(MAISAGENDADO_STATUS);

            pstmt.setString(1, status.toString());

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                RelatorioServico novoRelatorio = new RelatorioServico();

                novoRelatorio.setIdServico(Integer.parseInt(rs.getString("idServico")));
                novoRelatorio.setNomeServico(rs.getString("servico"));
                novoRelatorio.setCount(Integer.parseInt(rs.getString("count")));

                listaAgendamentos.add(novoRelatorio);
            }
            return listaAgendamentos;
        } catch (Exception ex) {
            return listaAgendamentos;
        } finally {
            try {
                conexao.close();
            } catch (SQLException ex) {
                Logger.getLogger(PessoaDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public ArrayList<RelatorioServico> listarFuncionarios() {
        ArrayList<RelatorioServico> listaAgendamentos = new ArrayList<RelatorioServico>();

        try {

            conexao = ConectaBanco.getConexao();

            PreparedStatement pstmt = conexao.prepareStatement(FUNCIONARIO_MAIS_TRABALHO);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                RelatorioServico novoRelatorio = new RelatorioServico();

                novoRelatorio.setNomeFuncionario(rs.getString("funcionario"));
                novoRelatorio.setCount(Integer.parseInt(rs.getString("count")));

                listaAgendamentos.add(novoRelatorio);
            }
            return listaAgendamentos;
        } catch (Exception ex) {
            return listaAgendamentos;
        } finally {
            try {
                conexao.close();
            } catch (SQLException ex) {
                Logger.getLogger(PessoaDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public ArrayList<RelatorioServico> listarFuncionariosPeriodo(int mes, int ano) {
        ArrayList<RelatorioServico> listaAgendamentos = new ArrayList<RelatorioServico>();

        try {

            conexao = ConectaBanco.getConexao();

            PreparedStatement pstmt = conexao.prepareStatement(FUNCIONARIO_MAIS_TRABALHO_PERIODO);

            if (mes > 0 && mes < 13) {
                pstmt.setInt(1, mes);
                pstmt.setInt(2, ano);
            }

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                RelatorioServico novoRelatorio = new RelatorioServico();

                novoRelatorio.setNomeFuncionario(rs.getString("funcionario"));
                novoRelatorio.setCount(Integer.parseInt(rs.getString("count")));

                listaAgendamentos.add(novoRelatorio);
            }
            return listaAgendamentos;
        } catch (Exception ex) {
            return listaAgendamentos;
        } finally {
            try {
                conexao.close();
            } catch (SQLException ex) {
                Logger.getLogger(PessoaDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public ArrayList<RelatorioServico> listarFuncionariosPeriodoStatus(int mes, int ano, StatusAgendamento status) {
        ArrayList<RelatorioServico> listaAgendamentos = new ArrayList<RelatorioServico>();

        try {

            conexao = ConectaBanco.getConexao();

            PreparedStatement pstmt = conexao.prepareStatement(FUNCIONARIO_MAIS_TRABALHO_PERIODO_STATUS);

            if (mes > 0 && mes < 13) {
                pstmt.setInt(1, mes);
                pstmt.setInt(2, ano);
            }
            pstmt.setString(3, status.toString());

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                RelatorioServico novoRelatorio = new RelatorioServico();

                novoRelatorio.setNomeFuncionario(rs.getString("funcionario"));
                novoRelatorio.setCount(Integer.parseInt(rs.getString("count")));

                listaAgendamentos.add(novoRelatorio);
            }
            return listaAgendamentos;
        } catch (Exception ex) {
            return listaAgendamentos;
        } finally {
            try {
                conexao.close();
            } catch (SQLException ex) {
                Logger.getLogger(PessoaDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public ArrayList<RelatorioServico> listarFuncionariosStatus(StatusAgendamento status) {
        ArrayList<RelatorioServico> listaAgendamentos = new ArrayList<RelatorioServico>();

        try {

            conexao = ConectaBanco.getConexao();

            PreparedStatement pstmt = conexao.prepareStatement(FUNCIONARIO_MAIS_TRABALHO_PERIODO_STATUS);

            pstmt.setString(3, status.toString());

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                RelatorioServico novoRelatorio = new RelatorioServico();

                novoRelatorio.setNomeFuncionario(rs.getString("funcionario"));
                novoRelatorio.setCount(Integer.parseInt(rs.getString("count")));

                listaAgendamentos.add(novoRelatorio);
            }
            return listaAgendamentos;
        } catch (Exception ex) {
            return listaAgendamentos;
        } finally {
            try {
                conexao.close();
            } catch (SQLException ex) {
                Logger.getLogger(PessoaDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public ArrayList<RelatorioServico> listarCliente() {
        ArrayList<RelatorioServico> listaAgendamentos = new ArrayList<RelatorioServico>();

        try {

            conexao = ConectaBanco.getConexao();

            PreparedStatement pstmt = conexao.prepareStatement(CLIENTE);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                RelatorioServico novoRelatorio = new RelatorioServico();

                novoRelatorio.setNomeFuncionario(rs.getString("cliente"));
                novoRelatorio.setCount(Integer.parseInt(rs.getString("count")));

                listaAgendamentos.add(novoRelatorio);
            }
            return listaAgendamentos;
        } catch (Exception ex) {
            return listaAgendamentos;
        } finally {
            try {
                conexao.close();
            } catch (SQLException ex) {
                Logger.getLogger(PessoaDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public ArrayList<RelatorioServico> listarClientePeriodo(int mes, int ano) {
        ArrayList<RelatorioServico> listaAgendamentos = new ArrayList<RelatorioServico>();

        try {

            conexao = ConectaBanco.getConexao();

            PreparedStatement pstmt = conexao.prepareStatement(CLIENTE_PERIODO);

            if (mes > 0 && mes < 13) {
                pstmt.setInt(1, mes);
                pstmt.setInt(2, ano);
            }

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                RelatorioServico novoRelatorio = new RelatorioServico();

                novoRelatorio.setNomeFuncionario(rs.getString("cliente"));
                novoRelatorio.setCount(Integer.parseInt(rs.getString("count")));

                listaAgendamentos.add(novoRelatorio);
            }
            return listaAgendamentos;
        } catch (Exception ex) {
            return listaAgendamentos;
        } finally {
            try {
                conexao.close();
            } catch (SQLException ex) {
                Logger.getLogger(PessoaDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public ArrayList<RelatorioServico> listarClientePeriodoStatus(int mes, int ano, StatusAgendamento status) {
        ArrayList<RelatorioServico> listaAgendamentos = new ArrayList<RelatorioServico>();

        try {

            conexao = ConectaBanco.getConexao();

            PreparedStatement pstmt = conexao.prepareStatement(CLIENTE_PERIODO_STATUS);

            if (mes > 0 && mes < 13) {
                pstmt.setInt(1, mes);
                pstmt.setInt(2, ano);
            }
            pstmt.setString(3, status.toString());

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                RelatorioServico novoRelatorio = new RelatorioServico();

                novoRelatorio.setNomeFuncionario(rs.getString("cliente"));
                novoRelatorio.setCount(Integer.parseInt(rs.getString("count")));

                listaAgendamentos.add(novoRelatorio);
            }
            return listaAgendamentos;
        } catch (Exception ex) {
            return listaAgendamentos;
        } finally {
            try {
                conexao.close();
            } catch (SQLException ex) {
                Logger.getLogger(PessoaDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public ArrayList<RelatorioServico> listarClienteStatus(StatusAgendamento status) {
        ArrayList<RelatorioServico> listaAgendamentos = new ArrayList<RelatorioServico>();

        try {

            conexao = ConectaBanco.getConexao();

            PreparedStatement pstmt = conexao.prepareStatement(CLIENTE_PERIODO_STATUS);

            pstmt.setString(3, status.toString());

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                RelatorioServico novoRelatorio = new RelatorioServico();

                novoRelatorio.setNomeFuncionario(rs.getString("cliente"));
                novoRelatorio.setCount(Integer.parseInt(rs.getString("count")));

                listaAgendamentos.add(novoRelatorio);
            }
            return listaAgendamentos;
        } catch (Exception ex) {
            return listaAgendamentos;
        } finally {
            try {
                conexao.close();
            } catch (SQLException ex) {
                Logger.getLogger(PessoaDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
