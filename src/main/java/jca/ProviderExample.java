package jca;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.Security;

/**
 * {@link ProviderExample} http://tutorials.jenkov.com/java-cryptography/index.html
 */
public class ProviderExample {

    public static void main(String[] args) throws Exception {
        Security.addProvider(new BouncyCastleProvider());

        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        byte[] keyBytes = new byte[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15};
        String algorithm = "RawBytes";
        SecretKeySpec key = new SecretKeySpec(keyBytes, algorithm);
        cipher.init(Cipher.ENCRYPT_MODE, key);

        byte[] plainText = "abcdefghijklmnopqrstuvwxyz".getBytes("UTF-8");
        byte[] cipherText = cipher.doFinal(plainText);
        System.out.println(new String(plainText));
        System.out.println(new String(cipherText));
    }

}
