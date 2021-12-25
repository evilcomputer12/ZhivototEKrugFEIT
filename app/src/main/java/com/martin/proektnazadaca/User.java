package com.martin.proektnazadaca;
public class User {
    public String FirstName, LastName, Phone, PersonType, Email;
    public User(){

    }
    public User(String FirstName, String LastName, String Phone, String PersonType, String Email){
        this.FirstName = FirstName;
        this.LastName = LastName;
        this.Phone = Phone;
        this.PersonType = PersonType;
        this.Email = Email;
    }
}
