package com.example.contacts;
/*This data is not being used now as we aew using roon database*/
public class Data {
    private int id;
    private String Name;
    private String Number;
    private String Number2;
    private String Number3;
    private String Email;

    public int getId() {
        return id;
    }

    public String getNumber2() {
        return Number2;
    }

    public void setNumber2(String number2) {
        Number2 = number2;
    }

    public String getNumber3() {
        return Number3;
    }

    public void setNumber3(String number3) {
        Number3 = number3;
    }

    public Data(int ID,String name, String number, String email, String number2, String number3) {
        id = ID;
        Name = name;
        Number = number;
        Email = email;
        Number2 = number2;
        Number3 = number3;
    }
    public Data(String name, String number, String email,String number2,String number3) {
        Name = name;
        Number = number;
        Email = email;
        Number2 = number2;
        Number3 = number3;
    }
    public Data(String name, String number, String email) {
        Name = name;
        Number = number;
        Email = email;
    }
    public Data(int ID,String name, String number, String email) {
        Name = name;
        Number = number;
        Email = email;
        id = ID;
    }
    public Data() {}

    public String getName() {
        return Name;
    }

    public String getNumber() {
        return Number;
    }

    public String getEmail() {
        return Email;
    }

    public void setName(String name) {
        Name = name;
    }

    public void setNumber(String number) {
        Number = number;
    }

    public void setEmail(String email) {
        Email = email;
    }
}
