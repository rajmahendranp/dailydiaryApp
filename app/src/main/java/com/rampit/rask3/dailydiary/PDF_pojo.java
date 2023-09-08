package com.rampit.rask3.dailydiary;


//Updated on 25/01/2022 by RAMPIT
//used has POJO class for Print_activity


class PDF_pojo {
    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public String getItem_price() {
        return item_price;
    }

    public void setItem_price(String item_price) {
        this.item_price = item_price;
    }

    public String getItem_URL() {
        return Item_URL;
    }

    public void setItem_URL(String item_URL) {
        Item_URL = item_URL;
    }

    public String getItem_type_code() {
        return Item_type_code;
    }

    public void setItem_type_code(String item_type_code) {
        Item_type_code = item_type_code;
    }


    public String getCreatedAt() {
        return CreatedAt;
    }

    public void setCreatedAt(String createdAt) {
        CreatedAt = createdAt;
    }

    public String getslno() {
        return Slno;
    }

    public void setslno(String slno) {
        Slno = slno;
    }

    public String getopendate() {
        return Opendate;
    }

    public void setopendate(String opendate) {
        Opendate = opendate;
    }
    public String getclosedate() {
        return Closedate;
    }

    public void setclosedate(String closedate) {
        Closedate = closedate;
    }

    public String getpaid() {
        return Paid;
    }

    public void setpaid(String paid) {
        Paid = paid;
    }
    public String getdebit() {
        return Debit;
    }

    public void setdebit(String debit) {
        Debit = debit;
    }
    public String getmissday() {
        return Missday;
    }

    public void setmissday(String missday) {
        Missday = missday;
    }
    public String getmissamo() {
        return Missamo;
    }

    public void setmissamo(String missamo) {
        Missamo = missamo;
    }
    public String getdays() {
        return Days;
    }

    public void setdays(String days) {
        Days = days;
    }
    public String getInstallement() {
        return Installement;
    }

    public void setInstallement(String installement) {
        Installement = installement;
    }

    String  item_name
            ,item_image
            ,item_price
            ,Item_URL
            ,Item_type_code
            ,WishCount
            ,Slno
            ,Opendate
            ,Closedate
            ,Paid
            ,Debit
            ,Missday
            ,Missamo
            ,Days
            ,Installement
            ,CreatedAt;
}