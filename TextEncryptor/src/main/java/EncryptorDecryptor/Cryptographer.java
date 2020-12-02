package EncryptorDecryptor;

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

public class Cryptographer {


    public SecretKeySpec keyGenerator (String password) {
        try {
            byte[] passwordEncrypter = password.getBytes("UTF-8");
            MessageDigest sha = MessageDigest.getInstance("SHA-1");
            passwordEncrypter = sha.digest(passwordEncrypter);
            passwordEncrypter = Arrays.copyOf(passwordEncrypter, 16);
            SecretKeySpec key = new SecretKeySpec(passwordEncrypter, "AES");
            return key;
        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String encrypter (String data, SecretKeySpec key) {
        try {
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] dataProcess = data.getBytes("UTF-8");
            byte[] bytesProcess = cipher.doFinal(dataProcess);
            return Base64.getEncoder().encodeToString(bytesProcess);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String decrypter (String data, SecretKeySpec key) {
        try {
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, key);
            return new String(cipher.doFinal(Base64.getDecoder().decode(data)));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


}
