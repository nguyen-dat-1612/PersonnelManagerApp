package com.managerapp.personnelmanagerapp.data.local;

import android.util.Base64;

import javax.inject.Inject;
import javax.inject.Singleton;

public class EncryptionManager {
    public EncryptionManager() {

    }

    public String encrypt(String data) {
        return Base64.encodeToString(data.getBytes(), Base64.DEFAULT);
    }

    public String decrypt(String encryptedData) {
        return new String(Base64.decode(encryptedData, Base64.DEFAULT));
    }
}
