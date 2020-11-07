package modelos;

import java.util.Date;
import java.sql.Time;

/**
 *
 * @author alunocmc
 *
 */
public class Agendamento {

    private Integer idAgendamento;

    private Date dataAgendamento;

    private Time horaAgendamento;

    private Cliente cliente;

    private Funcionario funcionario;

    private Servico servico;

    private StatusAgendamento status;

    public Integer getIdAgendamento() {
        return idAgendamento;
    }

    public void setIdAgendamento(Integer idAgendamento) {
        this.idAgendamento = idAgendamento;
    }

    public Date getDataAgendamento() {
        return dataAgendamento;
    }

    public void setDataAgendamento(Date dataAgendamento) {
        this.dataAgendamento = dataAgendamento;
    }

    public Time getHoraAgendamento() {
        return horaAgendamento;
    }

    public void setHoraAgendamento(Time horaAgendamento) {
        this.horaAgendamento = horaAgendamento;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Funcionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }

    public Servico getServico() {
        return servico;
    }

    public void setServico(Servico servico) {
        this.servico = servico;
    }

    public StatusAgendamento getStatus() {
        return status;
    }

    public void setStatus(StatusAgendamento status) {
        this.status = status;
    }

    public Agendamento() {
    }

    public Agendamento(Integer idAgendamento, Date dataAgendamento, Time horaAgendamento, Cliente cliente, Funcionario funcionario, Servico servico, StatusAgendamento status) {
        this.idAgendamento = idAgendamento;
        this.dataAgendamento = dataAgendamento;
        this.horaAgendamento = horaAgendamento;
        this.cliente = cliente;
        this.funcionario = funcionario;
        this.servico = servico;
        this.status = status;
    }

    public Agendamento(Cliente cliente,StatusAgendamento status) {
        this.cliente = cliente;
        this.status = status;
    }    
}
