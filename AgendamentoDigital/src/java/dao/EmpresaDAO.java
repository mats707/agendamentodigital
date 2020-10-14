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

    private static final String BUSCAR = "select e.id, e.nome, e.horainicialtrabalho, e.horafinaltrabalho, e.intervaloagendamentogeralservico, e.diasemanatrabalho, e.telefone,e.email from sistema.empresa e";

    private static final String ALTERAR = "update sistema.empresa\n"
            + "set nome=?,\n"
            + "horainicialtrabalho=?::TIME,\n"
            + "horafinaltrabalho=?::TIME,\n"
            + "intervaloagendamentogeralservico=?::INTERVAL\n,"
            + "diasemanatrabalho=?,\n"
            + "telefone=?,\n"
            + "email=?\n"
            + "where id=?;";
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
                String[] tempo = rs.getString("intervaloagendamentogeralservico").split(":");
                Duration duracao = Duration.ofHours(Integer.parseInt(tempo[0]));
                duracao = duracao.plusMinutes(Integer.parseInt(tempo[1]));
                duracao = duracao.plusSeconds(Integer.parseInt(tempo[2]));
                objEmpresa.setIntervaloAgendamentoGeralServico(duracao);

                objEmpresa.setEmail(rs.getString("email"));
                //objEmpresa.setTelefone(rs.getLong("telefone"));
                //objEmpresa.setDiasSemanaTrabalho((ArrayList<String>) rs.getArray("diaSemanaTrabalho"));

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
    public boolean alterar(Empresa objEmpresa) {
        try {

            //Conexao
            conexao = ConectaBanco.getConexao();

            ArrayList<String> dias = new ArrayList<String>();
            //conexao.createArrayOf("VARCHAR", new Object[]{"1", "2", "3"});

            for (Integer diasTrabalhados : objEmpresa.getDiaSemanaTrabalho()) {
                dias.add(String.valueOf(diasTrabalhados));
            }

            Array arrayDia = null;

            ArrayList<String> arrtelefones = new ArrayList<String>();
            //conexao.createArrayOf("VARCHAR", new Object[]{"1", "2", "3"});

            for (Long telefone : objEmpresa.getTelefone()) {
                arrtelefones.add(String.valueOf(telefone));
            }
            Array arrayTelefone = null;
            //cria comando SQL
            PreparedStatement pstmt = conexao.prepareStatement(ALTERAR);

            //Passa ID como parametro
            pstmt.setString(1, objEmpresa.getNome());
            pstmt.setTime(2, objEmpresa.getHoraInicialTrabalho());
            pstmt.setTime(3, objEmpresa.getHoraFinalTrabalho());
            pstmt.setString(4, objEmpresa.getIntervaloAgendamentoGeralServico().toString());
            try {
                arrayDia = conexao.createArrayOf("INTEGER", dias.toArray());
            } catch (SQLException ex) {
                Logger.getLogger(EmpresaDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            pstmt.setArray(5, arrayDia);
            try {
                arrayTelefone = conexao.createArrayOf("BIGINT", arrtelefones.toArray());
            } catch (SQLException ex) {
                Logger.getLogger(EmpresaDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            pstmt.setArray(6, arrayTelefone);
            pstmt.setString(7, objEmpresa.getEmail());
            pstmt.setInt(8, objEmpresa.getIdEmpresa());

            //executa
            pstmt.executeUpdate();
            return true;

        } catch (Exception ex) {
            return false;

        } finally {

            try {
                conexao.close();
            } catch (SQLException ex) {
                Logger.getLogger(EmpresaDAO.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }
};
