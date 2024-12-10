package com.esocial.script.generator.model;

import com.esocial.script.generator.util.CdataAdapter;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlRootElement(name = "dbscript")
@XmlType(propOrder = {"numos", "objeto", "tipo", "ordem", "tabela", "objetivo", "executar", "autor", "orcl", "mssql"})
public class DbScript {

    private String numos;
    private String objeto;
    private String tipo;
    private Ordem ordem;
    private String tabela;
    private String objetivo;
    private String executar;
    private String autor;
    private String orcl;    // Propriedade orcl
    private String mssql;   // Propriedade mssql

    // Getters e Setters

    @XmlAttribute
    public String getNumos() {
        return numos;
    }

    public void setNumos(String numos) {
        this.numos = numos;
    }

    @XmlElement
    public String getObjeto() {
        return objeto;
    }

    public void setObjeto(String objeto) {
        this.objeto = objeto;
    }

    @XmlElement
    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    @XmlElement(nillable = false)
    public Ordem getOrdem() {
        return ordem;
    }

    public void setOrdem(Ordem ordem) {
        this.ordem = ordem;
    }

    @XmlElement
    public String getTabela() {
        return tabela;
    }

    public void setTabela(String tabela) {
        this.tabela = tabela;
    }

    @XmlElement
    public String getObjetivo() {
        return objetivo;
    }

    public void setObjetivo(String objetivo) {
        this.objetivo = objetivo;
    }

    @XmlElement
    public String getExecutar() {
        return executar;
    }

    public void setExecutar(String executar) {
        this.executar = executar;
    }

    @XmlElement
    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    @XmlElement
    @XmlJavaTypeAdapter(CdataAdapter.class)
    public String getOrcl() {
        return orcl;
    }

    public void setOrcl(String orcl) {
        this.orcl = orcl;
    }

    @XmlElement
    @XmlJavaTypeAdapter(CdataAdapter.class)
    public String getMssql() {
        return mssql;
    }

    public void setMssql(String mssql) {
        this.mssql = mssql;
    }
}