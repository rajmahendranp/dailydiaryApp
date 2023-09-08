package com.rampit.rask3.dailydiary.SQLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.util.Log;

import com.rampit.rask3.dailydiary.Callback;
import com.rampit.rask3.dailydiary.FileUtils;
import com.rampit.rask3.dailydiary.NavigationActivity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static java.sql.Types.VARCHAR;

public class SQLiteHelper extends SQLiteOpenHelper {

    public static String DB = "LOGIN";
    public static  Integer current_version = 0 ;
    public static String TABLENAME = "REGISTER"; public static String USER = "USERNAME";public static String PASS = "PASSWORD";public static String EMAIL = "EMAIL";public static String CITY = "CITY";public static String PIN = "PINCODE";public static String PHONE = "MOBILE";public static String OTP = "OTP";

    public static String TABLENAME1 = "dd_main_info";    public static String CREATED = "created_date";public static String UPDATED = "updated_date";public static String ID = "id";public static String NAME = "dd_name";public static String LOCATION = "dd_location";public static String START = "starting_date";

    public static String TABLENAME2 = "dd_customers"    ;public static String CUORDER ="order_id";public static String CUNAME = "customer_name";public static String CUFATHER = "father_name";public static String CULOCATION = "location";
    public static String CULAND1 = "landmark_1";public static String CULAND2 = "landmark_2";public static String CUPHONE1 = "phone_1";public static String CUPHONE2 = "phone_2";
    public static String CURECOM = "recommended_by";public static String CURELAT = "relationship";public static String CUSTATUS = "debit_type";public static String CUCUID = "CID";
    public static String CUREPHO = "relation_phone";public static String CUINFO = "info";public static String CUTRACK = "tracking_id";public static String CUDEUP = "debit_type_updated";public static String CUTIME = "timing";

    public static String TABLENAME3 ="dd_tracking_type";public static String TID ="ID";public static String TNAME ="NAME";

    public static String TABLENAME4 ="dd_account_debit";public static String DID ="id";public static String DDATE ="debit_date";public static String DAMOUNT ="debit_amount";public static String DDCHARGE ="document_charge";public static String DINT ="interest";public static String DDAYS ="debit_days";
    public static String DCLOSE ="closeing_date";public static String DINST ="installment_amount";public static String DCUS ="customer_id";public static String DCRDATE ="created_date";public static String DUPDATE ="updated_date";public static String DACTIVE ="active_status";

    public static String TABLENAME5 = "dd_collection";public static String COLID = "id";public static String COLCUSID = "customer_id";public static String COLDEBID = "debit_id";public static String COLCUSNAME = "customer_name";public static String COLAMOUNT = "collection_amount";public static String COLOTHER = "other_fee";public static String COLDIS = "discount";public static String COLDATE = "collection_date";public static String COLCREATED ="created_date";public static String COLUPDATE ="updated_date";

    public static String TABLENAME6 ="dd_accounting_type";public static String ACID ="id";public static String ACCNAME ="acc_type_name";public static String ACCTY ="acc_type";public static String ACCCREATED="created_date";public static String ACCUPD ="updated_date";

    public static String TABLENAME7 ="dd_accounting";public static String ACD ="collection_id";public static String ACCTYID ="acc_type_id";public static String ACCAMO ="amount";public static String ACCNOTE="note";public static String ACCDATE="accounting_date";public static String ACCC= "created_date";public static String ACCUPDD ="updated_date";public static String ACCTRACK ="tracking_id";

    public static String TABLENAME8 ="dd_employee";public static String EID ="id";public static String ENAME ="name";public static String ELOC ="location";public static String EPHONE="phone";public static String EJOINDATE="joining_date";public static String EPAYMENT="payment_info";public static String ELEAVE= "leave_days";public static String ERESIGN ="resigning_date";public static String EREASON ="reason";public static String ECREATED ="created_date";public static String EUPDATE ="updated_date";

    public static String TABLENAME9 ="dd_employee_payment";public static String EPID ="id";public static String EPEID ="employee_id";public static String EPREASON ="reason";public static String EAMOUNT ="amount";public static String EACCTYPE ="acc_type_id";public static String EPTRACK ="tracking_id";public static String EPCREATED ="created_date";public static String EPUPDATE ="updated_date";

    public static String TABLE_NAME ="NAMES";public static String SLNO ="ID";public static String NAMEE ="NAME";public static String ORDER1 ="ORDERID";

    public static String TABLENAME10 ="dd_modules";public static String MODID ="ID";public static String MODNAM ="NAME";public static String MODCRE ="created_date";public static String MODUPD ="updated_date";

    public static String TABLENAME11 ="dd_privilege";public static String USRID ="ID";public static String USRMAID ="module_id ";public static String UID ="user_id ";public static String USRRRR ="add_privilege";public static String USRED ="edit_privilege";public static String USRDEL ="delete_privilege";public static String USRVIE ="view_privilege";public static String USRCRE ="created_date";public static String USRUPD ="updated_date";

    public static String TABLENAME12 = "dd_user";public static String USID ="ID";public static String USNAM ="username";public static String USPASS ="password";public static String USOTP ="otp";public static String USCR ="created_date";public static String USUPD ="updated_date";

