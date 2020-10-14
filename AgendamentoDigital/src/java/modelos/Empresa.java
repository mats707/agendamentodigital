/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelos;

import java.sql.Time;
import java.time.Duration;
import java.util.ArrayList;

/**
 *
 * @author mathm
 */
public class Empresa {

    private Integer idEmpresa;
    private String nome;
    private Time horaInicialTrabalho;
    private Time horaFinalTrabalho;
    private Duration intervaloAgendamentoGeralServico;
    private ArrayList<Integer> diaSemanaTrabalho; // 0- Domingo , 1-Segunda ..., 6-Sabado
    private ArrayList<Long> telefone;
    private String email;

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

    public Duration getIntervaloAgendamentoGeralServico() {
        return intervaloAgendamentoGeralServico;
    }

    public void setIntervaloAgendamentoGeralServico(Duration intervaloAgendamentoGeralServico) {
        this.intervaloAgendamentoGeralServico = intervaloAgendamentoGeralServico;
    }

    

    public Empresa(Integer idEmpresa, String nome, Time horaInicialTrabalho, Time horaFinalTrabalho, Duration intervaloAgendamentoGeralServico) {
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

    public ArrayList<Integer> getDiaSemanaTrabalho() {
        return diaSemanaTrabalho;
    }

    public void setDiaSemanaTrabalho(ArrayList<Integer> diasSemanaTrabalho) {
        this.diaSemanaTrabalho = diasSemanaTrabalho;
    }

    public ArrayList<Long> getTelefone() {
        return telefone;
    }

    public void setTelefone(ArrayList<Long> telefone) {
        this.telefone = telefone;
    }

    
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    

}
