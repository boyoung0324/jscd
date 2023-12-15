package com.jscd.app.order.dto;

import java.sql.Date;

public class CompanyInfoDTO {
    private int slrNo;
    private String slrName;
    private String slrNum;
    private String actName;
    private String actNum;
    private String slrAddr;
    private String companyName;
    private String indst;
    private String kind;
    private String slrPhone;
    private Date regDate;
    private int firstIdNo;
    private Date modifyDate;
    private int lastIdNo;
    private String etc;

    public CompanyInfoDTO(){}

    public CompanyInfoDTO(int slrNo, String slrName, String slrNum, String actName, String actNum, String slrAddr, String companyName, String indst, String kind, String slrPhone, Date regDate, int firstIdNo, Date modifyDate, int lastIdNo, String etc) {
        this.slrNo = slrNo;
        this.slrName = slrName;
        this.slrNum = slrNum;
        this.actName = actName;
        this.actNum = actNum;
        this.slrAddr = slrAddr;
        this.companyName = companyName;
        this.indst = indst;
        this.kind = kind;
        this.slrPhone = slrPhone;
        this.regDate = regDate;
        this.firstIdNo = firstIdNo;
        this.modifyDate = modifyDate;
        this.lastIdNo = lastIdNo;
        this.etc = etc;
    }

    public int getSlrNo() {
        return slrNo;
    }

    public void setSlrNo(int slrNo) {
        this.slrNo = slrNo;
    }

    public String getSlrName() {
        return slrName;
    }

    public void setSlrName(String slrName) {
        this.slrName = slrName;
    }

    public String getSlrNum() {
        return slrNum;
    }

    public void setSlrNum(String slrNum) {
        this.slrNum = slrNum;
    }

    public String getActName() {
        return actName;
    }

    public void setActName(String actName) {
        this.actName = actName;
    }

    public String getActNum() {
        return actNum;
    }

    public void setActNum(String actNum) {
        this.actNum = actNum;
    }

    public String getSlrAddr() {
        return slrAddr;
    }

    public void setSlrAddr(String slrAddr) {
        this.slrAddr = slrAddr;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getIndst() {
        return indst;
    }

    public void setIndst(String indst) {
        this.indst = indst;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getSlrPhone() {
        return slrPhone;
    }

    public void setSlrPhone(String slrPhone) {
        this.slrPhone = slrPhone;
    }

    public Date getRegDate() {
        return regDate;
    }

    public void setRegDate(Date regDate) {
        this.regDate = regDate;
    }

    public int getFirstIdNo() {
        return firstIdNo;
    }

    public void setFirstIdNo(int firstIdNo) {
        this.firstIdNo = firstIdNo;
    }

    public Date getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }

    public int getLastIdNo() {
        return lastIdNo;
    }

    public void setLastIdNo(int lastIdNo) {
        this.lastIdNo = lastIdNo;
    }

    public String getEtc() {
        return etc;
    }

    public void setEtc(String etc) {
        this.etc = etc;
    }

    @Override
    public String toString() {
        return "CompanyInfoDTO{" +
                "slrNo=" + slrNo +
                ", slrName='" + slrName + '\'' +
                ", slrNum='" + slrNum + '\'' +
                ", actName='" + actName + '\'' +
                ", actNum='" + actNum + '\'' +
                ", slrAddr='" + slrAddr + '\'' +
                ", companyName='" + companyName + '\'' +
                ", indst='" + indst + '\'' +
                ", kind='" + kind + '\'' +
                ", slrPhone='" + slrPhone + '\'' +
                ", regDate=" + regDate +
                ", firstIdNo=" + firstIdNo +
                ", modifyDate=" + modifyDate +
                ", lastIdNo=" + lastIdNo +
                ", etc='" + etc + '\'' +
                '}';
    }
}
