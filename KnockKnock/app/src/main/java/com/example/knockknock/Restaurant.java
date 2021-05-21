package com.example.knockknock;

public class Restaurant {

/*
    static String res_code; //레스토랑 코드
    static String res_name; //레스토랑 이름
    static String res_num; //레스토랑 전화번호
    static String res_gps; //레스토랑 위치
    static String res_time; //레스토랑 영업 시간
*/
     String res_code; //레스토랑 코드
     String res_name; //레스토랑 이름

    public String getRes_code() {
        return res_code;
    }

    public void setRes_code(String res_code) {
        this.res_code = res_code;
    }

    public String getRes_name() {
        return res_name;
    }

    public void setRes_name(String res_name) {
        this.res_name = res_name;
    }

    public String getRes_num() {
        return res_num;
    }

    public void setRes_num(String res_num) {
        this.res_num = res_num;
    }

    public String getRes_gps() {
        return res_gps;
    }

    public void setRes_gps(String res_gps) {
        this.res_gps = res_gps;
    }

    public String getRes_time() {
        return res_time;
    }

    public void setRes_time(String res_time) {
        this.res_time = res_time;
    }

    String res_num; //레스토랑 전화번호
     String res_gps; //레스토랑 위치
     String res_time; //레스토랑 영업 시간

/*
    public static String getRes_code() {
        return res_code;
    }

    public static void setRes_code(String res_code) {
        Restaurant.res_code = res_code;
    }

    public static String getRes_name() {
        return res_name;
    }

    public static void setRes_name(String res_name) {
        Restaurant.res_name = res_name;
    }

    public static String getRes_num() {
        return res_num;
    }

    public static void setRes_num(String res_num) {
        Restaurant.res_num = res_num;
    }

    public static String getRes_gps() {
        return res_gps;
    }

    public static void setRes_gps(String res_gps) {
        Restaurant.res_gps = res_gps;
    }

    public static String getRes_time() {
        return res_time;
    }

    public static void setRes_time(String res_time) {
        Restaurant.res_time = res_time;
    }

*/
    public Restaurant(String res_code, String res_name, String res_num, String res_gps, String res_time) {
        this.res_code = res_code;
        this.res_name = res_name;
        this.res_num = res_num;
        this.res_gps = res_gps;
        this.res_time = res_time;
    }
/*
 public Restaurant() {
    }

    public String getRes_code() {
        return res_code;
    }

    public void setRes_code(String res_code) {
        this.res_code = res_code;
    }

    public String getRes_name() {
        return res_name;
    }

    public void setRes_name(String res_name) {
        this.res_name = res_name;
    }

    public String getRes_num() {
        return res_num;
    }

    public void setRes_num(String res_num) {
        this.res_num = res_num;
    }

    public String getRes_gps() {
        return res_gps;
    }

    public void setRes_gps(String res_gps) {
        this.res_gps = res_gps;
    }

    public String getRes_time() {
        return res_time;
    }

    public void setRes_time(String res_time) {
        this.res_time = res_time;
    }

 */
}

