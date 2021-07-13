package com.example.notes.data;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CardSourceFirebaseImpl implements CardsSource {

    private static final String CARDS_COLLECTION = "notes";
    Context context;

    // База данных Firestore
    private FirebaseFirestore store = FirebaseFirestore.getInstance();

    // Коллекция документов
    private CollectionReference collection = store.collection(CARDS_COLLECTION);

    // Загружаемый список карточек
    private List<CardData> cardsData = new ArrayList<CardData>();

    @Override
    public CardsSource init(final CardsSourceResponse cardsSourceResponse) {
        // Получить всю коллекцию, отсортированную по полю (дата)
        collection.orderBy(CardDataMapping.Fields.DATE, Query.Direction.DESCENDING).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    // При удачном считывании данных загрузим список карточек

                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            cardsData = new ArrayList<CardData>();
                            for (QueryDocumentSnapshot document :
                                    task.getResult()) {
                                Map<String, Object> doc = document.getData();
                                String id = document.getId();
                                CardData cardData = CardDataMapping.toCardData(id, doc);
                                cardsData.add(cardData);
                            }

                            cardsSourceResponse.initialized(CardSourceFirebaseImpl.this);
                        } else {
                            Toast.makeText(context, (CharSequence) task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context, "get failed with" + e, Toast.LENGTH_SHORT).show();
            }
        });
        return null;
    }

    @Override
    public CardData getCardData(int position) {
        return cardsData.get(position);
    }

    @Override
    public int size() {
        if (cardsData == null) {
            return 0;
        }
        return cardsData.size();
    }

    @Override
    public void deleteCardData(int position) {
        // Удалить документ с определённым идентификатором
        collection.document(cardsData.get(position).getId()).delete();
        cardsData.remove(position);
    }

    @Override
    public void addCardData(CardData cardData) {
        // Добавить документ
        collection.add(CardDataMapping.toDocument(cardData)).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                cardData.setId(documentReference.getId());
            }
        });

    }

    @Override
    public void clearCardData() {
        for (CardData cardData: cardsData) {
            collection.document(cardData.getId()).delete();
        }
        cardsData = new ArrayList<CardData>();
    }

    @Override
    public void updateCardData(int position, CardData cardData) {
        String id = cardData.getId();
        // Изменить документ по идентификатору
        collection.document(id).set(CardDataMapping.toDocument(cardData));
    }
}
