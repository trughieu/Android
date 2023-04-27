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

import vn.mobileid.tse.model.connector.AWSRequest;
import vn.mobileid.tse.model.database.SettingData;
import vn.mobileid.tse.model.logger.Log4jHelper;

public class Dev_activity extends AppCompatActivity {
    public Executor executor;
    public BiometricPrompt biometricPrompt;
    public BiometricPrompt.PromptInfo promptInfo;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setApplicationLocale();
        Log4jHelper.setBuilder(() -> "com.example.sic");
        AWSRequest.setBuilder(() -> "eyJhbGciOiJFQ0RILUVTK0EyNTZLVyIsImVuYyI6IkEyNTZHQ00iLCJlcGsiOnsia3R5IjoiRUMi" + "LCJ4IjoiX1R2SG5wVUZvZ0VGYzdZV2xzYU9TZXhmV0xUMTdrSmtyUEVPRUpzTlZXUSIsInkiOiJ" + "pUm1ieDh6cHJOVmlJUGMta2kyejBHOFI3YV9BUzBQZnJHYlhrazRSRnVjIiwiY3J2IjoiUC0yNT" + "YifX0.MH_0X5EPxZuAjYaEPAufxNM0WBtr1SrjiyZO9rpvEIlqS24fFnKYuA.1Dn5Kr1nWhh4hvJ" + "B.BUu7GW4Uk_cowqcpBgJHh_kO9yoZOZluTMrOdeo2u750nwcSf6n50ilY3IN9UxZtxgR3zlN5D" + "9UHqYXJk5-RW4Dk-D6v4KKi8gb_3NjJTz3cyqbFNKwFXq4sDRtkHG8IwMnKNo3IJvsEyu4fUZKI" + "gvFt4Egzt6797JcOlHD1P__vQotqbasMk_2FZ7Fsbc6rhRJMgoQvOoi_6kJLA2EpEbJcWJoUCrPJ" + "eMv7YvBleaXlg4IGs69U8k-GfD5UTomyTYzhz-4mZbcAkhEOinP80La4KVx380UZlFVMTva4S0fp" + "oEWa2EYnm9Xu1PZ9fJNJkNHnv6Ykz4fvkRcI8EK_aKCXv45MmE2AiUXuM_g2lTWPmM1dFIUc-dwK" + "0tbV72zz_kc-3NKv1JW3Ps4_bmgp6CO1ig3gU1_GoBb73hUJg_drsbnEmSBB-9UYUCDmqRTtxx7a" + "Y4eB9IUfS-DCLP9Is5pvmPmegE9yLuEAi5QspYYL8VgAb0TsEa00ia4D95LxDDBxg6AmR3kuLrjGJ" + "cv12KUq4kfVoErB_EBhNHskMpU8bdITyNssocGE4C2qRc0SVqZWu8qOG55d0b1tsqoOob5p_ao6a" + "sXHKFLHl7ThjZE0R_XrynRiQxSV7eh2UVmkQ8P3GlKwQrUre8QwerEZU-SzUhgAzx6UD8M_ZajfFT" + "L0lNjmQiBg7ems-hXkrqLlXtdQZ2DTsTCtocU6V2c65mzb3B8cFeUiVNWTYyTPf0cOI2i2LOlEX1Gj" + "-FOekPGEvBwFKBjbV6FMDwolwaiEbvzInSn3ywp9dNZQfQUHobV7f0gtbGXPjYPeeg5ekezAbWzoaq" + "Hm4O0.JCOoHrh6CVaoYVacQfgkhg");

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
