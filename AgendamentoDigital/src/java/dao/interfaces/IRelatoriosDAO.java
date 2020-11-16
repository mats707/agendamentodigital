/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao.interfaces;

import java.util.ArrayList;
import modelos.Agendamento;
import modelos.RelatorioServico;
import modelos.StatusAgendamento;

/**
 *
 * @author Rafael Pereira
 */
public interface IRelatoriosDAO {

    public ArrayList<RelatorioServico> listarAgendamentos();

    public ArrayList<RelatorioServico> listarAgendamentosPeriodo(int mes, int ano);

    public ArrayList<RelatorioServico> listarAgendamentosPeriodoStatus(int mes, int ano, StatusAgendamento status);

    public ArrayList<RelatorioServico> listarAgendamentosStatus(StatusAgendamento status);

    public ArrayList<RelatorioServico> listarFuncionarios();

    public ArrayList<RelatorioServico> listarFuncionariosPeriodo(int mes, int ano);

    public ArrayList<RelatorioServico> listarFuncionariosPeriodoStatus(int mes, int ano, StatusAgendamento status);

    public ArrayList<RelatorioServico> listarFuncionariosStatus(StatusAgendamento status);

    public ArrayList<RelatorioServico> listarClientes();

    public ArrayList<RelatorioServico> listarClientesPeriodo(int mes, int ano);

    public ArrayList<RelatorioServico> listarClientesPeriodoStatus(int mes, int ano, StatusAgendamento status);

    public ArrayList<RelatorioServico> listarClientesStatus(StatusAgendamento status);
}
