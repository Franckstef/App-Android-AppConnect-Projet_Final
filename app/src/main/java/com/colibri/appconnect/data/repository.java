package com.colibri.appconnect.data;

import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.Transformations;

import com.colibri.appconnect.data.entity.User;
import com.colibri.appconnect.data.firestore.document.UserDoc;
import com.colibri.appconnect.data.firestore.firestorelive.DocumentTo;
import com.colibri.appconnect.util.QueryStates;
import com.colibri.appconnect.util.QueryStatus;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
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
    private final FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    private final Authentication auth = Authentication.getInstance();
    private final MutableLiveData<String> currentUserId =  new MutableLiveData<>();
    private CollectionReference getUsersCollection() {return firestore.collection("repo_user");}

    public void SignOut(){
        auth.signout();
    }

    public LiveData<Boolean> isSignIn(){
        return auth.isSigningIn;
    }
    public LiveData<QueryStatus<User>> getCurrentUser(){
        Log.d(TAG, "getCurrentUser: ");
        MediatorLiveData<QueryStatus<User>> liveResponse = new MediatorLiveData<>();
        liveResponse.postValue(new QueryStatus.Loading<>());

        LiveData<QueryStatus<String>> liveUID = auth.getLiveCurrentUserId();


        liveResponse.addSource(liveUID,
                new Observer<QueryStatus<String>>() {
                    LiveData<QueryStatus<User>> liveCurrentUser = null;
                    @Override
                    public void onChanged(QueryStatus<String> uid) {
                        Log.d(TAG, "liveUID onChanged: ");
                        if (uid == null) {
                            liveResponse.postValue(new QueryStatus.Error<>("UserID isn't initialize"));
                            return;
                        }

                        if (liveCurrentUser != null) {
                            liveResponse.removeSource(liveCurrentUser);
                            liveCurrentUser = null;
                        }

                        QueryStates state = uid.getState();
                        switch (state){
                            case Success:
                                liveResponse.postValue(new QueryStatus.Loading<>());
                                liveCurrentUser = getUser(uid.getData());
                                liveResponse.addSource(liveCurrentUser, liveResponse::setValue);
                            case Error:
                                liveResponse.postValue((new QueryStatus.Error<>(uid.getMessage())));
                            case Loading:
                                liveResponse.postValue(new QueryStatus.Loading<>());
                            default:
                                liveResponse.postValue(new QueryStatus.Error<>("Unknown error with UserId"));
                        }
                    }
                }
        );
        return liveResponse;
    }

    public Authenticator getAuthenticator(AppCompatActivity callerActivity){
        return auth.getAuthenticator(callerActivity);
    }

    public LiveData<QueryStatus<Void>> AuthenticateUser(Authenticator authenticator){
        if(auth.getCurrentUserId() != null){
            return new MutableLiveData<>(new QueryStatus.Success<>());
        }

        //The Boolean value return from AuthenticateUser is true if it's a new user
        LiveData<QueryStatus<FirebaseUser>> authResult = auth.AuthenticateUser(authenticator);
        return Transformations.map(authResult, input -> {
            QueryStates state = input.getState();
            switch (state){
                case Success:
                    Log.d(TAG, "AuthenticateUser: Success");
                    if(input.getData() != null){
                        createUser(input.getData());
                        // TODO: 2022-08-13 Create the new user
                        Log.d(TAG, "AuthenticateUser: New User");
                    }
                    return new QueryStatus.Success<>();
                case Error:
                    Log.d(TAG, "AuthenticateUser: Error");
                    return new QueryStatus.Error<>(input.getMessage());
                case Loading:
                    Log.d(TAG, "AuthenticateUser: Loading");
                    return new QueryStatus.Loading<>();
                default:
                    return new QueryStatus.Error<>("Unknown Error");
            }
        });
    }



    public LiveData<QueryStatus<User>> getUser(String userId){
        DocumentReference repo_user = getUsersCollection().document(userId);
        LiveData<QueryStatus<UserDoc>> data = DocumentTo.liveData(repo_user.get(), UserDoc.class);
        return Transformations.map(data,input -> {
            QueryStates state = input.getState();
            switch (state){
                case Success:
                    if (input.getData() == null) {
                        return new QueryStatus.Error<User>("No User Found");
                    }
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

    public LiveData<QueryStatus<User>> createUser(FirebaseUser user){

        MediatorLiveData <QueryStatus<User>> queryStatusLiveData = new MediatorLiveData<>();
        queryStatusLiveData.postValue(new QueryStatus.Loading<>());

        // TODO: 2022-08-12 Check if a user with the UserId exist before.
        UserDoc randomUser = GenerateRandomData.fakeUser(user);
        String userId = user.getUid();
        // This function return a livedata object where the status will be loading
        // until the new user is retrieve from the firestore.
        //
        // But, before requesting the new user, we want to wait that the creation of
        // the user is complete.


        // We first create a Livedata to track the userCreation.
        // The status is "Loading"


        // We then create the userCreation task.
        Task<Void> task_userCreation = getUsersCollection().document(userId).set(randomUser);

        // When the task fail, our Livedata status will be "Error"
        task_userCreation.addOnFailureListener(
            exception -> queryStatusLiveData.postValue(new QueryStatus.Error<>(exception)));

        // When the task is a Success, we then add a new source
        // to the livedata.
        // This source is the request for the document
        // When adding a source, you have to define an observer function
        // to know what to do with the information to this source.
        task_userCreation.addOnSuccessListener(v -> {
            LiveData<QueryStatus<User>> userQuery = getUser(userId);
            queryStatusLiveData.addSource(userQuery, queryStatusLiveData::postValue);
        });

        return queryStatusLiveData;
    }

    static public repository getInstance(){
        if (_instance == null) {
            _instance = new repository();
        }
        return _instance;
    }

    private static final String TAG = "AP::repository";
}
