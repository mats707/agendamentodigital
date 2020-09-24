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

    private static final String BUSCAR = "select e.id, e.nome, e.horainicialtrabalho, e.horafinaltrabalho, e.intervaloagendamentogeralservico from sistema.empresa e";

    private Connection conexao;

    Date date = new Date();
    SimpleDateFormat simpDate24 = new SimpleDateFormat("kk:mm:ss");

    @Override
    public void buscar(Empresa objEmpresa) {
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
                objEmpresa.setIntervaloAgendamentoGeralServico(rs.getTime("intervaloagendamentogeralservico"));
                
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
};
