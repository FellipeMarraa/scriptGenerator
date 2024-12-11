package com.esocial.script.generator.model;

import java.util.Map;

public class ViewData {

    private String tabelaPai;
    private String nomeTabela;
    private String nomeView;
    private String nomeEvento;
    private String nomeJson;
    private Map<String, String> tiposCampos; // Adicionado: Mapa de tipos dos campos

    // Getters e Setters
    public String getTabelaPai() {
        return tabelaPai;
    }

    public void setTabelaPai(String tabelaPai) {
        this.tabelaPai = tabelaPai;
    }

    public String getNomeTabela() {
        return nomeTabela;
    }

    public void setNomeTabela(String nomeTabela) {
        this.nomeTabela = nomeTabela;
    }

    public String getNomeView() {
        return nomeView;
    }

    public void setNomeView(String nomeView) {
        this.nomeView = nomeView;
    }

    public String getNomeEvento() {
        return nomeEvento;
    }

    public void setNomeEvento(String nomeEvento) {
        this.nomeEvento = nomeEvento;
    }

    public String getNomeJson() {
        return nomeJson;
    }

    public void setNomeJson(String nomeJson) {
        this.nomeJson = nomeJson;
    }

    public Map<String, String> getTiposCampos() {
        return tiposCampos;
    }

    public void setTiposCampos(Map<String, String> tiposCampos) {
        this.tiposCampos = tiposCampos;
    }
}