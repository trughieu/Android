package com.example.sic.modle;

public class Message {

    String messageCaption;
    String scaIdentity;
    String createdDt;
    String transactionId;

    public Message() {
    }

    public Message(String messageCaption, String scaIdentity, String createdDt) {
        this.messageCaption = messageCaption;
        this.createdDt = createdDt;
        this.scaIdentity = scaIdentity;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getMessageCaption() {
        return messageCaption;
    }

    public void setMessageCaption(String messageCaption) {
        this.messageCaption = messageCaption;
    }

    public String getScaIdentity() {
        return scaIdentity;
    }

    public void setScaIdentity(String scaIdentity) {
        this.scaIdentity = scaIdentity;
    }

    public String getCreatedDt() {
        return createdDt;
    }

    public void setCreatedDt(String createdDt) {
        this.createdDt = createdDt;
    }

    @Override
    public String toString() {
        return "message{" +
                "tv_1='" + messageCaption + '\'' +
                ", tv_2='" + scaIdentity + '\'' +
                ", tv_3='" + createdDt + '\'' +
                '}';
    }
}
