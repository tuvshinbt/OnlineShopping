/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.mum.cs490.project.utils;

/**
 * @author tuvshuu
 */

import org.springframework.stereotype.Component;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;

@Component
public class AESConverter {

    private SecretKeySpec secretKey;
    private byte[] key;

    private String secretKeyWord;

    public AESConverter() {
    }

    public AESConverter(String secretKeyWord) {
        this.secretKeyWord = secretKeyWord;
    }

    public void setKey() {
        MessageDigest sha = null;
        try {
            key = secretKeyWord.getBytes("UTF-8");
            sha = MessageDigest.getInstance("SHA-1");
            key = sha.digest(key);
            key = Arrays.copyOf(key, 16);
            secretKey = new SecretKeySpec(key, "AES");
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            System.err.println(e);
        }
    }

    public String encrypt(String strToEncrypt) {
        try {
            if (secretKeyWord != null && !secretKeyWord.isEmpty()) {
                setKey();
                Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
                cipher.init(Cipher.ENCRYPT_MODE, secretKey);
                return Base64.getEncoder().encodeToString(cipher.doFinal(strToEncrypt.getBytes("UTF-8")));
            } else {
                return strToEncrypt;
            }
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | UnsupportedEncodingException | IllegalBlockSizeException | BadPaddingException e) {
            System.err.println("Error while encrypting: " + e.toString());
        }
        return null;
    }

    public String decrypt(String strToDecrypt) {
        try {
            if (secretKeyWord != null && !secretKeyWord.isEmpty()) {
                setKey();
                Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
                cipher.init(Cipher.DECRYPT_MODE, secretKey);
                return new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));
            } else {
                return strToDecrypt;
            }
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
            System.err.println("Error while decrypting: " + e.toString());
        }
        return null;
    }
}
