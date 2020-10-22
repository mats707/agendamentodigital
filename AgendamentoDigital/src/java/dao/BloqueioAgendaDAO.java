/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import static dao.RelatoriosDAO.MAISAGENDADO;
import dao.interfaces.IBloqueioAgendaDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
    private static final String LISTAR = "SELECT bl.id,bl.databloqueio,bl.horainicial,bl.funcionario,pessoa.nome from sistema.bloqueioagenda as bl, sistema.pessoa as pessoa, sistema.funcionario as func where bl.funcionario=func.id and func.pessoa = pessoa.id;";
    private Connection conexao;

    @Override
    public void buscar(BloqueioAgenda bloqueio) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean alterar(BloqueioAgenda bloqueio) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean cadastrar(BloqueioAgenda bloqueio) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
                Logger.getLogger(BloqueioAgenda.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
