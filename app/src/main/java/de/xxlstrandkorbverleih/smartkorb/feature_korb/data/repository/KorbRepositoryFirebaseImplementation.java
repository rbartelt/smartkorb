package de.xxlstrandkorbverleih.smartkorb.feature_korb.data.repository;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import de.xxlstrandkorbverleih.smartkorb.feature_korb.domain.model.Korb;
import de.xxlstrandkorbverleih.smartkorb.feature_korb.domain.repository.KorbRepository;

public class KorbRepositoryFirebaseImplementation implements KorbRepository {

    private String TAG = this.getClass().getSimpleName();
    private CollectionReference db = FirebaseFirestore.getInstance().collection("beachchair");

    private MutableLiveData<List<Korb>> allBechchairs = new MutableLiveData<>();

    public KorbRepositoryFirebaseImplementation() {
        db.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    Log.w(TAG, "Listen failed.", error);
                    return;
                }
                List<Korb> result = new ArrayList<Korb>();
                for (QueryDocumentSnapshot document : value) {
                    Korb tmp = document.toObject(Korb.class);
                    result.add(tmp);
                }
                allBechchairs.postValue(result);
            }
        });
    }

    public LiveData<List<Korb>> getAllKörbe() {
        return allBechchairs;
    }

    public void insert(Korb beachchair) {
        db.document(String.valueOf(beachchair.getNumber())).set(beachchair).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Log.d(TAG, "DocumentSnapshot added with ID: ");
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });
    }

    @Override
    public void update(Korb beachchair) {
        db.document(String.valueOf(beachchair.getNumber())).set(beachchair).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Log.d(TAG, "DocumentSnapshot added with ID: ");
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });

    }

    public void delete(Korb beachchair) {
        db.document(String.valueOf(beachchair.getNumber())).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Log.d(TAG, "Beachchair: " + beachchair.getNumber() + " deleted.");
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Deleting "+beachchair.getNumber()+" failed.", e);
                    }
                });
    }

    @Override
    public void deletAllKörbe() {

    }

    public LiveData<Korb> getBeachchairByNumber(int number) {
        final MutableLiveData<Korb> beachchairMutableLiveData = new MutableLiveData<>();
        db.document(String.valueOf(number)).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot != null) {
                    Korb beachchair = documentSnapshot.toObject(Korb.class);
                    beachchairMutableLiveData.setValue(beachchair);
                }
            }
        });
        return beachchairMutableLiveData;
    }

    @Override
    public LiveData<Korb> getBeachchairByUid(String beachchairUid) {
        final MutableLiveData<Korb> beachchairMutableLiveData = new MutableLiveData<>();
        db.whereEqualTo("korbUid", beachchairUid).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    Log.w(TAG, "Listen failed.", error);
                    return;
                }
                List<Korb> tmp = value.toObjects(Korb.class);
                beachchairMutableLiveData.setValue(tmp.get(0));
                }
            });
        return beachchairMutableLiveData;
    }
}
