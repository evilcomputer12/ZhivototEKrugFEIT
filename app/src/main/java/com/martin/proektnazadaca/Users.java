package com.martin.proektnazadaca;

import java.util.Comparator;

public class Users{
    public String fname1, lname1, lptype1, lprofpic1, llocation1, dist, rating, uid;

    public Users() {

    }


    public String getFname1() {
        return fname1;
    }

    public String getLname1() {
        return lname1;
    }

    public String getLptype1() {
        return lptype1;
    }

    public String getLprofpic1() {
        return lprofpic1;
    }

    public String getLlocation1() {
        return llocation1;
    }

    public String getDist() {
        return dist;
    }

    public String getRating() {
        return rating;
    }
    public String getUid() {
        return uid;
    }

    public Users(String fname1, String lname1, String lptype1, String lprofpic1, String llocation1, String dist, String rating, String uid) {
        this.fname1 = fname1;
        this.lname1 = lname1;
        this.lptype1 = lptype1;
        this.lprofpic1 = lprofpic1;
        this.llocation1 = llocation1;
        this.dist = dist;
        this.rating = rating;
        this.uid = uid;
    }


//    public static Comparator<Users> myName = new Comparator<Users>() {
//        @Override
//        public int compare(Users e1, Users e2) {
//            return e1.getFname1().compareTo(e2.getFname1());
//        }
//    };
}