    public static String TABLENAME14 = "dd_settings";public static String SEPHO ="phone_number";public static String SEIMEI ="IMEI";public static String SEPDFPASS ="pdf_password";
    public static String SEID ="ID";public static String SEVE ="version"; public static String NEWSDDD ="newwsss";public static String NEWSDD ="newwss";
    public static String NEWSD ="newws";public static String SEVE1 ="oldversion";public static String SELI ="license";public static String SEEXP ="expiry";
    public static String SEEMAIL ="email";public static String SETHEME ="theme";public static String SECC ="cc";public static String SEPASS ="password";
    public static String SELANG ="language";public static String SECR ="created_date";public static String SEUPD ="updated_date";

    public static String TABLENAME13 = "dd_tally";public static String TAID ="id";public static String TATWOT ="two_thousand";public static String TAONET ="one_thousand";public static String TAFIVEH ="five_hundred";public static String TATWOH ="two_hundred";public static String TAONEH ="one_hundred";
    public static String TAFIFTY ="fifty";public static String TATWENTY ="twenty";public static String TATEN ="ten";public static String TAFIVE ="five";public static String TACUS1 ="custom1";public static String TACUS2 ="custom2";public static String TACUS3 ="custom3";public static String TATRACK ="tracking_id";public static String TACR ="created_date";public static String TAUPD ="updated_date";
    public static String BACKPRI ="backup_privilege";
    public static String QUICK ="quick_bulkupdate";
    public static String CUPID = "previous_order_id";
    public static String CUPID1 = "order_id_new";

    public static String TABLENAME15 = "dd_license";public static String li_id ="ID";public static String license ="license";public static String li_name ="name";public static String LICR ="created_date";public static String LIUPD ="updated_date";
    public static String license_note ="note";public static String license_image ="image";

    public static String TABLENAME16 = "dd_delete";public static String DEID ="id";public static String DEDB ="debit_amount";public static String DEIN ="interest";public static String DEDOC ="document_charge";
    public static String DECO ="collection_amount";public static String DEDI ="discount";public static String DEOT ="other_fee";public static String DECR ="credit";public static String DEDE ="debit";public static String DECR1 ="created_date";public static String DEUPD ="updated_date";

    public static String TABLENAME17 = "dd_remaining";public static String REID ="id";public static String REAM ="amount";public static String REDE ="debit_id";public static String RECUN ="customer_name";public static String RECU ="customer_id";public static String RECID ="CID";
    public static String RECR1 ="created_date";public static String REUPD ="updated_date";

    public static String TABLENAME18 = "dd_drive";public static String dr_id ="id";public static String dr_file ="file_id";public static String dr_folder ="folder_id";


