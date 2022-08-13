package com.colibri.appconnect.data.firestore.firestorelive.util;

import com.colibri.appconnect.data.firestore.document.FirestoreDocument;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

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
            ((FirestoreDocument) pojoObj).setDocumentReference(querySnapshot.getReference());
        }
        return pojoObj;
    }
}
