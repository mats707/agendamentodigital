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

    public ArrayList<RelatorioServico> listarMaisTrabalhado();

    public ArrayList<RelatorioServico> listarMaisTrabalhadoPeriodo(int mes, int ano);

    public ArrayList<RelatorioServico> listarMaisTrabalhadoPeriodoStatus(int mes, int ano, StatusAgendamento status);

    public ArrayList<RelatorioServico> listarMaisTrabalhadoStatus(StatusAgendamento status);

    public ArrayList<RelatorioServico> listarCliente();

    public ArrayList<RelatorioServico> listarClientePeriodo(int mes, int ano);

    public ArrayList<RelatorioServico> listarClientePeriodoStatus(int mes, int ano, StatusAgendamento status);

    public ArrayList<RelatorioServico> listarClienteStatus(StatusAgendamento status);

}
