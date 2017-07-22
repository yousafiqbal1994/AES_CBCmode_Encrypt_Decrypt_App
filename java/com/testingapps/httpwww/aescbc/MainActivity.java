package com.testingapps.httpwww.aescbc;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.scottyab.aescrypt.AESCrypt;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MainActivity extends AppCompatActivity {

    TextView msg,key,result;
    String encryptedMsg;
    Button encrypt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        msg = (TextView) findViewById(R.id.msg);
        key = (TextView) findViewById(R.id.key);
        result = (TextView) findViewById(R.id.result);
        encrypt = (Button) findViewById(R.id.button);
        encrypt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String originalKey = key.getText().toString();
                    String hashedKey = computeHashedKeyfromInputKey(key.getText().toString());
                    encryptTheMessagewithNewkey(originalKey,hashedKey, msg.getText().toString());
                } catch (GeneralSecurityException e) {
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    private String computeHashedKeyfromInputKey(String key) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(key.getBytes("UTF-8")); // Change this to "UTF-16" if needed
        byte[] digest = md.digest();
        return String.format("%064x", new java.math.BigInteger(1, digest));
    }
    private void encryptTheMessagewithNewkey(String originalKey, String hashedKey, String message) throws GeneralSecurityException {
        encryptedMsg = AESCrypt.encrypt(hashedKey, message);
        result.setText("Result \n"+"Message = " +message+"\n"+"Original key = " +originalKey+"\n"+"Hashed Key = " +hashedKey+"\n"+"Encrypted message = " + encryptedMsg);
    }
}

