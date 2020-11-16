/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dao.interfaces.IBloqueioAgendaDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelos.BloqueioAgenda;
import modelos.Funcionario;
import modelos.Servico;
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
    private static final String LISTAR_HORARIOS_OCUPADOS = ""
            + "select\n"
            + "	 tab_bloq.horaInicial\n"
            + "	,tab_bloq.horaFinal\n"
            + "	,tab_bloq.funcionario\n"
            + "from\n"
            + "(\n"
            + "	select\n"
            + "		 bloq.id as bloqueioAgenda\n"
            + "		,bloq.dataBloqueio\n"
            + "		,bloq.horaInicial\n"
            + "		,bloq.horaInicial + bloq.duracao as horaFinal\n"
            + "		,bloq.funcionario\n"
            + "	from sistema.BloqueioAgenda bloq\n"
            + "	where\n"
            + "		bloq.dataBloqueio = ?::DATE\n"
            + "		and bloq.funcionario = ?\n"
            + ") as tab_bloq\n"
            + "inner join\n"
            + "(\n"
            + "	select\n"
            + "		t_servico.id,\n"
            + "		t1.horarioSolicitado,\n"
            + "		t1.horarioSolicitado + t_servico.duracao as horarioFinalSolicitado\n"
            + "	from (\n"
            + "		select\n"
            + "			? as servico\n"
            + "			,?::TIME as horarioSolicitado\n"
            + "	) t1\n"
            + "	inner join sistema.servico t_servico\n"
            + "	on t_servico.id = t1.servico\n"
            + ") as tab_param\n"
            + "on (tab_param.horarioSolicitado >= tab_bloq.horaInicial::time\n"
            + "AND tab_param.horarioSolicitado < tab_bloq.horaFinal::time)\n"
            + "OR (tab_param.horarioFinalSolicitado > tab_bloq.horaInicial::time\n"
            + "AND tab_param.horarioFinalSolicitado < tab_bloq.horaFinal::time)\n"
            + "OR  (tab_bloq.horaInicial::time >= tab_param.horarioSolicitado\n"
            + "AND tab_bloq.horaInicial::time < tab_param.horarioFinalSolicitado)\n"
            + "OR  (tab_bloq.horaFinal::time > tab_param.horarioSolicitado\n"
            + "AND tab_bloq.horaFinal::time < tab_param.horarioFinalSolicitado)\n"
            + "\n"
            + ";";
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

    @Override
    public ArrayList<Map<String, String>> listarHorariosOcupados(BloqueioAgenda bloqueioAgenda, Servico servico) {

        ArrayList<Map<String, String>> arrayHorariosOcupados = new ArrayList<>();

        try {

            //Conexao
            conexao = ConectaBanco.getConexao();
            //cria comando SQL
            PreparedStatement pstmt = conexao.prepareStatement(LISTAR_HORARIOS_OCUPADOS);
            pstmt.setDate(1, new java.sql.Date(bloqueioAgenda.getDataBloqueio().getTime()));
            pstmt.setInt(2, bloqueioAgenda.getFuncionario().getIdFuncionario());
            pstmt.setInt(3, servico.getIdServico());
            pstmt.setTime(4, new java.sql.Time(bloqueioAgenda.getHoraInicial().getTime()));

            //executa
            ResultSet rs = pstmt.executeQuery();

            DateFormat formatter = new SimpleDateFormat("kk:mm");
            Time horaInicialBloqueio = null;
            Time horaFinalBloqueio = null;

            while (rs.next()) {
                Map<String, String> arrHorariosOcupados = new HashMap<String, String>();
                arrHorariosOcupados.put("funcionario", rs.getString("funcionario"));
                //Parse horaAgendamento
                try {
                    horaInicialBloqueio = new java.sql.Time(formatter.parse(rs.getString("horaInicial")).getTime());
                } catch (ParseException ex) {
                    Logger.getLogger(AgendamentoDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
                try {
                    horaFinalBloqueio = new java.sql.Time(formatter.parse(rs.getString("horaFinal")).getTime());
                } catch (ParseException ex) {
                    Logger.getLogger(AgendamentoDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
                arrHorariosOcupados.put("horaInicialBloqueio", horaInicialBloqueio.toString().substring(0, 5));
                arrHorariosOcupados.put("horaFinalBloqueio", horaFinalBloqueio.toString().substring(0, 5));
                arrayHorariosOcupados.add(arrHorariosOcupados);
//                [
//                    {
//                        "cliente": "X",
//                        "funcionario": "X",
//                        "horarioAgendamento": "kk:mm" ,
//                        "horarioFinalAgendamento": "kk:mm"
//                    },
//                    {
//                        "cliente": "X",
//                        "funcionario": "X",
//                        "horarioAgendamento": "kk:mm" ,
//                        "horarioFinalAgendamento": "kk:mm"
//                    }
//                ]                
            }
            return arrayHorariosOcupados;

        } catch (Exception ex) {
            System.out.println(ex);
            return arrayHorariosOcupados;
        } finally {
            try {
                conexao.close();
            } catch (SQLException ex) {
                Logger.getLogger(AgendamentoDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
