/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao.interfaces;

import java.util.ArrayList;
import modelos.Agendamento;

/**
 *
 * @author profe
 */
public interface IAgendamentoDAO {

    public String cadastrar(Agendamento agendamento);

    public ArrayList<Agendamento> listar();

    public ArrayList<Agendamento> listarPorCategoria(Agendamento agendamento);

    public void buscar(Agendamento agendamento);
    
    public Agendamento buscaCompleta(Agendamento agendamento);

    public String alterarAgendamento(Agendamento agendamento);

    public boolean excluir(Agendamento agendamento);

}
