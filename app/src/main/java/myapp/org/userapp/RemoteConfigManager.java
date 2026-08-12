package myapp.org.userapp;

import android.content.Context;

import androidx.annotation.NonNull;

import com.google.firebase.remoteconfig.ConfigUpdate;
import com.google.firebase.remoteconfig.ConfigUpdateListener;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigException;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigValue;

import java.util.HashMap;
import java.util.Map;

public class RemoteConfigManager {
    public interface FetchCallback {
        void onSuccess();
        void onFailure(String error);
    }

    private static RemoteConfigManager instance;
    private final FirebaseRemoteConfig remoteConfig;

    private RemoteConfigManager(Context context) {
        remoteConfig = FirebaseRemoteConfig.getInstance();
        Map<String, Object> defaults = new HashMap<>();
        remoteConfig.setDefaultsAsync(defaults);
        remoteConfig.addOnConfigUpdateListener(new ConfigUpdateListener() {
            @Override
            public void onUpdate(@NonNull ConfigUpdate configUpdate) {
                remoteConfig.activate();
            }

            @Override
            public void onError(@NonNull FirebaseRemoteConfigException error) {
            }
        });
    }

    public static synchronized RemoteConfigManager getInstance(Context context) {
        if (instance == null) {
            instance = new RemoteConfigManager(context.getApplicationContext());
        }
        return instance;
    }

    public void fetchAndActivate(FetchCallback callback) {
        remoteConfig.fetchAndActivate()
            .addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    if (callback != null) {
                        callback.onSuccess();
                    }
                } else {
                    if (callback != null) {
                        String error = task.getException() != null ? task.getException().getMessage() : "Unknown error";
                        callback.onFailure(error);
                    }
                }
            });
    }

    public String getString(String key) {
        return remoteConfig.getString(key);
    }

    public boolean getBoolean(String key) {
        return remoteConfig.getBoolean(key);
    }

    public long getLong(String key) {
        return remoteConfig.getLong(key);
    }

    public double getDouble(String key) {
        return remoteConfig.getDouble(key);
    }
}
