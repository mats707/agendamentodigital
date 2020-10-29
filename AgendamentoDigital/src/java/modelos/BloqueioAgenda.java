/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelos;

import java.sql.Time;
import java.time.Duration;
import java.util.Date;

/**
 *
 * @author Rafael Pereira
 */
public class BloqueioAgenda {
    private int idBloquieio;
    
    private Date dataBloqueio;
    
    private Time horaInicial;
    
    private Duration duracaoBloqueio;
    
    private Funcionario Funcionario;

    public int getIdBloquieio() {
        return idBloquieio;
    }

    public void setIdBloquieio(int idBloquieio) {
        this.idBloquieio = idBloquieio;
    }

    public Date getDataBloqueio() {
        return dataBloqueio;
    }

    public void setDataBloqueio(Date dataBloqueio) {
        this.dataBloqueio = dataBloqueio;
    }

    public Time getHoraInicial() {
        return horaInicial;
    }

    public void setHoraInicial(Time horaInicial) {
        this.horaInicial = horaInicial;
    }

    public Duration getDuracaoBloqueio() {
        return duracaoBloqueio;
    }

    public void setDuracaoBloqueio(Duration duracaoBloqueio) {
        this.duracaoBloqueio = duracaoBloqueio;
    }

    public Funcionario getFuncionario() {
        return Funcionario;
    }

    public void setFuncionario(Funcionario Funcionario) {
        this.Funcionario = Funcionario;
    }

    public BloqueioAgenda() {
    }

    public BloqueioAgenda(int idBloquieio, Date dataBloqueio, Time horaInicial, Duration duracaoBloqueio, Funcionario Funcionario) {
        this.idBloquieio = idBloquieio;
        this.dataBloqueio = dataBloqueio;
        this.horaInicial = horaInicial;
        this.duracaoBloqueio = duracaoBloqueio;
        this.Funcionario = Funcionario;
    }
    
    
}
