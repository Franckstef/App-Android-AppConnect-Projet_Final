package com.colibri.appconnect.data;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.colibri.appconnect.data.entity.User;
import com.colibri.appconnect.data.firestore.document.UserDoc;
import com.colibri.appconnect.data.firestore.firestorelive.DocumentTo;
import com.colibri.appconnect.util.QueryStates;
import com.colibri.appconnect.util.QueryStatus;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

interface IRepository{
    void getUserList();
    void getUser(String userId);
    void getUserChatRooms(String userId);
    void getChatRoom(String chatRoomId);
    void getChatRoomMessages(String chatRoomId);
}
public class repository {
    static private repository _instance;
    FirebaseFirestore firestore = FirebaseFirestore.getInstance();

    private CollectionReference getUsersCollection() {return firestore.collection("repo_user");}

    public LiveData<QueryStatus<User>> getUser(String userId){
        DocumentReference repo_user = getUsersCollection().document(userId);
        LiveData<QueryStatus<UserDoc>> data = DocumentTo.liveData(repo_user.get(), UserDoc.class);
        return Transformations.map(data,input -> {
            QueryStates state = input.getState();
            switch (state){

                case Success:
                    return new QueryStatus.Success<>(new User(input.getData()));
                case Error:
                    if (input.getError() != null) {
                        return new QueryStatus.Error<>(input.getError());
                    }
                    return new QueryStatus.Error<>(input.getMessage());
                case Loading:
                    return new QueryStatus.Loading<>();
                default:
                    return new QueryStatus.Error<>("Query State Invalid");
            }
        });
    }

    public LiveData<QueryStatus<User>> createUser(String userId){

        // TODO: 2022-08-12 Check if a user with the UserId exist before.
        UserDoc randomUser = GenerateRandomData.fakeUser();

        // This function return a livedata object where the status will be loading
        // until the new user is retrieve from the firestore.
        //
        // But, before requesting the new user, we want to wait that the creation of
        // the user is complete.


        // We first create a Livedata to track the userCreation.
        // The status is "Loading"
        MediatorLiveData <QueryStatus<User>> queryStatusLiveData = new MediatorLiveData<>();
        queryStatusLiveData.postValue(new QueryStatus.Loading<>());

        // We then create the userCreation task.
        Task<Void> task_userCreation = getUsersCollection().document(userId).set(randomUser);

        // When the task fail, our Livedata status will be "Error"
        task_userCreation.addOnFailureListener(
            exception -> {
                queryStatusLiveData.postValue(new QueryStatus.Error<User>(exception));
            });

        // When the task is a Success, we then add a new source
        // to the livedata.
        // This source is the request for the document
        // When adding a source, you have to define an observer function
        // to know what to do with the information to this source.
        task_userCreation.addOnSuccessListener(v -> {
            LiveData<QueryStatus<User>> userQuery = getUser(userId);
            queryStatusLiveData.addSource(userQuery, userQueryStatus -> {
                queryStatusLiveData.postValue(userQueryStatus);
            });
        });

        return queryStatusLiveData;
    }

    static public repository getInstance(){
        if (_instance == null) {
            _instance = new repository();
        }
        return _instance;
    }



}
