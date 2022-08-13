package com.colibri.appconnect.data;

import com.colibri.appconnect.data.firestore.document.UserDoc;
import com.colibri.appconnect.data.firestore.document.UserDocInfoHr;
import com.colibri.appconnect.data.firestore.document.UserDocInfoPersonal;
import com.github.javafaker.Faker;

import java.util.concurrent.ThreadLocalRandom;

public class GenerateRandomData {
    public static UserDoc fakeUser(){
        Faker faker = new Faker();
        UserDoc userDoc = new UserDoc(
                faker.name().name(),
                "",
                new UserDocInfoPersonal(
                        faker.internet().safeEmailAddress(),
                        faker.phoneNumber().phoneNumber()
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
