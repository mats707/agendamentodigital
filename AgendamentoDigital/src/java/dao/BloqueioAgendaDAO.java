/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import static dao.RelatoriosDAO.MAISAGENDADO;
import dao.interfaces.IBloqueioAgendaDAO;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.Duration;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelos.BloqueioAgenda;
import modelos.Funcionario;
import util.ConectaBanco;

/**
 *
 * @author Rafael Pereira
 */
public class BloqueioAgendaDAO implements IBloqueioAgendaDAO {

    private static final String BUSCAR = "";
    private static final String LISTAR = "SELECT bl.id,bl.databloqueio,bl.horainicial,bl.funcionario,pessoa.nome,bl.duracao from sistema.bloqueioagenda as bl, sistema.pessoa as pessoa, sistema.funcionario as func where bl.funcionario=func.id and func.pessoa = pessoa.id order by id desc;";
    private static final String CADASTRAR = "insert into sistema.bloqueioagenda (id,dataBloqueio,horaInicial,duracao,funcionario) values\n"
            + " (nextval('sistema.sqn_bloqueioAgenda'),?::DATE,?::TIME,?::INTERVAL,?);";
    private static final String DELETAR = "DELETE FROM sistema.bloqueioagenda where databloqueio =?::DATE and horainicial=?::TIME and funcionario=?;";
    private static final String LISTAR_FILTRO_FUNC = "SELECT bl.id,bl.databloqueio,bl.horainicial,bl.funcionario,pessoa.nome,bl.duracao from sistema.bloqueioagenda as bl, sistema.pessoa as pessoa, sistema.funcionario as func where bl.funcionario=func.id and func.pessoa = pessoa.id and bl.funcionario=? order by id desc;";

    private Connection conexao;

    @Override
    public void buscar(BloqueioAgenda bloqueio) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String deletar(BloqueioAgenda bloqueio) {
        String sqlReturnCode = "0";

        PreparedStatement pstmt = null;
        try {
            conexao = ConectaBanco.getConexao();
            pstmt = conexao.prepareStatement(DELETAR, Statement.RETURN_GENERATED_KEYS);
            pstmt.setDate(1, new java.sql.Date(bloqueio.getDataBloqueio().getTime()));
            pstmt.setTime(2, bloqueio.getHoraInicial());
            pstmt.setInt(3, bloqueio.getFuncionario().getIdFuncionario());
            pstmt.execute();

            final ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                bloqueio.setIdBloquieio(rs.getInt("id"));
            }

            return sqlReturnCode;

        } catch (SQLException sqlErro) {

            sqlReturnCode = sqlErro.getSQLState();

            if (sqlReturnCode.equalsIgnoreCase("23505")) { //Significa que violou uma unique constraint
                return sqlErro.getMessage().split("\"")[1];
            } else {
                return sqlReturnCode;
            }

        } finally {
            if (conexao != null) {
                try {
                    conexao.close();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }
    }

    @Override
    public String cadastrar(BloqueioAgenda bloqueio) {
        String sqlReturnCode = "0";

        PreparedStatement pstmt = null;
        try {
            conexao = ConectaBanco.getConexao();
            pstmt = conexao.prepareStatement(CADASTRAR, Statement.RETURN_GENERATED_KEYS);
            pstmt.setDate(1, new java.sql.Date(bloqueio.getDataBloqueio().getTime()));
            pstmt.setTime(2, bloqueio.getHoraInicial());
            pstmt.setString(3, bloqueio.getDuracaoBloqueio().toString());
            pstmt.setInt(4, bloqueio.getFuncionario().getIdFuncionario());
            pstmt.execute();

            final ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                bloqueio.setIdBloquieio(rs.getInt("id"));
            }

            return sqlReturnCode;

        } catch (SQLException sqlErro) {

            sqlReturnCode = sqlErro.getSQLState();

            if (sqlReturnCode.equalsIgnoreCase("23505")) { //Significa que violou uma unique constraint
                return sqlErro.getMessage().split("\"")[1];
            } else {
                return sqlReturnCode;
            }

        } finally {
            if (conexao != null) {
                try {
                    conexao.close();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }
    }

    @Override
    public ArrayList<BloqueioAgenda> listarBloqueio() {
        ArrayList<BloqueioAgenda> listaBloqueioAgenda = new ArrayList<BloqueioAgenda>();

        try {

            conexao = ConectaBanco.getConexao();

            PreparedStatement pstmt = conexao.prepareStatement(LISTAR);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                BloqueioAgenda bloqueio = new BloqueioAgenda();
                bloqueio.setIdBloquieio(rs.getInt("id"));
                bloqueio.setDataBloqueio(rs.getDate("dataBloqueio"));
                bloqueio.setHoraInicial(rs.getTime("horaInicial"));
                //Converse a duracao do banco (interval) para Duration do java
                String[] tempo = rs.getString("duracao").split(":");
                Duration duracao = Duration.ofHours(Integer.parseInt(tempo[0]));
                duracao = duracao.plusMinutes(Integer.parseInt(tempo[1]));
                duracao = duracao.plusSeconds(Integer.parseInt(tempo[2]));
                bloqueio.setDuracaoBloqueio(duracao);

                Funcionario objFunc = new Funcionario();
                objFunc.setIdFuncionario(rs.getInt("funcionario"));
                objFunc.setIdPessoa(Integer.BYTES);
                bloqueio.setFuncionario(objFunc);

                listaBloqueioAgenda.add(bloqueio);
            }
            return listaBloqueioAgenda;
        } catch (Exception ex) {
            return listaBloqueioAgenda;
        } finally {
            try {
                conexao.close();

            } catch (SQLException ex) {
                Logger.getLogger(BloqueioAgenda.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public ArrayList<BloqueioAgenda> listarBloqueioFunc(BloqueioAgenda bloqueio) {
        ArrayList<BloqueioAgenda> listaBloqueioAgenda = new ArrayList<BloqueioAgenda>();

        try {

            conexao = ConectaBanco.getConexao();

            PreparedStatement pstmt = conexao.prepareStatement(LISTAR_FILTRO_FUNC);
            pstmt.setInt(1, bloqueio.getFuncionario().getIdFuncionario());

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                BloqueioAgenda novobloqueio = new BloqueioAgenda();
                novobloqueio.setIdBloquieio(rs.getInt("id"));
                novobloqueio.setDataBloqueio(rs.getDate("dataBloqueio"));
                novobloqueio.setHoraInicial(rs.getTime("horaInicial"));
                //Converse a duracao do banco (interval) para Duration do java
                String[] tempo = rs.getString("duracao").split(":");
                Duration duracao = Duration.ofHours(Integer.parseInt(tempo[0]));
                duracao = duracao.plusMinutes(Integer.parseInt(tempo[1]));
                duracao = duracao.plusSeconds(Integer.parseInt(tempo[2]));
                novobloqueio.setDuracaoBloqueio(duracao);

                Funcionario objFunc = new Funcionario();
                objFunc.setIdFuncionario(rs.getInt("funcionario"));
                objFunc.setIdPessoa(Integer.BYTES);
                novobloqueio.setFuncionario(objFunc);

                listaBloqueioAgenda.add(novobloqueio);
            }
            return listaBloqueioAgenda;
        } catch (Exception ex) {
            return listaBloqueioAgenda;
        } finally {
            try {
                conexao.close();

            } catch (SQLException ex) {
                Logger.getLogger(BloqueioAgenda.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
