package com.esocial.script.generator.model;

import java.util.List;
import java.util.Map;

public class NewScriptDTO {

    private String numos;
    private String objeto;
    private String tipo;
    private Ordem ordem;
    private String tabela;
    private String objetivo;
    private String executar;
    private String autor;
    private String orcl;
    private String mssql;

    private String tabelaPai;
    private String nomeTabela;
    private String nomeView;
    private List<String> campos;
    private String nomeEvento;
    private String nomeJson;
    private Map<String, String> tiposCampos;

    public String getNumos() {
        return numos;
    }

    public void setNumos(String numos) {
        this.numos = numos;
    }

    public String getObjeto() {
        return objeto;
    }

    public void setObjeto(String objeto) {
        this.objeto = objeto;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Ordem getOrdem() {
        return ordem;
    }

    public void setOrdem(Ordem ordem) {
        this.ordem = ordem;
    }

    public String getTabela() {
        return tabela;
    }

    public void setTabela(String tabela) {
        this.tabela = tabela;
    }

    public String getObjetivo() {
        return objetivo;
    }

    public void setObjetivo(String objetivo) {
        this.objetivo = objetivo;
    }

    public String getExecutar() {
        return executar;
    }

    public void setExecutar(String executar) {
        this.executar = executar;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getOrcl() {
        return orcl;
    }

    public void setOrcl(String orcl) {
        this.orcl = orcl;
    }

    public String getMssql() {
        return mssql;
    }

    public void setMssql(String mssql) {
        this.mssql = mssql;
    }

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

    public List<String> getCampos() {
        return campos;
    }

    public void setCampos(List<String> campos) {
        this.campos = campos;
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
