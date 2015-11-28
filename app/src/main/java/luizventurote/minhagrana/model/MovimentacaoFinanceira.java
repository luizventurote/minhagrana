package luizventurote.minhagrana.model;

import java.util.Date;

public class MovimentacaoFinanceira {

    /**
     * Descrição da movimentação financeira
     */
    private String descricao;

    /**
     * Valor da movimentação
     */
    private Double valor;

    /**
     * Data da movimentação
     */
    private Date data;


    public MovimentacaoFinanceira(String descricao, Double valor) {
        this.descricao = descricao;
        this.valor = valor;
        this.data = new Date();
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
}
