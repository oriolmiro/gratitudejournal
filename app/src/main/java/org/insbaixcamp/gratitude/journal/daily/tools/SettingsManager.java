package org.insbaixcamp.gratitude.journal.daily.tools;

import android.app.ActivityManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.insbaixcamp.gratitude.journal.daily.model.User;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Locale;
import java.util.TimeZone;

public class SettingsManager {
    private static final String KEY_USER_NAME = "user_name";
    private final String PREF_NAME;
    private static final String KEY_OPEN_COUNT = "open_count"; // Clave para almacenar el contador
    private static final String KEY_USER_ID = "user_id";
    private static final String KEY_ONBOARDING_FINISH = "onboarding_finish"; // Nueva clave
    private final SharedPreferences sharedPreferences;
    private final SharedPreferences.Editor editor;
    private final FirebaseAuth firebaseAuth;
    public String userId;
    private Context context;

    public SettingsManager(Context context) {
        PREF_NAME = context.getPackageName().replaceAll("\\.", "_") + "_prefs";
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        firebaseAuth = FirebaseAuth.getInstance();
        editor = sharedPreferences.edit();
        this.context = context;
    }

    public int addCount() {
        // Obtiene el valor actual del contador
        int currentCount = sharedPreferences.getInt(KEY_OPEN_COUNT, 0);
        //Toast.makeText(this.context,"count: "+ currentCount,Toast.LENGTH_LONG).show();
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
    public String getUserId() {
        // Obtiene el valor de "user_id" desde SharedPreferences
        return sharedPreferences.getString(KEY_USER_ID, null);
    }
    public String getUserName() {
        // Obtiene el valor de "user_id" desde SharedPreferences
        return sharedPreferences.getString(KEY_USER_NAME, null);
    }
    public void setUserName(String name) {

        sharedPreferences.edit().putString(KEY_USER_NAME, name).apply();
    }
    public JSONObject gatherDeviceInfo() {
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("androidVersion", getAndroidVersion());
            jsonObject.put("deviceBrand", getDeviceBrand());
            jsonObject.put("deviceModel", getDeviceModel());
            jsonObject.put("deviceLanguage", getDeviceLanguage());
            jsonObject.put("deviceCountry", getDeviceCountry());
            jsonObject.put("deviceTimezone", getDeviceTimezone());
            jsonObject.put("screenResolution", getScreenResolution());
            jsonObject.put("screenDensity", getScreenDensity());
            jsonObject.put("screenOrientation", getScreenOrientation());
            jsonObject.put("connectivityType", getConnectivityType());
            jsonObject.put("appVersionName", getAppVersionName());
            jsonObject.put("appVersionCode", getAppVersionCode());
            jsonObject.put("cpuType", getCpuType());
            jsonObject.put("totalRAM", getTotalRAM());

            // ... puedes continuar agregando más parámetros si lo deseas.

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObject;
    }
    private String getAndroidVersion() {
        return Build.VERSION.RELEASE;
    }

    private String getDeviceBrand() {
        return Build.BRAND;
    }

    private String getDeviceModel() {
        return Build.MODEL;
    }

    private String getDeviceLanguage() {
        return Locale.getDefault().getLanguage();
    }

    private String getDeviceCountry() {
        return Locale.getDefault().getCountry();
    }

    private String getDeviceTimezone() {
        return TimeZone.getDefault().getID();
    }

    private String getScreenResolution() {
        DisplayMetrics metrics = new DisplayMetrics();
        ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getMetrics(metrics);
        return metrics.widthPixels + "x" + metrics.heightPixels;
    }

    private float getScreenDensity() {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return metrics.density;
    }

    private String getScreenOrientation() {
        int orientation = context.getResources().getConfiguration().orientation;
        return orientation == 1 ? "Portrait" : "Landscape";
    }

    private String getConnectivityType() {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null ? activeNetwork.getTypeName() : "None";
    }

    private String getAppVersionName() {
        try {
            PackageInfo pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return pInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "Unknown";
        }
    }

    private int getAppVersionCode() {
        try {
            PackageInfo pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return pInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return -1;
        }
    }

    private String getCpuType() {
        return Build.CPU_ABI;
    }

    private long getTotalRAM() {
        ActivityManager actManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo memInfo = new ActivityManager.MemoryInfo();
        actManager.getMemoryInfo(memInfo);
        return memInfo.totalMem;
    }
    public void saveUserToFirebase() {
        // Obtiene una referencia a la base de datos de Firebase
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        database.getReference("users").child(this.getUserId()).setValue(new User(this.getUserId(),this.getUserName(),this.gatherDeviceInfo()));

    }
}
