package com.example.sic;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricPrompt;

import java.util.Locale;
import java.util.concurrent.Executor;

import vn.mobileid.tse.model.database.SettingData;

public class Dev_activity extends AppCompatActivity {
    public Executor executor;
    public BiometricPrompt biometricPrompt;
    public BiometricPrompt.PromptInfo promptInfo;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setApplicationLocale();
    }

    public void setApplicationLocale() {
        String locale = SettingData.getLanguage(this);
        Resources resources = getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        Configuration config = resources.getConfiguration();
        config.setLocale(new Locale(locale.toLowerCase()));
        resources.updateConfiguration(config, dm);
    }
}
