package com.martin.proektnazadaca;
public class User {
    public String FirstName, LastName, Phone, PersonType, Email, ProfilePic, Location, Distance, Rating, Uid;
    public User(){

    }


    public User(String FirstName, String LastName, String Phone, String PersonType, String Email, String ProfilePic, String Location, String Distance, String Rating, String Uid){
        this.FirstName = FirstName;
        this.LastName = LastName;
        this.Phone = Phone;
        this.PersonType = PersonType;
        this.Email = Email;
        this.ProfilePic = ProfilePic;
        this.Location = Location;
        this.Distance = Distance;
        this.Rating = Rating;
        this.Uid = Uid;
    }
}
