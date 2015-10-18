package utils;

/**
 * Created by davem on 24/09/2015.
 */
public class Person {
    private String name, surname, email;
    private int ID;

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getSurname() {
        return surname;
    }
    public void setSurname(String surname) {
        this.surname = surname;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public int getID() {
        return ID;
    }
    public void setID(int iD) {
        ID = iD;
    }

    @Override
    public String toString(){
        return "ID: "+getID()+ "\n"+"Name: "+getName()+"\n"+"Surname: "+getSurname()+"\n"+"Email: "+getEmail()+"\n";
    }
}
