package com.rampit.rask3.dailydiary;

//Updated on 25/01/2022 by RAMPIT
//used has POJO class for print_pdf


class Daily_POJO {



    public String getfrom_date() {
        return From_date;
    }

    public void setfrom_date(String from_date) {
        this.From_date = from_date;
    }

    public String getto_date() {
        return To_date;
    }

    public void setto_date(String to_date) {
        this.To_date = to_date;
    }

    public String gettotal_customers() {
        return Total_customers;
    }

    public void settotal_customers(String total_customers) {
        Total_customers = total_customers;
    }

    public String getpaid_customers() {
        return Paid_customers;
    }

    public void setpaid_customers(String paid_customers) {
        Paid_customers = paid_customers;
    }


    public String getbalance_customers() {
        return Balance_customers;
    }

    public void setbalance_customers(String balance_customers) {
        Balance_customers = balance_customers;
    }

    public String getbefore_balance() {
        return Before_balance;
    }

    public void setbefore_balance(String before_balance) {
        Before_balance = before_balance;
    }

    public String gettoday_collection() {
        return Today_collection;
    }

    public void settoday_collection(String today_collection) {
        Today_collection = today_collection;
    }
    public String getnip_collection() {
        return NIP_collection;
    }

    public void setnip_collection(String nip_collection) {
        NIP_collection = nip_collection;
    }
    public String getnipnip_collection() {
        return NIPNIP_collection;
    }

    public void setnipnip_collection(String nipnip_collection) {
        NIPNIP_collection = nipnip_collection;
    }
    public String gettotal_collection() {
        return Total_collection;
    }

    public  void settotal_collection(String total_collection) {
        Total_collection = total_collection;
    }
    public String getdebit() {
        return Debit;
    }

    public void setdebit(String debit) {
        Debit = debit;
    }
    public String getdocument_charge() {
        return Document_charge;
    }

    public void setdocument_charge(String document_charge) {
        Document_charge = document_charge;
    }
    public String getinterest() {
        return Interest;
    }

    public  void setinterest(String interest) {
        Interest = interest;
    }
    public String getother() {
        return Other;
    }

    public void setother(String other) {
        Other = other;
    }
    public String gethigh_amount() {
        return High_amount;
    }

    public void sethigh_amount(String high_amount) {
        High_amount = high_amount;
    }
    public String getlow_amount() {
        return Low_amount;
    }

    public void setlow_amount(String low_amount) {
        Low_amount = low_amount;
    }
    public String getexpense() {
        return Expense;
    }

    public void setexpense(String expense) {
        Expense = expense;
    }
    public String gettotal_balance() {
        return Total_balance;
    }

    public void settotal_balance(String total_balance) {
        Total_balance = total_balance;
    }
    public String getnew_credit() {
        return New_credit;
    }

    public void setnew_credit(String new_credit) {
        New_credit = new_credit;
    }

    public String getnip_balance() {
        return NIP_balance;
    }

    public void setnip_balance(String nip_balance) {
        NIP_balance = nip_balance;
    }

    public String getnipnip_balance() {
        return NIPNIP_balance;
    }

    public void setnipnip_balance(String nipnip_balance) {
        NIPNIP_balance = nipnip_balance;
    }

    public String getcurrent_balance() {
        return Current_balance;
    }

    public void setcurrent_balance(String current_balance) {
        Current_balance = current_balance;
    }

    public String getmoved_nip() {
        return Moved_NIP;
    }

    public void setmoved_nip(String moved_nip) {
        Moved_NIP = moved_nip;
    }

    public String getmoved_nipnip() {
        return Moved_NIPNIP;
    }

    public void setmoved_nipnip(String moved_nipnip) {
        Moved_NIPNIP = moved_nipnip;
    }

    public String getdiscount() {
        return Discount;
    }

    public void setdiscount(String discount) {
        Discount = discount;
    }

    public String getcollection_balance() {
        return Collection_balance;
    }

    public void setcollection_balance(String collection_balance) {
        Collection_balance = collection_balance;
    }
    public String getclosed_collection() {
        return Closed_collection;
    }

    public void setclosed_collection(String closed_collection) {
        Closed_collection = closed_collection;
    }

    String  From_date
            ,To_date
            ,Total_customers
            ,Paid_customers
            ,Balance_customers
            ,Expense
            ,Interest
            ,Other
            ,Debit
        ,Before_balance
        ,Today_collection
        ,Low_amount
        ,NIP_collection
        ,NIPNIP_collection
        ,Total_collection
        ,Document_charge
        ,High_amount
            ,Total_balance
            ,New_credit
            ,NIP_balance
            ,NIPNIP_balance
            ,Current_balance
            ,Moved_NIP
            ,Moved_NIPNIP
            ,Discount
            ,Collection_balance
            ,Closed_collection;
}