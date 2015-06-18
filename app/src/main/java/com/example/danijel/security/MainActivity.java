package com.example.danijel.security;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
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

    byte[] encrypted;

    public void encrypt() throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException
    {
        cipher.init(Cipher.ENCRYPT_MODE, aesKey);
        encrypted = cipher.doFinal(input);
        encryptText.setText("Encrypted: " + new String(Base64.getEncoder().encodeToString(encrypted)));
    }

    public void decrypt() throws InvalidKeyException, BadPaddingException, IllegalBlockSizeException
    {

        cipher.init(Cipher.DECRYPT_MODE, aesKey);
        String decrypted = new String(cipher.doFinal(encrypted));
        decryptText.setText("Decrypted: " + decrypted);

    }

    private static SecretKey generateKey() throws NoSuchAlgorithmException
    {
        KeyGenerator keygen;

        keygen = KeyGenerator.getInstance("AES");
        keygen.init(128);
        SecretKey aesKey = keygen.generateKey();
        return aesKey;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inputXml = (TextView)findViewById(R.id.input_text);
        encryptText = (TextView)findViewById(R.id.encrypt_text);
        decryptText = (TextView)findViewById(R.id.decrypt_text);

       input = inputXml.getText().toString().getBytes();

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
        SecretKeySpec aesKey = new SecretKeySpec(key.getEncoded(), "AES");



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
