package br.com.quaddro.quaddro100h.lab04.domain.model;

public enum UF {
    ESCOLHA("..."),
    AC, AL, AP, AM, BA, CE, DF, ES, GO,
    MA, MT, MS, MG, PA, PB, PR, PE, PI,
    RJ, RN, RS, RO, RR, SC, SP, SE, TO;

    private String descricao;

    UF() {
        this.descricao = super.toString();
    }

    UF(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public String toString() {
        return descricao;
    }
}
