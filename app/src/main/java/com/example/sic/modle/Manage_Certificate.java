package com.example.sic.modle;

public class Manage_Certificate {
    public String CNSubjectDN;
    public String CNIssuerDN;
    public String ValidTo;
    public String credentialID;

    public String getCredentialID() {
        return credentialID;
    }

    public void setCredentialID(String credentialID) {
        this.credentialID = credentialID;
    }

    public String getCNIssuerDN() {
        return CNIssuerDN;
    }

    public void setCNIssuerDN(String CNIssuerDN) {
        this.CNIssuerDN = CNIssuerDN;
    }

    public String getValidTo() {
        return ValidTo;
    }

    public void setValidTo(String validTo) {
        this.ValidTo = validTo;
    }

    public String getCNSubjectDN() {
        return CNSubjectDN;
    }

    public void setCNSubjectDN(String CNSubjectDN) {
        this.CNSubjectDN = CNSubjectDN;
    }
}
