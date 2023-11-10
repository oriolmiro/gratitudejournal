package org.insbaixcamp.gratitude.journal.daily.tools;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.insbaixcamp.gratitude.journal.daily.model.Quote;

import java.util.Locale;
import java.util.Random;

public class QuotesManager {
    private final String TAG = QuotesManager.class.getSimpleName();
    String languageCode;

    public QuotesManager(FirebaseDatabase mockedFirebaseDatabase) {
    }

    public QuotesManager() {

    }

    public void getQuoteOfTheDay(QuoteCallback quoteCallback){

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        languageCode = Locale.getDefault().getLanguage(); // Obtener el código de idioma local

        // Comprobar si existe el nodo de idioma, de lo contrario usar "default"
        databaseReference.child("quotes").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (!dataSnapshot.hasChild(languageCode)) {
                    languageCode = "default";
                }

                DatabaseReference quotesRef = databaseReference.child("quotes").child(languageCode);

                quotesRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists() && dataSnapshot.hasChildren()) {
                            // Contar el número de frases
                            long count = dataSnapshot.getChildrenCount();
                            // Generar un índice aleatorio
                            int index = new Random().nextInt((int) count);
                            // Seleccionar una frase aleatoria
                            DataSnapshot randomQuoteSnapshot = dataSnapshot.child(String.valueOf(index));
                            Quote quote = randomQuoteSnapshot.getValue(Quote.class);
                            quoteCallback.onCallback(quote);
                        } else {
                            quoteCallback.onError("No hay frases disponibles en el idioma seleccionado.");
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        quoteCallback.onError("La lectura fue cancelada debido a un error: " + databaseError.getMessage());
                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                quoteCallback.onError("La lectura fue cancelada debido a un error: " + databaseError.getMessage());
            }
        });

    }
}
