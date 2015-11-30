package luizventurote.minhagrana.model;

import android.util.Log;
import java.util.Date;
import luizventurote.minhagrana.helper.Helper;

public class MovimentacaoFinanceira extends ObjetoPersistente {

    /**
     * Descrição da movimentação financeira
     */
    private String descricao;

    /**
     * Valor da movimentação
     */
    private Double valor;

    /**
     * Data de inserção no sistema
     */
    private Date data_system;

    /**
     * Data da movimentação financeira
     */
    private Date data;

    public MovimentacaoFinanceira(String descricao, Double valor, Date data) {
        this.descricao = descricao;
        this.valor = valor;
        this.data = data;
        this.data_system = new Date();
    }

    public MovimentacaoFinanceira(Long id, String descricao, Double valor, Date data) {
        this.id = id;
        this.descricao = descricao;
        this.valor = valor;
        this.data = data;
    }

    public MovimentacaoFinanceira(Long id, String descricao, Double valor) {
        this.id = id;
        this.descricao = descricao;
        this.valor = valor;
    }

    public String getDescricao() {
        return descricao;
    }

    public Double getValor() {
        return valor;
    }

    public Date getData() {
        return data;
    }

    public Date getDataSystem() {
        return data;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public void printLog() {
        Log.d("LOG_MOVFIN", this.id + " - " + this.descricao +
                " - " + this.valor + " - " + Helper.formatDateToString(this.data));
    }
}