    public SQLiteHelper(Context context) {
        super(context, DB, null, 1);
    }
    @Override
    public void onOpen(SQLiteDatabase database) {
        super.onOpen(database);
        if(Build.VERSION.SDK_INT >= 28)
        {
            database.disableWriteAheadLogging();
        }
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
//        super.onOpen(db);
//        if(Build.VERSION.SDK_INT >= 28)
//        {
//            db.disableWriteAheadLogging();
//        }
        String CREATE_TABLE =("CREATE TABLE IF NOT EXISTS "+TABLENAME+"("+USER+" VARCHAR,"+PASS+" VARCHAR,"+EMAIL+" VARCHAR,"+CITY+" VARCHAR,"+PHONE+" VARCHAR,"+PIN+" VARCHAR,"+OTP+" VARCHAR)");
        db.execSQL(CREATE_TABLE);
        String CREATE_TABLE1 =("CREATE TABLE IF NOT EXISTS "+TABLENAME1+"("+ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+NAME+" VARCHAR,"+LOCATION+" VARCHAR,"+START+" VARCHAR,"+SEPHO+" INTEGER DEFAULT '0',"+CREATED+" DATE,"+UPDATED+" DATE)");
        db.execSQL(CREATE_TABLE1);
        String CREATE_TABLE3 = "CREATE TABLE IF NOT EXISTS "+TABLE_NAME+"("+SLNO+" INTEGER PRIMARY KEY AUTOINCREMENT,"+NAMEE+" VARCHAR,"+ORDER1+" VARCHAR"+")";
        db.execSQL(CREATE_TABLE3);
        String CREATE_TABLE4 =("CREATE TABLE IF NOT EXISTS "+TABLENAME2+"("+ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+CUPID1+" INTEGER DEFAULT '0',"+CUPID+" INTEGER DEFAULT '0',"+CUCUID+" INTEGER DEFAULT '0',"+CUTIME+" VARCHAR DEFAULT '0',"+CUORDER+" INTEGER DEFAULT '0',"+CUNAME+" VARCHAR,"+CUFATHER+" VARCHAR,"+CULOCATION+" VARCHAR,"+CULAND1+" VARCHAR,"+CULAND2+" VARCHAR,"+CUPHONE1+" VARCHAR,"+CUPHONE2+" VARCHAR,"+CURECOM+" VARCHAR,"+CURELAT+" VARCHAR,"+CUREPHO+" VARCHAR,"+CUINFO+" VARCHAR,"+CUTRACK+" VARCHAR,"+CUSTATUS+" INTEGER DEFAULT '0',"+CUDEUP+" DATE,"+CREATED+" DATE,"+UPDATED+" DATE)");
        db.execSQL(CREATE_TABLE4);
        String CREATE_TABLE5 = "CREATE TABLE IF NOT EXISTS "+TABLENAME3+"("+TID+" INTEGER ,"+TNAME+" VARCHAR"+")";
        db.execSQL(CREATE_TABLE5);
        String CREATE_TABLE6 =("CREATE TABLE IF NOT EXISTS "+TABLENAME4+"("+ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+DDATE+" DATE,"+DAMOUNT+" VARCHAR DEFAULT '0',"+DDCHARGE+" VARCHAR DEFAULT '0',"+DINT+" VARCHAR DEFAULT '0',"+DDAYS+" VARCHAR DEFAULT '0',"+DCLOSE+" VARCHAR,"+DINST+" VARCHAR DEFAULT '0',"+DCUS+" VARCHAR,"+DCRDATE+" DATE,"+DUPDATE+" DATE,"+DACTIVE+" INTEGER)");
        db.execSQL(CREATE_TABLE6);
        String CREATE_TABLE7 =("CREATE TABLE IF NOT EXISTS "+TABLENAME5+"("+ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+COLCUSID+" INTEGER,"+COLDEBID+" INTEGER,"+COLCUSNAME+" VARCHAR,"+COLAMOUNT+" VARCHAR DEFAULT '0',"+COLOTHER+" VARCHAR DEFAULT '0',"+COLDIS+" VARCHAR DEFAULT '0',"+COLDATE+" DATE,"+COLCREATED+" DATE,"+COLUPDATE+" DATE)");
        db.execSQL(CREATE_TABLE7);
        String CREATE_TABLE8 =("CREATE TABLE IF NOT EXISTS "+TABLENAME6+"("+ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+ACCNAME+" VARCHAR,"+ACCTY+" VARCHAR,"+ACCCREATED+" DATE,"+ACCUPD+" DATE)");
        db.execSQL(CREATE_TABLE8);
        String CREATE_TABLE9 =("CREATE TABLE IF NOT EXISTS "+TABLENAME7+"("+ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+ACCTYID+" VARCHAR,"+ACCAMO+" VARCHAR DEFAULT '0',"+ACCTRACK+" VARCHAR DEFAULT '0',"+ACCNOTE+" VARCHAR,"+ACD+" INTEGER,"+ACCDATE+" DATE,"+ACCC+" DATE,"+ACCUPDD+" DATE)");
        db.execSQL(CREATE_TABLE9);
        String CREATE_TABLE10 =("CREATE TABLE IF NOT EXISTS "+TABLENAME8+"("+EID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+ENAME+" VARCHAR,"+ELOC+" VARCHAR,"+EPHONE+" VARCHAR,"+EJOINDATE+" DATE,"+ELEAVE+" VARCHAR DEFAULT '0',"+ERESIGN+" VARCHAR,"+EREASON+" VARCHAR,"+ECREATED+" DATE,"+EUPDATE+" DATE)");
        db.execSQL(CREATE_TABLE10);
        String CREATE_TABLE11 =("CREATE TABLE IF NOT EXISTS "+TABLENAME9+"("+EPID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+EPEID+" VARCHAR DEFAULT '0',"+EPREASON+" VARCHAR,"+EAMOUNT+" VARCHAR DEFAULT '0',"+EACCTYPE+" VARCHAR DEFAULT '0',"+EPAYMENT+" VARCHAR DEFAULT '0',"+EPTRACK+" VARCHAR DEFAULT '0',"+EPCREATED+" DATE,"+EPUPDATE+" DATE)");
        db.execSQL(CREATE_TABLE11);
        String CREATE_TABLE12 =("CREATE TABLE IF NOT EXISTS "+TABLENAME10+"("+MODID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+MODNAM+" VARCHAR ,"+MODCRE+" DATE,"+MODUPD+" DATE)");
        db.execSQL(CREATE_TABLE12);
        String CREATE_TABLE13 =("CREATE TABLE IF NOT EXISTS "+TABLENAME11+"("+USRID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+USRMAID+" VARCHAR DEFAULT '0',"+UID+" VARCHAR DEFAULT '0',"+USRRRR+" VARCHAR DEFAULT '0', "+USRED+" VARCHAR DEFAULT '0',"+USRDEL+" VARCHAR DEFAULT '0',"+USRVIE+" VARCHAR DEFAULT '0',"+USRCRE+" DATE,"+USRUPD+" DATE)");
        db.execSQL(CREATE_TABLE13);
        String CREATE_TABLE14 =("CREATE TABLE IF NOT EXISTS "+TABLENAME12+"("+USID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+USNAM+" VARCHAR ,"+USPASS+" VARCHAR ,"+USOTP+" VARCHAR ,"+QUICK+" INTEGER DEFAULT '0',"+BACKPRI+" INTEGER DEFAULT '0' ,"+USCR+" DATE,"+USUPD+" DATE)");
        db.execSQL(CREATE_TABLE14);
        String CREATE_TABLE15 =("CREATE TABLE IF NOT EXISTS "+TABLENAME13+"("+TAID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+TATWOT+" INTEGER DEFAULT '0',"+TAONET+" INTEGER DEFAULT '0' ,"+TAFIVEH+" INTEGER DEFAULT '0' ,"+TATWOH+" INTEGER DEFAULT '0',"+TAONEH+" INTEGER DEFAULT '0',"+TAFIFTY+" INTEGER DEFAULT '0',"+TATWENTY+" INTEGER DEFAULT '0',"+TATEN+" INTEGER DEFAULT '0',"+TAFIVE+" INTEGER DEFAULT '0',"+TACUS1+" INTEGER DEFAULT '0',"+TACUS2+" INTEGER DEFAULT '0',"+TACUS3+" INTEGER DEFAULT '0',"+TATRACK+" INTEGER DEFAULT '0',"+USCR+" DATE,"+USUPD+" DATE)");
        db.execSQL(CREATE_TABLE15);
        String CREATE_TABLE16 =("CREATE TABLE IF NOT EXISTS "+TABLENAME14+"("+SEID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+SEIMEI+" INTEGER DEFAULT '0',"+SEPHO+" INTEGER DEFAULT '0',"+NEWSDDD+" INTEGER DEFAULT '0',"+SEVE+" INTEGER DEFAULT '0',"+SEVE1+" INTEGER DEFAULT '0',"+SELI+" VARCHAR ,"+SEEXP+" DATE ,"+SEPASS+" VARCHAR ,"+SEEMAIL+" VARCHAR ,"+SECC+" VARCHAR ,"+SELANG+" VARCHAR ,"+SETHEME+" INTEGER DEFAULT '0',"+SEPDFPASS+" VARCHAR ,"+SECR+" DATE,"+SEUPD+" DATE)");
        db.execSQL(CREATE_TABLE16);
        String CREATE_TABLE17 =("CREATE TABLE IF NOT EXISTS "+TABLENAME15+"("+li_id+" INTEGER PRIMARY KEY AUTOINCREMENT,"+SEIMEI+" INTEGER DEFAULT '0',"+li_name+" VARCHAR ,"+license+" VARCHAR ,"+license_note+" VARCHAR ,"+license_image+" BLOB  ,"+SECR+" DATE,"+SEUPD+" DATE)");
        db.execSQL(CREATE_TABLE17);
        String CREATE_TABLE18 =("CREATE TABLE IF NOT EXISTS "+TABLENAME16+"("+DEID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+CUTRACK+" VARCHAR,"+DEDB+" INTEGER DEFAULT '0',"+DEIN+" INTEGER DEFAULT '0',"+DEDOC+" INTEGER DEFAULT '0',"+DECO+" INTEGER DEFAULT '0',"+DEDI+" INTEGER DEFAULT '0',"+DEOT+" INTEGER DEFAULT '0',"+DECR+" INTEGER DEFAULT '0',"+DEDE+" INTEGER DEFAULT '0',"+DECR1+" DATE,"+DEUPD+" DATE)");
        db.execSQL(CREATE_TABLE18);
        String CREATE_TABLE19 =("CREATE TABLE IF NOT EXISTS "+TABLENAME17+"("+REID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+RECUN+" VARCHAR ,"+REAM+" INTEGER DEFAULT '0',"+RECU+" INTEGER DEFAULT '0',"+RECID+" INTEGER DEFAULT '0',"+REDE+" INTEGER DEFAULT '0',"+CUTRACK+" VARCHAR,"+RECR1+" DATE,"+REUPD+" DATE)");
        db.execSQL(CREATE_TABLE19);
        String CREATE_TABLE20 =("CREATE TABLE IF NOT EXISTS "+TABLENAME18+"("+dr_id+" INTEGER PRIMARY KEY AUTOINCREMENT,"+dr_file+" VARCHAR ,"+dr_folder+" VARCHAR ,"+SECR+" DATE,"+SEUPD+" DATE)");
        db.execSQL(CREATE_TABLE20);

        String SET_INSERT = "INSERT INTO " + TABLENAME14 + " (" + SELANG +  ") Values ('ta')";
        String SET_INSERT1 = "INSERT INTO " + TABLENAME14 + " (" + SEVE +  ") Values ('1')";
        String SET_INSERT2 = "INSERT INTO " + TABLENAME14 + " (" + SEVE1 +  ") Values ('0')";
        String ACC_INSERT = "INSERT INTO " + TABLENAME6 + " (" + ACCNAME +  "," + ACCTY +") Values ('investment','1')";
        String ACC_INSERT1 = "INSERT INTO " + TABLENAME6 + " (" + ACCNAME +  "," + ACCTY +") Values ('Finance','1')";
        String ACC_INSERT2 = "INSERT INTO " + TABLENAME6 + " (" + ACCNAME +  "," + ACCTY +") Values ('Chittu','1')";
        String ACC_INSERT3 = "INSERT INTO " + TABLENAME6 + " (" + ACCNAME +  "," + ACCTY +") Values ('Transfer_Hand','1')";
        String ACC_INSERT4 = "INSERT INTO " + TABLENAME6 + " (" + ACCNAME +  "," + ACCTY +") Values ('Anamattu','1')";
        String ACC_INSERT5 = "INSERT INTO " + TABLENAME6 + " (" + ACCNAME +  "," + ACCTY +") Values ('Other_Income','1')";
        String ACC_INSERT6 = "INSERT INTO " + TABLENAME6 + " (" + ACCNAME +  "," + ACCTY +") Values ('Employee_credit','1')";
        String ACC_INSERT7 = "INSERT INTO " + TABLENAME6 + " (" + ACCNAME +  "," + ACCTY +") Values ('High','1')";
        String ACC_INSERT8 = "INSERT INTO " + TABLENAME6 + " (" + ACCNAME +  "," + ACCTY +") Values ('Profit','1')";
        String ACC_INSERT9 = "INSERT INTO " + TABLENAME6 + " (" + ACCNAME +  "," + ACCTY +") Values ('Expense','2')";
        String ACC_INSERT10 = "INSERT INTO " + TABLENAME6 + " (" + ACCNAME +  "," + ACCTY +") Values ('Petrol','2')";
        String ACC_INSERT11 = "INSERT INTO " + TABLENAME6 + " (" + ACCNAME +  "," + ACCTY +") Values ('rent','2')";
        String ACC_INSERT12 = "INSERT INTO " + TABLENAME6 + " (" + ACCNAME +  "," + ACCTY +") Values ('Debit_Hand','2')";
        String ACC_INSERT13 = "INSERT INTO " + TABLENAME6 + " (" + ACCNAME +  "," + ACCTY +") Values ('Chittu','2')";
        String ACC_INSERT14 = "INSERT INTO " + TABLENAME6 + " (" + ACCNAME +  "," + ACCTY +") Values ('Vehicle_Expense','2')";
        String ACC_INSERT15 = "INSERT INTO " + TABLENAME6 + " (" + ACCNAME +  "," + ACCTY +") Values ('Other_Expense','2')";
        String ACC_INSERT16 = "INSERT INTO " + TABLENAME6 + " (" + ACCNAME +  "," + ACCTY +") Values ('Donation_Expense','2')";
        String ACC_INSERT17 = "INSERT INTO " + TABLENAME6 + " (" + ACCNAME +  "," + ACCTY +") Values ('Employee_Expense','2')";
        String ACC_INSERT18 = "INSERT INTO " + TABLENAME6 + " (" + ACCNAME +  "," + ACCTY +") Values ('Low','2')";
        String ACC_INSERT19 = "INSERT INTO " + TABLENAME6 + " (" + ACCNAME +  "," + ACCTY +") Values ('Lose','2')";
        String TID_INSERT = "INSERT INTO " + TABLENAME3 + " (" + TNAME +  "," + TID +") Values ('morning','1')";
        String TID_INSERT1 = "INSERT INTO " + TABLENAME3 + " (" + TNAME +  "," + TID +") Values ('evening','2')";
        String MOD_INSERT1 = "INSERT INTO " + TABLENAME10 + " (" + MODNAM +  ") Values ('company')";
        String MOD_INSERT2 = "INSERT INTO " + TABLENAME10 + " (" + MODNAM +  ") Values ('user')";
        String MOD_INSERT3 = "INSERT INTO " + TABLENAME10 + " (" + MODNAM +  ") Values ('debit')";
        String MOD_INSERT4 = "INSERT INTO " + TABLENAME10 + " (" + MODNAM +  ") Values ('collection')";
        String MOD_INSERT5 = "INSERT INTO " + TABLENAME10 + " (" + MODNAM +  ") Values ('account')";
        String MOD_INSERT6 = "INSERT INTO " + TABLENAME10 + " (" + MODNAM +  ") Values ('reports')";
        String MOD_INSERT7 = "INSERT INTO " + TABLENAME10 + " (" + MODNAM +  ") Values ('customer')";
        String MOD_INSERT8 = "INSERT INTO " + TABLENAME10 + " (" + MODNAM +  ") Values ('settings')";
        String MOD_INSERT9 = "INSERT INTO " + TABLENAME10 + " (" + MODNAM +  ") Values ('tally')";
        String MOD_INSERT10 = "INSERT INTO " + TABLENAME10 + " (" + MODNAM +  ") Values ('employee_details')";
        String MOD_INSERT11 = "INSERT INTO " + TABLENAME10 + " (" + MODNAM +  ") Values ('today_report')";
        String MOD_INSERT12 = "INSERT INTO " + TABLENAME10 + " (" + MODNAM +  ") Values ('current_account')";
        String MOD_INSERT13 = "INSERT INTO " + TABLENAME10 + " (" + MODNAM +  ") Values ('closed_account')";
        String MOD_INSERT14 = "INSERT INTO " + TABLENAME10 + " (" + MODNAM +  ") Values ('today_account')";
        String MOD_INSERT15 = "INSERT INTO " + TABLENAME10 + " (" + MODNAM +  ") Values ('current_incompleted_account')";
        String MOD_INSERT16 = "INSERT INTO " + TABLENAME10 + " (" + MODNAM +  ") Values ('NIP_account')";
        String MOD_INSERT17 = "INSERT INTO " + TABLENAME10 + " (" + MODNAM +  ") Values ('NIPNIP_account')";
        String MOD_INSERT18 = "INSERT INTO " + TABLENAME10 + " (" + MODNAM +  ") Values ('total_customer')";
        String MOD_INSERT19 = "INSERT INTO " + TABLENAME10 + " (" + MODNAM +  ") Values ('final_report')";
        String MOD_INSERT20 = "INSERT INTO " + TABLENAME10 + " (" + MODNAM +  ") Values ('print')";
        String MOD_INSERT21 = "INSERT INTO " + TABLENAME10 + " (" + MODNAM +  ") Values ('Bulk Update')";
        String MOD_INSERT22 = "INSERT INTO " + TABLENAME10 + " (" + MODNAM +  ") Values ('Single Collection')";
        String MOD_INSERT23 = "INSERT INTO " + TABLENAME10 + " (" + MODNAM +  ") Values ('Quick Bulk update')";
//        String MOD_INSERT21 = "INSERT INTO " + TABLENAME10 + " (" + MODNAM +  ") Values ('Quick Bulkupdate')";
        String yesterdate,dateString;
        try {
            Calendar c = Calendar.getInstance();
            SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
            dateString = df.format(c.getTime());
            Date debit = df.parse(dateString);
            Calendar cal = Calendar.getInstance();
            cal.setTime(debit);
            cal.add(Calendar.DATE,2);
            yesterdate = df.format(cal.getTime());
            ContentValues cv = new ContentValues();
            cv.put(SEEXP, yesterdate);
            cv.put(SEVE,20);
            cv.put(SEVE1,20);
            db.insert(TABLENAME14,null,cv);
//            db.update(TABLENAME14,  cv,"ID = ?",new String[]{"1"});
            Log.d("date",yesterdate);
//            String SET_INSERT = "INSERT INTO " + TABLENAME14 + " (" + SEEXP +  ") Values ()";
//            db.execSQL(SET_INSERT);
        }
        catch (ParseException e) {
            e.printStackTrace();
        }
//        db.execSQL(SET_INSERT);
//        db.execSQL(SET_INSERT1);
//        db.execSQL(SET_INSERT2);
        db.execSQL(TID_INSERT);
        db.execSQL(TID_INSERT1);
        db.execSQL(ACC_INSERT);
        db.execSQL(ACC_INSERT1);
        db.execSQL(ACC_INSERT2);
        db.execSQL(ACC_INSERT3);
        db.execSQL(ACC_INSERT4);
        db.execSQL(ACC_INSERT5);
        db.execSQL(ACC_INSERT6);
        db.execSQL(ACC_INSERT7);
        db.execSQL(ACC_INSERT8);
        db.execSQL(ACC_INSERT9);
        db.execSQL(ACC_INSERT10);
        db.execSQL(ACC_INSERT11);
        db.execSQL(ACC_INSERT12);
        db.execSQL(ACC_INSERT13);
        db.execSQL(ACC_INSERT14);
        db.execSQL(ACC_INSERT15);
        db.execSQL(ACC_INSERT16);
        db.execSQL(ACC_INSERT17);
        db.execSQL(ACC_INSERT18);
        db.execSQL(ACC_INSERT19);
        db.execSQL(MOD_INSERT1);
        db.execSQL(MOD_INSERT2);
        db.execSQL(MOD_INSERT3);
        db.execSQL(MOD_INSERT4);
        db.execSQL(MOD_INSERT5);
        db.execSQL(MOD_INSERT6);
        db.execSQL(MOD_INSERT7);
        db.execSQL(MOD_INSERT8);
        db.execSQL(MOD_INSERT9);
        db.execSQL(MOD_INSERT10);
        db.execSQL(MOD_INSERT11);
        db.execSQL(MOD_INSERT12);
        db.execSQL(MOD_INSERT13);
        db.execSQL(MOD_INSERT14);
        db.execSQL(MOD_INSERT15);
        db.execSQL(MOD_INSERT16);
        db.execSQL(MOD_INSERT17);
        db.execSQL(MOD_INSERT18);
        db.execSQL(MOD_INSERT19);
        db.execSQL(MOD_INSERT20);
        db.execSQL(MOD_INSERT21);
        db.execSQL(MOD_INSERT22);
        db.execSQL(MOD_INSERT23);
    }
    private static final String DATABASE_ALTER_TEAM_1 = "ALTER TABLE "
            + TABLENAME14 + " ADD COLUMN " + NEWSD + " INTEGER DEFAULT 0;";

    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
//        Log.i("DB", "updating database from v" + oldVersion + " to v" + newVersion);
//        for (int upgradeTo = oldVersion + 1; upgradeTo <= newVersion; upgradeTo++) {
//            Log.d("DB", "incremental database update from v" + (upgradeTo - 1) + " to v" + upgradeTo);
//            switch (newVersion) {
//                case 1:
//                    onCreate(database);
//                    break;
//                case 2:
        if(newVersion < 7){
            newVersion = 10;
        }
        newVersion = 20 ;
        Log.d("op0909","op09i09");
        if(oldVersion <= newVersion) {
            current_version = 20 ;
            for(int i = oldVersion;i<= newVersion;i++){
                Log.d("opop21",String.valueOf(oldVersion)+","+String.valueOf(newVersion));
                Log.d("op0909","op09i09");
                if(i == 6){
                    Log.d("opop12",String.valueOf(i));
                    update(database);
//                    update3(database);
                    ContentValues cv = new ContentValues();
//                cv.put(SEEXP, yesterdate);
                    cv.put(SEVE,7);
                    cv.put(SEVE1,7);
                    database.update(TABLENAME14,  cv,"ID = ?",new String[]{"1"});
                }
                if(i == 7){
                    Log.d("opop12",String.valueOf(i));
                    update1(database);
                    ContentValues cv = new ContentValues();
//                cv.put(SEEXP, yesterdate);
                    cv.put(SEVE,8);
                    cv.put(SEVE1,8);
                    database.update(TABLENAME14,  cv,"ID = ?",new String[]{"1"});
                }
                if(i == 8){
                    Log.d("opop12",String.valueOf(i));
                    update2(database);
                    ContentValues cv = new ContentValues();
//                cv.put(SEEXP, yesterdate);
                    cv.put(SEVE,9);
                    cv.put(SEVE1,9);
                    database.update(TABLENAME14,  cv,"ID = ?",new String[]{"1"});
                }
                if(i == 9){
                    Log.d("opop12",String.valueOf(i));
                    update3(database);
                    ContentValues cv = new ContentValues();
//                cv.put(SEEXP, yesterdate);
                    cv.put(SEVE,10);
                    cv.put(SEVE1,10);
                    database.update(TABLENAME14,  cv,"ID = ?",new String[]{"1"});
                }
                if(i == 10){
                    Log.d("opop12",String.valueOf(i));
                    update4(database);
                    ContentValues cv = new ContentValues();
//                cv.put(SEEXP, yesterdate);
                    cv.put(SEVE,11);
                    cv.put(SEVE1,11);
                    database.update(TABLENAME14,  cv,"ID = ?",new String[]{"1"});
                }
                if(i == 11){
                    Log.d("opop12",String.valueOf(i));
                    update5(database);
                    ContentValues cv = new ContentValues();
//                cv.put(SEEXP, yesterdate);
                    cv.put(SEVE,12);
                    cv.put(SEVE1,12);
                    database.update(TABLENAME14,  cv,"ID = ?",new String[]{"1"});
                }
                if(i == 12){
                    Log.d("opop12",String.valueOf(i));
                    update6(database);
                    ContentValues cv = new ContentValues();
//                cv.put(SEEXP, yesterdate);
                    cv.put(SEVE,13);
                    cv.put(SEVE1,13);
                    database.update(TABLENAME14,  cv,"ID = ?",new String[]{"1"});
                }
                if(i == 13){
                    Log.d("opop12",String.valueOf(i));
                    update7(database);
                    ContentValues cv = new ContentValues();
//                cv.put(SEEXP, yesterdate);
                    cv.put(SEVE,14);
                    cv.put(SEVE1,14);
                    database.update(TABLENAME14,  cv,"ID = ?",new String[]{"1"});
                }
                if(i == 14){
                    Log.d("opop12",String.valueOf(i));
                    update8(database);
                    ContentValues cv = new ContentValues();
//                cv.put(SEEXP, yesterdate);
                    cv.put(SEVE,15);
                    cv.put(SEVE1,15);
                    database.update(TABLENAME14,  cv,"ID = ?",new String[]{"1"});
                }
                if(i == 15){
                    Log.d("opop12",String.valueOf(i));
                    update9(database);
                    ContentValues cv = new ContentValues();
//                cv.put(SEEXP, yesterdate);
                    cv.put(SEVE,16);
                    cv.put(SEVE1,16);
                    database.update(TABLENAME14,  cv,"ID = ?",new String[]{"1"});
                }
                if(i == 16){
                    Log.d("opop12",String.valueOf(i));
                    update10(database);
                    ContentValues cv = new ContentValues();
//                cv.put(SEEXP, yesterdate);
                    cv.put(SEVE,17);
                    cv.put(SEVE1,17);
                    database.update(TABLENAME14,  cv,"ID = ?",new String[]{"1"});
                }
                if(i == 17){
                    Log.d("opop12",String.valueOf(i));
                    update11(database);
                    ContentValues cv = new ContentValues();
//                cv.put(SEEXP, yesterdate);
                    cv.put(SEVE,18);
                    cv.put(SEVE1,18);
                    database.update(TABLENAME14,  cv,"ID = ?",new String[]{"1"});
                }
                if(i == 18){
                    Log.d("opop12",String.valueOf(i));
                    update12(database);
                    ContentValues cv = new ContentValues();
//                cv.put(SEEXP, yesterdate);
                    cv.put(SEVE,19);
                    cv.put(SEVE1,19);
                    database.update(TABLENAME14,  cv,"ID = ?",new String[]{"1"});
                }
                if(i == 19){
                    Log.d("opop12",String.valueOf(i));
                    update13(database);
                    ContentValues cv = new ContentValues();
//                cv.put(SEEXP, yesterdate);
                    cv.put(SEVE,20);
                    cv.put(SEVE1,20);
                    database.update(TABLENAME14,  cv,"ID = ?",new String[]{"1"});
                }
            }
//                cv.put(NEWSD,100);
//            database.update(TABLENAME14,  cv,"ID = ?",new String[]{"1"});

//            database.execSQL(DATABASE_ALTER_TEAM_1);
//            String yesterdate,dateString;
//            try {
//                Calendar c = Calendar.getInstance();
//                SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
//                dateString = df.format(c.getTime());
//                Date debit = df.parse(dateString);
//                Calendar cal = Calendar.getInstance();
//                cal.setTime(debit);
//                cal.add(Calendar.DATE,2);
//                yesterdate = df.format(cal.getTime());
//                ContentValues cv = new ContentValues();
//                cv.put(SEEXP, yesterdate);
//                cv.put(SEVE,1);
//                cv.put(SEVE1,1);
//                cv.put(NEWSD,100);
//                database.update(TABLENAME14,  cv,"ID = ?",new String[]{"1"});
//            Log.d("date",yesterdate);
////            String SET_INSERT = "INSERT INTO " + TABLENAME14 + " (" + SEEXP +  ") Values ()";
////            db.execSQL(SET_INSERT);
//            } catch (ParseException e) {
//                e.printStackTrace();
//            }
        }
//                    break;
//            }
//        }
    }



    private static void alterTable(SQLiteDatabase database, String alterAdditions) {
        String sql = "alter table " + TABLENAME2 + " " + alterAdditions;
        Log.v("DB", "alter script: " + sql);
        database.execSQL(sql);
    }
    public void update(SQLiteDatabase db){
        db.execSQL("ALTER TABLE " + TABLENAME2 + " ADD COLUMN previous_order_id INTEGER(250)");

//        ContentValues cv = new ContentValues();
//        cv.put("previous_order_id",100);
//        db.update(TABLENAME14,  cv,"ID = ?",new String[]{"1"});
        Log.d("opop","opop");
    }
    public void update1(SQLiteDatabase db){
        db.execSQL("ALTER TABLE " + TABLENAME14 + " ADD COLUMN pdf_password VARCHAR(250)");
//        ContentValues cv = new ContentValues();
//        cv.put("previous_order_id",100);
//        db.update(TABLENAME14,  cv,"ID = ?",new String[]{"1"});
        Log.d("opop","opop123");
    }
    public void update2(SQLiteDatabase db){
        db.execSQL("ALTER TABLE " + TABLENAME14 + " ADD COLUMN phone_number VARCHAR(250)");
//        ContentValues cv = new ContentValues();
//        cv.put("previous_order_id",100);
//        db.update(TABLENAME14,  cv,"ID = ?",new String[]{"1"});
        Log.d("opop","opop123");
    }
    public void update3(SQLiteDatabase db){
        db.execSQL("ALTER TABLE " + TABLENAME1 + " ADD COLUMN phone_number VARCHAR(250)");
//        ContentValues cv = new ContentValues();
//        cv.put("previous_order_id",100);
//        db.update(TABLENAME14,  cv,"ID = ?",new String[]{"1"});
        Log.d("opop","opop123");
    }
    public void update4(SQLiteDatabase db){
        db.execSQL("ALTER TABLE " + TABLENAME12 + " ADD COLUMN backup_privilege VARCHAR(250)");
        Log.d("opop","opop123");
    }
    public void update5(SQLiteDatabase db){
        db.execSQL("ALTER TABLE " + TABLENAME2 + " ADD COLUMN order_id_new INTEGER(250)");
        Log.d("opop","opop123");
    }
    public void update6(SQLiteDatabase db){
        db.execSQL("CREATE TABLE IF NOT EXISTS "+TABLENAME15+"("+li_id+" INTEGER PRIMARY KEY AUTOINCREMENT,"+SEIMEI+" INTEGER DEFAULT '0',"+license+" VARCHAR ,"+li_name+" VARCHAR ,"+SECR+" DATE,"+SEUPD+" DATE)");
        Log.d("licenseeeeee","opop123");
    }
    public void update7(SQLiteDatabase db){
        db.execSQL("ALTER TABLE " + TABLENAME15 + " ADD COLUMN note VARCHAR(250)");
        Log.d("opop","opop123");
    }
    public void update8(SQLiteDatabase db){
        db.execSQL("CREATE TABLE IF NOT EXISTS "+TABLENAME16+"("+DEID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+DEDB+" INTEGER ,"+DEIN+" INTEGER ,"+DEDOC+" INTEGER " +
                ","+DECO+" INTEGER ,"+DEDI+" INTEGER ,"+DEOT+" INTEREST,"+DECR+" INTEREST,"+DEDE+" INTEREST ,"+DECR1+" DATE,"+DEUPD+" DATE)");
        Log.d("licenseeeeee","opop123");
    }
    public void update9(SQLiteDatabase db){
        db.execSQL("ALTER TABLE " + TABLENAME12 + " ADD COLUMN quick_bulkupdate VARCHAR(250)");
        Log.d("opop","opop123");
    }
    public void update10(SQLiteDatabase db){
        db.execSQL("CREATE TABLE IF NOT EXISTS "+TABLENAME17+"("+REID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+RECUN+" VARCHAR ,"+REAM+" INTEGER ,"+REDE+" INTEGER ,"+RECU+" INTEGER " +
                ","+RECID+" INTEGER ,"+CUTRACK+" VARCHAR ,"+RECR1+" DATE,"+REUPD+" DATE)");
        Log.d("licenseeeeee","opop123");
    }
    public void update11(SQLiteDatabase db){
        db.execSQL("CREATE TABLE IF NOT EXISTS "+TABLENAME18+"("+dr_id+" INTEGER PRIMARY KEY AUTOINCREMENT,"+dr_file+" VARCHAR ,"+dr_folder+" VARCHAR ,"+RECR1+" DATE,"+REUPD+" DATE)");
        Log.d("licenseeeeee","opop123");
    }
    private void update12(SQLiteDatabase db) {
        db.execSQL("ALTER TABLE " + TABLENAME16 + " ADD COLUMN "+CUTRACK+" VARCHAR(250)");
        Log.d("opop","opop123");
    }
    public void update13(SQLiteDatabase db){
        db.execSQL("ALTER TABLE " + TABLENAME15 + " ADD COLUMN image BLOB");
        Log.d("opop","opop123");
    }
    public boolean importDatabase(String dbPath,String db,Context context) throws IOException {

        // Close the SQLiteOpenHelper so it will commit the created empty
        // database to internal storage.
//        final String inFileName = context.getDatabasePath("LOGIN").getPath();
        close();
        File newDb = new File(dbPath);
        File oldDb = new File(db);
        if (newDb.exists()) {
            Log.d("feef", String.valueOf(newDb));
            FileUtils.copyFile(new FileInputStream(newDb), new FileOutputStream(oldDb),context);
            // Access the copied database so SQLiteHelper will cache it and mark
            // it as created.
            getWritableDatabase().close();
            close();
            return true;
        }
        close();
        return false;
    }
}
