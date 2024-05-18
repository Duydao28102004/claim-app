package com.example.claimapp;

/**
 * @author <Dao Bao Duy - s3978826>
 *     Adapted from: chatGPT, w3schools
 */

import java.util.ArrayList;
import java.util.Date;

public class Claim {
    private String id;
    private Date claimDate;
    private String insuredPerson;
    private String cardNumber;
    private Date examDate;
    private ArrayList<String> documents;
    private double claimAmount;
    private String status;
    private String bankingInfo;

    public Claim() {
        id = "default";
        claimDate = new Date();
        insuredPerson = "default";
        cardNumber = "default";
        examDate = new Date();
        documents = null;
        claimAmount = 0.0;
        status = "default";
        bankingInfo = "default";
    }

    public Claim(String id, Date claimDate, String insuredPerson, String cardNumber, Date examDate, ArrayList<String> documents, double claimAmount, String status, String bankingInfo) {
        this.id = id;
        this.claimDate = claimDate;
        this.insuredPerson = insuredPerson;
        this.cardNumber = cardNumber;
        this.examDate = examDate;
        this.documents = documents;
        this.claimAmount = claimAmount;
        this.status = status;
        this.bankingInfo = bankingInfo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getClaimDate() {
        return claimDate;
    }

    public void setClaimDate(Date claimDate) {
        this.claimDate = claimDate;
    }

    public String getInsuredPerson() {
        return insuredPerson;
    }

    public void setInsuredPerson(String insuredPerson) {
        this.insuredPerson = insuredPerson;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public Date getExamDate() {
        return examDate;
    }

    public void setExamDate(Date examDate) {
        this.examDate = examDate;
    }

    public ArrayList<String> getDocuments() {
        return documents;
    }

    public void setDocuments(ArrayList<String> documents) {
        this.documents = documents;
    }

    public double getClaimAmount() {
        return claimAmount;
    }

    public void setClaimAmount(double claimAmount) {
        this.claimAmount = claimAmount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getBankingInfo() {
        return bankingInfo;
    }

    public void setBankingInfo(String bankingInfo) {
        this.bankingInfo = bankingInfo;
    }

    @Override
    public String toString() {
        return
                "id='" + id + '\'' +
                ", claimDate=" + claimDate +
                ", insuredPerson:" + insuredPerson +
                ", cardNumber:" + cardNumber + '\n' +
                "examDate:" + examDate +
                ", documents: " + documents +
                ", claimAmount: " + claimAmount + "$" + "\n" +
                "status: " + status + '\n' +
                "bankingInfo='" + bankingInfo ;
    }
}
