package org.insbaixcamp.gratitude.journal.daily.tools;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SettingsManager {
    private final String PREF_NAME;
    private static final String KEY_OPEN_COUNT = "open_count"; // Clave para almacenar el contador
    private static final String KEY_USER_ID = "user_id";
    private static final String KEY_ONBOARDING_FINISH = "onboarding_finish"; // Nueva clave
    private final SharedPreferences sharedPreferences;
    private final SharedPreferences.Editor editor;
    private final FirebaseAuth firebaseAuth;
    public String userId;

    public SettingsManager(Context context) {
        PREF_NAME = context.getPackageName().replaceAll("\\.", "_") + "_prefs";
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        firebaseAuth = FirebaseAuth.getInstance();
        editor = sharedPreferences.edit();
    }

    public int addCount() {
        // Obtiene el valor actual del contador
        int currentCount = sharedPreferences.getInt(KEY_OPEN_COUNT, 0);

        // Incrementa el contador en uno
        currentCount++;

        // Guarda el nuevo valor del contador en SharedPreferences
        editor.putInt(KEY_OPEN_COUNT, currentCount);
        editor.apply();

        // Retorna el valor actualizado del contador
        return currentCount;
    }
    public void userAuth(){
        firebaseAuth.signInAnonymously()
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        FirebaseUser user = authResult.getUser();
                        if (user != null) {
                            // Llamada de éxito: se ha iniciado sesión de forma anónima
                            userId = user.getUid();

                        } else {
                            // Manejar el caso en el que el usuario sea nulo
                            userId = new String();
                        }
                        editor.putString(KEY_USER_ID, userId).apply();
                        editor.apply();
                    }
                });
    }
    public void onboardingFinish() {
        // Establece la variable "onboarding_finish" en SharedPreferences como "true"
        sharedPreferences.edit().putBoolean(KEY_ONBOARDING_FINISH, true).apply();
    }
    public boolean isOnboardingFinished() {
        // Obtiene el valor de la variable "onboarding_finish" desde SharedPreferences
        return sharedPreferences.getBoolean(KEY_ONBOARDING_FINISH, false);
    }
}
