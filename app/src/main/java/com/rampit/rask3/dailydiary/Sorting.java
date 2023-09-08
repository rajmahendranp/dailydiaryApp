package com.rampit.rask3.dailydiary;

//Updated on 25/01/2022 by RAMPIT
//Sort array of datas

import java.util.Comparator;

public class Sorting {

    private String file,id;
    private Integer created;


    public Sorting(String file , String created , String id) {

        this.file = String.valueOf(file);
        this.created = Integer.valueOf(created);
        this.id = String.valueOf(id);

    }

    public static Comparator<Sorting> days_desc = new Comparator<Sorting>() {

        public int compare(com.rampit.rask3.dailydiary.Sorting s1, com.rampit.rask3.dailydiary.Sorting s2) {

            int rollno1 = Integer.parseInt(s1.getRollno());
            int rollno2 = Integer.parseInt(s2.getRollno());

            /*For ascending order*/
            return rollno1-rollno2;

            /*For descending order*/
//            return rollno2-rollno1;
        }};
    @Override
    public String toString() {
        return  file+",,,"+String.valueOf(created)+",,,"+String.valueOf(id);
    }

    public String getRollno() {
        return String.valueOf(created);
    }
}
//