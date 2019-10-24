package com.tabian.tcoetkiosk;

/**
 * Created by User on 2/8/2017.
 */

public class UserInformation  {

   /* private String name;
    private String email;
    private String phone_num;*/

    public static String publicName;
    public static String publicClass;
    public static String publicDept;
    public static String publicDiv;
    public static String publicRno;
    public static String publicAd1;
    public static String publicAd2;
    public static String publicAd3;
    public static String publicAd4;
    public static String publicPhone;
    public static String publicAcad;
    public static String publicEmail;

    public UserInformation(){

    }

    public UserInformation(String publicName,String publicClass,String publicDept,String publicDiv,
                           String publicRno,String publicAd1,String publicAd2,String publicAd3,String publicAd4,
                           String publicAcad,String publicPhone,String publicEmail) {


        this.publicName = publicName;
        this.publicClass = publicClass;
        this.publicDept = publicDept;
        this.publicDiv = publicDiv;
        this.publicRno = publicRno;
        this.publicAd1 = publicAd1;
        this.publicAd2 = publicAd2;
        this.publicAd3 = publicAd3;
        this.publicAd4 = publicAd4;
        this.publicPhone = publicPhone;
        this.publicAcad = publicAcad;
        this.publicEmail = publicEmail;

    }
/*1 GET 1 SET..zzzzzzzzzzz.*/
    public String getPublicName() {
        return publicName;
    }
    public void setPublicName(String publicName) {
        this.publicName = publicName;
    }

    public String getPublicClass() {
        return publicClass;
    }

    public void setPublicClass(String publicClass) {
        this.publicClass = publicClass;
    }

    public String getPublicDept() {
        return publicDept;
    }

    public void setPublicDept(String publicDept) {
        this.publicDept = publicDept;
    }
    public String getPublicDiv() {
        return publicDiv;
    }
    public void setPublicDiv(String publicDiv) {
        this.publicDiv = publicDiv;
    }

    public String getPublicRno() {
        return publicRno;
    }
    public void setPublicRno(String publicRno) {
        this.publicRno = publicRno;
    }

    public String getPublicAd1() {
        return publicAd1;
    }


    public void setPublicAd1(String publicAd1) {
        this.publicAd1 = publicAd1;
    }

    public String getPublicAd2() {
        return publicAd2;
    }
    public void setPublicAd2(String publicAd2) {
        this.publicAd2 = publicAd2;
    }

    public String getPublicAd3() {
        return publicAd3;
    }
    public void setPublicAd3(String publicAd3) {
        this.publicAd3 = publicAd3;
    }

    public String getPublicAd4() {
        return publicAd4;
    }
    public void setPublicAd4(String publicAd4) {
        this.publicAd4 = publicAd4;
    }
    public String getPublicPhone() {
        return publicPhone;
    }
    public void setPublicPhone(String publicPhone) {
        this.publicPhone = publicPhone;
    }
    public String getPublicAcad() {
        return publicAcad;
    }
    public void setPublicAcad(String publicAcad) {
        this.publicAcad = publicAcad;
    }

    public String getPublicEmail() {
        return publicEmail;
    }
    public void setPublicEmail(String publicEmail) {
        this.publicEmail = publicEmail;
    }

}