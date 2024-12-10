package com.esocial.script.generator.model;

import jakarta.xml.bind.annotation.XmlElement;

import java.util.ArrayList;
import java.util.List;

public class Ordem {

    private List<String> apos = new ArrayList<>();

    @XmlElement
    public List<String> getApos() {
        return apos;
    }

    public void setApos(List<String> apos) {
        this.apos = apos;
    }
}
