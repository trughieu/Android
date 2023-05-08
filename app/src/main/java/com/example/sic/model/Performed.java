package com.example.sic.model;

import java.io.Serializable;

public class Performed implements Serializable {
    String Operating;
    String IP;
    String Browser;
    String RP;
    String credentialID;
    String message;
    String messageCaption;
    String submitFrom;
    String OS;
    String MAC;
    String COMPUTER_NAME;
    String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getOS() {
        return OS;
    }

    public void setOS(String OS) {
        this.OS = OS;
    }

    public String getMAC() {
        return MAC;
    }

    public void setMAC(String MAC) {
        this.MAC = MAC;
    }

    public String getCOMPUTER_NAME() {
        return COMPUTER_NAME;
    }

    public void setCOMPUTER_NAME(String COMPUTER_NAME) {
        this.COMPUTER_NAME = COMPUTER_NAME;
    }

    public String getSubmitFrom() {
        return submitFrom;
    }

    public void setSubmitFrom(String submitFrom) {
        this.submitFrom = submitFrom;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessageCaption() {
        return messageCaption;
    }

    public void setMessageCaption(String messageCaption) {
        this.messageCaption = messageCaption;
    }

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
