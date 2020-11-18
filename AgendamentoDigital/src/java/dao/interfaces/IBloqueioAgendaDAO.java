/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao.interfaces;

import java.util.ArrayList;
import java.util.Map;
import modelos.BloqueioAgenda;
import modelos.Servico;

/**
 *
 * @author Rafael Pereira
 */
public interface IBloqueioAgendaDAO {

    public void buscar(BloqueioAgenda bloqueioAgenda);

    public ArrayList<BloqueioAgenda> listarBloqueio();

    public String deletar (BloqueioAgenda bloqueioAgenda);

    public String cadastrar(BloqueioAgenda bloqueioAgenda);
    
    public ArrayList<Map<String, String>> listarHorariosOcupados(BloqueioAgenda bloqueioAgenda, Servico servico);

}
