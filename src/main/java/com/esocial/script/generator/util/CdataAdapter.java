package com.esocial.script.generator.util;

import jakarta.xml.bind.annotation.adapters.XmlAdapter;

public class CdataAdapter extends XmlAdapter<String, String> {

    @Override
    public String marshal(String v) throws Exception {
        if (v != null) {
            return "<![CDATA[" + v + "]]>";
        }
        return null;
    }

    @Override
    public String unmarshal(String v) throws Exception {
        return v;
    }
}