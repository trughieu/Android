package com.example.sic.modle;

public class Performed {
    String Operating;
    String IP;
    String Browser;
    String RP;
    String credentialID;

    public String getCredentialID() {
        return credentialID;
    }

    public void setCredentialID(String credentialID) {
        this.credentialID = credentialID;
    }

    public String getOperating() {
        return Operating;
    }

    public void setOperating(String operating) {
        Operating = operating;
    }

    public String getIP() {
        return IP;
    }

    public void setIP(String IP) {
        this.IP = IP;
    }

    public String getBrowser() {
        return Browser;
    }

    public void setBrowser(String browser) {
        Browser = browser;
    }

    public String getRP() {
        return RP;
    }

    public void setRP(String RP) {
        this.RP = RP;
    }
}
