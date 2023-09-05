package com.valorizeseusamigos.membros.model;

import com.google.firebase.database.DatabaseReference;
import com.valorizeseusamigos.membros.helper.FirebaseHelper;

public class Extrato {

    private String id;
    private String operacao;
    private long data;
    private double valor;
    private String tipo;

    public Extrato() {
        DatabaseReference extratoRef = FirebaseHelper.getDatabaseReference();
        setId(extratoRef.push().getKey());
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOperacao() {
        return operacao;
    }

    public void setOperacao(String operacao) {
        this.operacao = operacao;
    }

    public long getData() {
        return data;
    }

    public void setData(long data) {
        this.data = data;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
