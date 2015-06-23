package com.example.danijel.security;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;


public class MainActivity extends ActionBarActivity {

    TextView inputXml;
    TextView encryptText;
    TextView decryptText;

    byte[] input;
    Cipher cipher;
    SecretKeySpec aesKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inputXml = (TextView)findViewById(R.id.input_text);
        encryptText = (TextView)findViewById(R.id.encrypt_text);
        decryptText = (TextView)findViewById(R.id.decrypt_text);

        try {
            cipher = Cipher.getInstance("AES");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        }
        SecretKey key = null;
        try {
            key = generateKey();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        aesKey = new SecretKeySpec(key.getEncoded(), "AES");
    }

    private static byte[] encrypt(SecretKeySpec raw, byte[] clear) throws Exception {
        SecretKeySpec skeySpec = raw;//new SecretKeySpec(raw, "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
        byte[] encrypted = cipher.doFinal(clear);
        return encrypted;
    }

    private static byte[] decrypt(SecretKeySpec raw, byte[] encrypted) throws Exception {
        SecretKeySpec skeySpec = raw;//new SecretKeySpec(raw, "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, skeySpec);
        byte[] decrypted = cipher.doFinal(encrypted);
        return decrypted;
    }


    public void encrypt(View view) throws Exception {

        input = inputXml.getText().toString().getBytes();
        byte[] encryptedData = encrypt(aesKey,input);
        input = encryptedData;
        String value = new String(encryptedData, "UTF-8");

        encryptText.setText("Encrypted: " + value);
    }

    public void decrypt(View view) throws Exception {
        byte[] decryptedData = decrypt(aesKey, input);
        String value = new String(decryptedData, "UTF-8");
        decryptText.setText("Decrypted: " + value);
    }

    private static SecretKey generateKey() throws NoSuchAlgorithmException {
        KeyGenerator keygen;

        keygen = KeyGenerator.getInstance("AES");
        keygen.init(128);
        SecretKey aesKey = keygen.generateKey();
        return aesKey;
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
