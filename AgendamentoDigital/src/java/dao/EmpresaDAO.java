package dao;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dao.interfaces.IEmpresaDAO;
import java.math.BigDecimal;
import java.sql.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.rowset.serial.SerialArray;
import modelos.Empresa;
import util.ConectaBanco;

public class EmpresaDAO implements IEmpresaDAO {

    private static final String BUSCAR = "SELECT\n"
            + "	id,\n"
            + "	nome,\n"
            + "	horainicialtrabalho,\n"
            + "	horafinaltrabalho,\n"
            + "	intervaloagendamentogeralservico,\n"
            + "	periodominimocancelamento,\n"
            + "	diasemanatrabalho,\n"
            + "	telefone,\n"
            + "	email\n"
            + "from sistema.empresa;";

    private static final String ALTERAR = "UPDATE sistema.empresa\n"
            + "SET nome=?,\n"
            + "horainicialtrabalho=?::TIME,\n"
            + "horafinaltrabalho=?::TIME,\n"
            + "intervaloagendamentogeralservico=?::INTERVAL\n,"
            + "periodominimocancelamento=?::INTERVAL\n,"
            + "diasemanatrabalho=?,\n"
            + "telefone=?,\n"
            + "email=?\n"
            + "WHERE id=?;";
    private Connection conexao;

    Date date = new Date();
    SimpleDateFormat simpDate24 = new SimpleDateFormat("kk:mm:ss");

    @Override
    public void buscar(Empresa objEmpresa) {
        ArrayList<Integer> listaDiaSemanaTrabalho = new ArrayList<>();
        Integer[] diaSemanaTrabalho = null;
        Array arrayDiaSemanaTrabalho = null;
        ArrayList<Long> listaTelefone = new ArrayList();
        Long[] telefone = null;
        Array arrayTelefone = null;
        try {

            //Conexao
            conexao = ConectaBanco.getConexao();
            //cria comando SQL
            PreparedStatement pstmt = conexao.prepareStatement(BUSCAR);
            //executa
            ResultSet rs = pstmt.executeQuery();
            System.out.println(pstmt.toString());
            while (rs.next()) {
                objEmpresa.setIdEmpresa(rs.getInt("id"));
                objEmpresa.setNome(rs.getString("nome"));
                objEmpresa.setHoraInicialTrabalho(rs.getTime("horainicialtrabalho"));
                objEmpresa.setHoraFinalTrabalho(rs.getTime("horafinaltrabalho"));
                // get the hours, minutes and seconds value and add it to the duration
                String[] tempoIntervaloAgendamentoGeralServico = rs.getString("intervaloagendamentogeralservico").split(":");
                Duration duracaoIntervaloAgendamentoGeralServico = Duration.ofHours(Integer.parseInt(tempoIntervaloAgendamentoGeralServico[0]));
                duracaoIntervaloAgendamentoGeralServico = duracaoIntervaloAgendamentoGeralServico.plusMinutes(Integer.parseInt(tempoIntervaloAgendamentoGeralServico[1]));
                duracaoIntervaloAgendamentoGeralServico = duracaoIntervaloAgendamentoGeralServico.plusSeconds(Integer.parseInt(tempoIntervaloAgendamentoGeralServico[2]));
                objEmpresa.setIntervaloAgendamentoGeralServico(duracaoIntervaloAgendamentoGeralServico);

                String[] tempoPeriodoMinimoCancelamento = rs.getString("periodominimocancelamento").split(":");
                Duration duracaoPeriodoMinimoCancelamento = Duration.ofHours(Integer.parseInt(tempoPeriodoMinimoCancelamento[0]));
                duracaoPeriodoMinimoCancelamento = duracaoPeriodoMinimoCancelamento.plusMinutes(Integer.parseInt(tempoPeriodoMinimoCancelamento[1]));
                duracaoPeriodoMinimoCancelamento = duracaoPeriodoMinimoCancelamento.plusSeconds(Integer.parseInt(tempoPeriodoMinimoCancelamento[2]));
                objEmpresa.setPeriodoMinimoCancelamento(duracaoPeriodoMinimoCancelamento);

                objEmpresa.setEmail(rs.getString("email"));

                arrayDiaSemanaTrabalho = rs.getArray("diasemanatrabalho");
                if (arrayDiaSemanaTrabalho != null) {
                    diaSemanaTrabalho = (Integer[]) arrayDiaSemanaTrabalho.getArray();
                    for (int i = 0; i < diaSemanaTrabalho.length; i++) {
                        Integer diaAdicional = null;
                        diaAdicional = diaSemanaTrabalho[i];
                        listaDiaSemanaTrabalho.add(diaAdicional);
                    }
                }
                objEmpresa.setDiaSemanaTrabalho(listaDiaSemanaTrabalho);

                arrayTelefone = rs.getArray("telefone");
                if (arrayTelefone != null) {
                    telefone = (Long[]) arrayTelefone.getArray();
                    for (int i = 0; i < telefone.length; i++) {
                        Long telefoneAdicional = null;
                        telefoneAdicional = telefone[i];

                        listaTelefone.add(telefoneAdicional);
                    }
                }
                objEmpresa.setTelefone(listaTelefone);

            }
        } catch (Exception ex) {
            System.out.println(ex);
        } finally {
            try {
                conexao.close();
            } catch (SQLException ex) {
                Logger.getLogger(EmpresaDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public String alterar(Empresa objEmpresa) {

        //Conexao
        conexao = ConectaBanco.getConexao();

        String sqlReturnCode = "0";
        Array arrayDia = null;
        Array arrayTelefone = null;

        PreparedStatement pstmt = null;
        try {
            ArrayList<String> dias = new ArrayList<String>();
            for (Integer diasTrabalhados : objEmpresa.getDiaSemanaTrabalho()) {
                dias.add(String.valueOf(diasTrabalhados));
            }

            ArrayList<String> arrtelefones = new ArrayList<String>();
            for (Long telefone : objEmpresa.getTelefone()) {
                arrtelefones.add(String.valueOf(telefone));
            }
            pstmt = conexao.prepareStatement(ALTERAR);
            pstmt.setString(1, objEmpresa.getNome());
            pstmt.setTime(2, objEmpresa.getHoraInicialTrabalho());
            pstmt.setTime(3, objEmpresa.getHoraFinalTrabalho());
            pstmt.setString(4, objEmpresa.getIntervaloAgendamentoGeralServico().toString());
            pstmt.setString(5, objEmpresa.getPeriodoMinimoCancelamento().toString());
            try {
                arrayDia = conexao.createArrayOf("INTEGER", dias.toArray());
            } catch (SQLException ex) {
                Logger.getLogger(EmpresaDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            pstmt.setArray(6, arrayDia);
            try {
                arrayTelefone = conexao.createArrayOf("BIGINT", arrtelefones.toArray());
            } catch (SQLException ex) {
                Logger.getLogger(EmpresaDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            pstmt.setArray(7, arrayTelefone);
            pstmt.setString(8, objEmpresa.getEmail());
            pstmt.setInt(9, objEmpresa.getIdEmpresa());

            pstmt.executeUpdate();
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
};
