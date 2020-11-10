/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao.interfaces;

import java.util.ArrayList;
import java.sql.Time;
import java.util.Map;
import modelos.Agendamento;

/**
 *
 * @author profe
 */
public interface IAgendamentoDAO {

    public String cadastrar(Agendamento agendamento);

    public ArrayList<Agendamento> listar();

    public ArrayList<Map<String, String>> listarHorariosOcupados(Agendamento agendamento);

    public ArrayList<Agendamento> listarCliente(Agendamento agendamento);

    public ArrayList<Agendamento> listarFuncionario(Agendamento agendamento);

    public String alterarAgendamento(Agendamento agendamento);

    public String alterarStatus(Agendamento agendamento);

    public Agendamento buscar(Agendamento agendamento);

    public Agendamento verificarStatusCliente(Agendamento agendamento);

    public Agendamento verificarStatusFuncionario(Agendamento agendamento);

    public boolean excluir(Agendamento agendamento);

}
