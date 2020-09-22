/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelos;

import java.sql.Time;

/**
 *
 * @author mathm
 */
public class Empresa {
    
    private Integer idEmpresa;
    private String nome;
    private Time horaInicialTrabalho;
    private Time horaFinalTrabalho;
    private Time intervaloAgendamentoGeralServico;

    public Integer getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(Integer idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Time getHoraInicialTrabalho() {
        return horaInicialTrabalho;
    }

    public void setHoraInicialTrabalho(Time horaInicialTrabalho) {
        this.horaInicialTrabalho = horaInicialTrabalho;
    }

    public Time getHoraFinalTrabalho() {
        return horaFinalTrabalho;
    }

    public void setHoraFinalTrabalho(Time horaFinalTrabalho) {
        this.horaFinalTrabalho = horaFinalTrabalho;
    }

    public Time getIntervaloAgendamentoGeralServico() {
        return intervaloAgendamentoGeralServico;
    }

    public void setIntervaloAgendamentoGeralServico(Time intervaloAgendamentoGeralServico) {
        this.intervaloAgendamentoGeralServico = intervaloAgendamentoGeralServico;
    }

    public Empresa(Integer idEmpresa, String nome, Time horaInicialTrabalho, Time horaFinalTrabalho, Time intervaloAgendamentoGeralServico) {
        this.idEmpresa = idEmpresa;
        this.nome = nome;
        this.horaInicialTrabalho = horaInicialTrabalho;
        this.horaFinalTrabalho = horaFinalTrabalho;
        this.intervaloAgendamentoGeralServico = intervaloAgendamentoGeralServico;
    }

    public Empresa(Integer idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    public Empresa() {
    }
    
}
