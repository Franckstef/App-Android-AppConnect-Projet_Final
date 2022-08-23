package com.colibri.appconnect.data.firestore.document;

import android.util.Log;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentId;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Exclude;

import java.util.Objects;

enum DocumentState{
    /**
     * Document with conflicted DocumentReference
     */
    ERROR,
    /**
     * Document without ID and Collection
     */
    NO_DOCUMENT,
    /**
     * Document with Collection, without ID
     */
    MISSING_ID,
    /**
     * Document with ID, without Collection
     */
    FLOATING_DOC,
    /**
     * Document with ID and Collection
     */
    DOCUMENT
}
public abstract  class FirestoreDocument {
    @DocumentId private String docId;
    @Exclude private DocumentReference documentReference;
    @Exclude private CollectionReference parentCollectionReference;


    FirestoreDocument(){}
    FirestoreDocument(DocumentSnapshot snapshot){
        linkToSnapshot(snapshot);
    }

    private DocumentState getState(){
        if (asConflictField()){
            return DocumentState.ERROR;
        }
        if (documentReference == null) {
            if (docId == null) {
                if (parentCollectionReference == null) {
                    return DocumentState.NO_DOCUMENT;
                } else {
                    return DocumentState.MISSING_ID;
                }
            } else {
                if (parentCollectionReference == null) {
                    return DocumentState.FLOATING_DOC;
                }
            }
        }
        return DocumentState.DOCUMENT;
    }

    private boolean asConflictField(){
        boolean isConflicted = false;
        String errorState = "Conflicted Field: ";
        //If there isn't a document reference, there can be no conflict
        if (documentReference != null) {
            if (    docId != null &&
                    !docId.equals(documentReference.getId()))
            {
                    errorState = errorState + "Document ID";
                    isConflicted = true;
            }

            if (    parentCollectionReference != null &&
                    !documentReference.getParent().getPath().equals(parentCollectionReference.getPath()))
            {
                errorState = errorState + "Parent Collection";
                isConflicted = true;
            }
        }
        if(isConflicted){
            Log.e(TAG, "asConflictField: " + errorState);
        }
        return isConflicted;
    }

    public void setDocId(String docId) {
        final String currentDocId = getDocId();

        //If there's no change, exit
        if(Objects.equals(docId, currentDocId)){
            return;
        }
        //If it was already set, show a warning
        if (currentDocId != null) {
            Log.w(TAG, "setDocId: Changing Document ID from '" +
                    currentDocId + "' to '" + docId + "'." +
                    "This may create a copy of this document.");
        }

        final DocumentState state = getState();
        this.docId = docId;
        switch (state){
            case NO_DOCUMENT:
            case FLOATING_DOC:
                assert getState() == DocumentState.FLOATING_DOC;
                break;
            case MISSING_ID:
            case DOCUMENT:
                final CollectionReference parent = getParentCollectionReference();
                documentReference = parent.document(docId);
                assert getState() == DocumentState.DOCUMENT;
                break;
        }
    }



    @Exclude
    public void setParentCollectionReference(CollectionReference parentCollectionReference) {
        final CollectionReference parent = getParentCollectionReference();

        String currentPath = null;
        if (parent != null) {
            currentPath = parent.getPath();
        }

        String newPath = null;
        if (parentCollectionReference != null) {
            newPath = parentCollectionReference.getPath();
        }

        //If there's no change, exit
        if(Objects.equals(currentPath, newPath))
        {
            return;
        }

        //If it was already set, show a warning
        if (parent != null) {
            Log.w(TAG, "setParentCollectionReference: Changing Document ID from '" +
                    currentPath + "' to '" + newPath + "'." +
                    "This may create a copy of this document.");
        }

        final DocumentState state = getState();
        this.parentCollectionReference = parentCollectionReference;
        switch (state){
            case NO_DOCUMENT:
            case MISSING_ID:
                assert getState() == DocumentState.MISSING_ID;
                break;
            case FLOATING_DOC:
            case DOCUMENT:
                final String id = getDocId();
                documentReference = parentCollectionReference.document(id);
                assert getState() == DocumentState.DOCUMENT;
                break;
        }
    }

    @Exclude
    private void setDocumentReference(DocumentReference newDocumentReference) {
        final DocumentReference reference = getDocumentReference();

        String currentPath = null;
        if (reference != null) {
            currentPath = reference.getPath();
        }

        String newPath = null;
        if (newDocumentReference != null) {
            newPath = newDocumentReference.getPath();
        }

        //If there's no change, exit
        if(Objects.equals(currentPath, newPath))
        {
            return;
        }



        final DocumentState state = getState();
        final CollectionReference currentParent = getParentCollectionReference();

        CollectionReference newParent = null;
        if (newDocumentReference != null) {
            newParent = newDocumentReference.getParent();
        }

        final String currentId = getDocId();

        String newId = null;
        if (newDocumentReference != null) {
            newId = newDocumentReference.getId();
        }
        this.documentReference = newDocumentReference;

        switch (state){
            case MISSING_ID:
                if(!currentParent.getPath().equals(newParent.getPath())){
                    Log.w(TAG, "setDocumentReference: Changing Document Collection from '" +
                            currentParent.getPath() +
                            "' to '" +
                            newParent.getPath() +
                            "'.");
                }
                break;
            case FLOATING_DOC:
                if(!Objects.equals(currentId, newId)){
                    Log.w(TAG, "setDocumentReference: Changing Document Id from '" +
                            currentId +
                            "' to '" +
                            newId +
                            "'.");
                }
            case DOCUMENT:
                if(!Objects.equals(currentPath, newPath)) {
                    Log.w(TAG, "setDocumentReference: Changing Document Reference from '" +
                            currentPath + "' to '" + newPath + "'." +
                            "This may create a copy of this document.");
                    break;
                }

        }
        this.documentReference = newDocumentReference;
        this.docId = newId;
        this.parentCollectionReference = newParent;
        assert getState() == DocumentState.DOCUMENT;
    }

