package com.example.sic.model;

import com.checkid.icao.utils.FaceMethod;

import java.io.Serializable;

public class ScanType implements Serializable {

    private final FaceMethod method;
    private final String cardNumber;
    private ScanType(FaceMethod method, String cardNumber) {
        this.method = method;
        this.cardNumber = cardNumber;
    }

    public FaceMethod getMethod() {
        return method;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public static final class Liveness extends ScanType {
        public Liveness() {
            super(FaceMethod.LIVENESS, null);
        }
    }

    public static final class KYC extends ScanType {
        private final String cardNo;

        public KYC(String cardNo) {
            super(FaceMethod.KYC, cardNo);
            this.cardNo = cardNo;
        }

        public String getCardNo() {
            return cardNo;
        }
    }

    public static final class Verify extends ScanType {
        private final String cardNo;

        public Verify(String cardNo) {
            super(FaceMethod.VERIFY, cardNo);
            this.cardNo = cardNo;
        }

        public String getCardNo() {
            return cardNo;
        }
    }
}
