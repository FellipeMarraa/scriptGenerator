package com.esocial.script.generator.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.xml.bind.annotation.XmlElement;

import java.util.ArrayList;
import java.util.List;

public class Ordem {

    private List<String> apos = new ArrayList<>();

    @JsonCreator
    public Ordem(@JsonProperty("apos") List<String> apos) {
        this.apos = apos;
    }

    @XmlElement
    public List<String> getApos() {
        return apos;
    }

    public void setApos(List<String> apos) {
        this.apos = apos;
    }
}
