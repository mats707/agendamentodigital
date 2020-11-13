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

    public ArrayList<RelatorioServico> listarMaisAgendado();

    public ArrayList<RelatorioServico> listarMaisAgendadoPeriodo(int mes,int ano);
    
    public ArrayList<RelatorioServico> listarMaisTrabalhado();

    public ArrayList<RelatorioServico> listarMaisTrabalhadoPeriodo(int mes,int ano);
    
    public ArrayList<RelatorioServico> listarMaisAgendadoPeriodoStatus (int mes, int ano, StatusAgendamento status);

}
