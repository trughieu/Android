package com.example.sic.model;

import vn.mobileid.tse.model.connector.plugin.CertificateAuthority;

public class CertificateCA extends CertificateAuthority {

    String name;

    public CertificateCA() {
    }

    public CertificateCA(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
