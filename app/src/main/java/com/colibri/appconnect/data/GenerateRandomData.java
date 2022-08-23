package com.colibri.appconnect.data;

import com.colibri.appconnect.data.firestore.document.UserDoc;
import com.colibri.appconnect.data.firestore.document.UserDocInfoHr;
import com.colibri.appconnect.data.firestore.document.UserDocInfoPersonal;
import com.github.javafaker.Faker;
import com.google.firebase.auth.FirebaseUser;

import java.util.concurrent.ThreadLocalRandom;

public class GenerateRandomData {
    static Faker faker = new Faker();

    public static String getFakeName(){
        return getFakeName(null);
    }

    public static String getFakeName(FirebaseUser user){
        if (user != null) {
            String name = user.getDisplayName();
            if(name != null && !name.isEmpty()){
                return name;
            }
        }
        return faker.name().name();
    }

    public static String getFakeAvatar(){
        return getFakeName(null);
    }

    public static String getFakeAvatar(FirebaseUser user){
        if (user != null) {
            if(user.getPhotoUrl() != null){
                return user.getPhotoUrl().toString();
            }else {
                return "https://i.pravatar.cc/150?u="+user.getUid();
            }
        }
        int randomNum = ThreadLocalRandom.current().nextInt(1, 9999999);
        return "https://i.pravatar.cc/150?u=" + randomNum;
    }

    public static String getFakeEmail(){
        return getFakeEmail(null);
    }

    public static String getFakeEmail(FirebaseUser user){
        if (user != null) {
            String email = user.getEmail();
            if(email != null && !email.isEmpty()){
                return email;
            }
        }
        return faker.internet().safeEmailAddress();
    }

    public static String getFakePhone(){
        return getFakeEmail(null);
    }

    public static String getFakePhone(FirebaseUser user){
        if (user != null) {
            String phone = user.getPhoneNumber();
            if(phone != null && !phone.isEmpty()){
                return phone;
            }
        }
        return faker.phoneNumber().phoneNumber();
    }

    public static UserDocInfoHr getFakeHrInfo(){
        return new UserDocInfoHr(
                ThreadLocalRandom.current().nextInt(1111, 9999),
                faker.job().title(),
                faker.country().capital()
        );
    }

    public static UserDocInfoPersonal getFakeInfoPersonal(){
        return getFakeInfoPersonal(null);
    }

    public static UserDocInfoPersonal getFakeInfoPersonal(FirebaseUser user){
        return new UserDocInfoPersonal(
                getFakeEmail(user),
                getFakePhone(user)
        );
    }

    public static UserDoc fakeUser(){
        return fakeUser(null);
    }

    public static UserDoc fakeUser(FirebaseUser user){
        return new UserDoc(
                getFakeName(user),
                getFakeAvatar(user),
                getFakeInfoPersonal(user),
                getFakeHrInfo()
        );
    }
}
