package com.colibri.appconnect.data.firestore.firestorelive.util;

import android.util.Log;

import com.colibri.appconnect.data.firestore.document.FirestoreDocument;
import com.colibri.appconnect.util.QueryStatus;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class FirestoreLiveUtil {
    public static <T>  List<T> QueryToPojo(QuerySnapshot querySnapshot, Class<T> tClass){
        List<T> returnList = new ArrayList<>();
        for (DocumentSnapshot doc :
                querySnapshot.getDocuments()) {
            returnList.add(DocumentToPojo(doc,tClass));
        }
        return returnList;
    }

    public static <T>  T DocumentToPojo(DocumentSnapshot querySnapshot, Class<T> tClass){
        T pojoObj = querySnapshot.toObject(tClass);
        if(pojoObj instanceof FirestoreDocument){
            ((FirestoreDocument) pojoObj).linkToSnapshot(querySnapshot);
        }
        return pojoObj;
    }

    public static <T>  QueryStatus<T> NewDocumentToPojo(DocumentSnapshot querySnapshot, Class<T> aClass){
        if(FirestoreDocument.class.isAssignableFrom(aClass)){
            try {
                final Constructor<? extends T> ctor = aClass.getConstructor(DocumentSnapshot.class);
                final T newInstance = ctor.newInstance(querySnapshot);
                return new QueryStatus.NewDocument<>(newInstance);

            } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
                return new QueryStatus.Error<>(e.getMessage());
            }
        } else {
            return new QueryStatus.Error<>("No Document Found");
        }
    }


}