    public String getDocId() {
        final DocumentState state = getState();
        if(state == DocumentState.ERROR){
            Log.e(TAG, "getDocId: ", new Exception("Conflicted Field"));
            return null;
        }

        if (docId == null) {
            switch (state){
                case FLOATING_DOC:
                    Log.e(TAG, "getDocId: ", new Exception(
                            "Impossible state: Floating Document without a Document ID"
                    ));
                    return null;
                case NO_DOCUMENT:
                case MISSING_ID:
                    return null;
                case DOCUMENT:
                    if (documentReference == null) {
                        Log.e(TAG, "getDocId: ", new Exception(
                                "Impossible state: Document without a Document Reference OR a Document ID"
                        ));
                        return null;
                    }
                    final String referenceId = documentReference.getId();
                    Log.w(TAG, "getDocId: " +
                            "Document ID is NULL and shouldn't be when in state 'Document'. " +
                            "Fixing it by setting it to the DocumentReferenceID '" +
                            referenceId +
                            "'");
                    docId = referenceId;
                    break;
            }
        }
        return docId;
    }
    @Exclude
    public CollectionReference getParentCollectionReference() {
        final DocumentState state = getState();
        if(state == DocumentState.ERROR){
            Log.e(TAG, "getParentCollectionReference: ", new Exception("Conflicted Field"));

            return null;
        }

        if (parentCollectionReference == null) {
            switch (state){
                case MISSING_ID:
                    Log.e(TAG, "getDocId: ", new Exception(
                            "Impossible state: Missing_ID Document without a ParentCollection"
                    ));
                    return null;
                case FLOATING_DOC:
                case NO_DOCUMENT:
                    return null;
                case DOCUMENT:
                    if (documentReference == null) {
                        Log.e(TAG, "getParentCollectionReference: ", new Exception(
                                "Impossible state: Document without a Document Reference OR a ParentCollection"
                        ));
                        return null;
                    }
                    final CollectionReference parent = documentReference.getParent();
                    final String referenceId = documentReference.getId();
                    Log.w(TAG, "getParentCollectionReference: " +
                            "ParentCollection is NULL and shouldn't be when in state 'Document'. " +
                            "Fixing it by setting it to the DocumentReference parent '" +
                            parent.getPath() +
                            "'");
                    parentCollectionReference = parent;
                    break;
            }
        }
        return parentCollectionReference;
    }
    @Exclude
    public DocumentReference getDocumentReference() {
        final DocumentState state = getState();
        if(state == DocumentState.ERROR){
            Log.e(TAG, "getDocumentReference: ", new Exception("Conflicted Field"));
            return null;
        }

        if (documentReference == null) {
            switch (state){
                case FLOATING_DOC:
                case NO_DOCUMENT:
                case MISSING_ID:
                    return null;
                case DOCUMENT:
                    if (docId == null || parentCollectionReference == null) {
                        Log.e(TAG, "getDocumentReference: ", new Exception(
                                "Impossible state: Document without a Document Reference or, a Document ID and ParentCollection"
                        ));
                        return null;
                    }
                    final DocumentReference document = parentCollectionReference.document(docId);
                    Log.w(TAG, "getDocumentReference: " +
                            "Document Reference is NULL and shouldn't be when in state 'Document'. " +
                            "Fixing it by setting it to the path parentCollection/docId '" +
                            document.getPath() +
                            "'");
                    documentReference = document;
                    break;
            }
        }
        return documentReference;
    }

    public void pushToFirebase(){
        if (documentReference == null) {
            Log.e(TAG, "pushToFirebase: ", new Exception("Cannot execute pushToFirebase because documentReference is null"));
            return;
        }
        documentReference.set(this);
    }

    public void linkToSnapshot(DocumentSnapshot snapshot){
        if (snapshot == null) {
            Log.e(TAG, "linkToSnapshot: ", new Exception("Snapshot shouldn't be null"));
        } else {
            setDocumentReference(snapshot.getReference());
        }
    }





    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FirestoreDocument that = (FirestoreDocument) o;
        return Objects.equals(getDocId(), that.getDocId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getDocId());
    }

    private static final String TAG = "AP::FirestoreDocument";
}
