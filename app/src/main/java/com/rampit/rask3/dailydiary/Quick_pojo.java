package com.rampit.rask3.dailydiary;

//Updated on 25/01/2022 by RAMPIT
//POJO class used for Quick bulk update page

public class Quick_pojo {



    public String getdebit_id() {
        return debit_id;
    }

    public void setdebit_id(String debit_id) {
        this.debit_id = debit_id;
    }

    public String getuid() {
        return uid;
    }

    public void setuid(String uid) {
        this.uid = uid;
    }

    public String getcid() {
        return cid;
    }

    public void setcid(String cid) {
        this.cid = cid;
    }

    public String gettotaldays() {
        return totaldays;
    }

    public void settotaldays(String totaldays) {
        this.totaldays = totaldays;
    }


    public String getinstallment() {
        return installment;
    }

    public void setinstallment(String installment) {
        this.installment = installment;
    }

    public String getddbt() {
        return ddbt;
    }

    public void setddbt(String ddbt) {
        this.ddbt = ddbt;
    }

    public String gettyp() {
        return typ;
    }

    public void settyp(String typ) {
        this.typ = typ;
    }
    public String getorderrr() {
        return orderrr;
    }

    public void setorderrr(String orderrr) {
        this.orderrr = orderrr;
    }
    public String getcollect10() {
        return collect10;
    }

    public void setcollect10(String collect10) {
        this.collect10 = collect10;
    }
    public String getName() {
        return Name;
    }

    public  void setName(String Name) {
        this.Name = Name;
    }


    String  debit_id
            ,uid
            ,cid
            ,totaldays
            ,installment
            ,ddbt
            ,typ
            ,orderrr
            ,collect10
            ,Name;
}