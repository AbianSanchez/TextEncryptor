package EncryptorDecryptor;

import org.junit.jupiter.api.Test;

import javax.crypto.spec.SecretKeySpec;

import static org.junit.jupiter.api.Assertions.*;

class CryptographerTest {
    private Cryptographer crypto = new Cryptographer();
    private SecretKeySpec key = crypto.keyGenerator("Saturno_45");
    private String phrase = "Cryptography systems are based on mathematical problems so complex they cannot be solved without a key";
    private String phraseEncryp = "2AOMUcQQmP9u1c9Jq/heLBEJUWxkisyRwbjyRPoQrJCXicrq+0qE3hk8+XTLuubQSgdelcAZfIVq+2PTwaJ0p9BFPALM3tITD++Tave1TP44Uv8btZO5pVh06zqbJx3nFo6o3p7AG6WoLiTrAU6AGg==";

    @Test
    void test_encrypter() {
        String result = crypto.encrypter(phrase,key);
        assertEquals(phraseEncryp, result);
    }

    @Test
    void test_Fail_encrypter() {
        SecretKeySpec keyTemp = crypto.keyGenerator("Saturno");
        String result = crypto.encrypter(phrase,keyTemp);
        assertNotEquals(phraseEncryp, result);
    }

    @Test
    void test_decrypter() {
        String result = crypto.decrypter(phraseEncryp,key);
        assertEquals(phrase, result);
    }

    @Test
    void test_Fail_decrypter() {
        SecretKeySpec keyTemp = crypto.keyGenerator("Mercurio");
        String result = crypto.decrypter(phraseEncryp,keyTemp);
        assertEquals(phrase, result);
    }


}