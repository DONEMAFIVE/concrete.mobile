package ru.zzbo.concretemobile.utils;

import android.widget.Toast;

import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;

public class CryptoUtil {

    private String keyCode;
    final String password = "tyhh&676ddsdsb4324dfdKK";
    final String salt = "1454";

    public CryptoUtil(String keyCode) {
        this.keyCode = keyCode;
    }

    //зашифровать
    public String encrypt() {
        TextEncryptor encryptor = Encryptors.text(password, salt);
        String cipherText = encryptor.encrypt(this.keyCode);
        return cipherText;
    }

    //расшифровать
    public String decrypt() {
        try {
            TextEncryptor encryptor = Encryptors.text(password, salt);
            return encryptor.decrypt(keyCode);
        } catch (IllegalArgumentException | IllegalStateException e) {
            e.printStackTrace();
//            Toast.makeText(null, "Нарушение протокола безопасности программы. Обратитесь к поставщику программного обеспечения!", Toast.LENGTH_LONG).show();
            return null;
        }
    }
}
