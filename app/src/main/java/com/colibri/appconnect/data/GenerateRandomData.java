package com.colibri.appconnect.data;

import com.colibri.appconnect.data.firestore.document.UserDoc;
import com.colibri.appconnect.data.firestore.document.UserDocInfoHr;
import com.colibri.appconnect.data.firestore.document.UserDocInfoPersonal;
import com.github.javafaker.Faker;
import com.google.firebase.auth.FirebaseUser;

import java.util.concurrent.ThreadLocalRandom;

public class GenerateRandomData {
    public static UserDoc fakeUser(FirebaseUser user){
        Faker faker = new Faker();
        String name = user.getDisplayName();
        if(name == null ||name.isEmpty()){
            name = faker.name().name();
        }

        String email = user.getEmail();
        if(email == null ||email.isEmpty()){
            email = faker.internet().safeEmailAddress();
        }

        String avatar;
        if(user.getPhotoUrl() != null) {
            avatar = user.getPhotoUrl().toString();
        } else {
            avatar = "https://i.pravatar.cc/150?u="+user.getUid();
        }

        String phone = user.getPhoneNumber();
        if(phone == null || phone.isEmpty()){
            phone = faker.phoneNumber().phoneNumber();
        }

        UserDoc userDoc = new UserDoc(
                name,
                avatar,
                new UserDocInfoPersonal(
                        email,
                        phone
                ),
                new UserDocInfoHr(
                        ThreadLocalRandom.current().nextInt(1111, 9999),
                        faker.job().title(),
                        faker.country().capital()
                )
        );
        return userDoc;
    }
}
