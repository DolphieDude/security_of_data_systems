package cherevatyi.andrii;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.*;
import java.security.spec.X509EncodedKeySpec;
import javax.crypto.Cipher;

public class RSAEncrypt {
    public static void main(String[] args) throws Exception {
        String msgPath, publicPath;

        msgPath = "C:\\lab5\\message.txt";
        publicPath = "C:\\lab5\\public.txt";
        String hashPath = "C:\\lab5\\hash.txt";

        System.out.println("Шифрування");

        byte[] msgBytes, hashcode, publicBytes;
        //Зчитування файлу та визначення хеш-суми
        msgBytes = Files.readAllBytes(Paths.get(msgPath));
        hashcode = MessageDigest.getInstance("SHA-256").digest(msgBytes);
        Files.write(Paths.get(hashPath), hashcode);
        //Зчитування ключа
        publicBytes = Files.readAllBytes(Paths.get(publicPath));
        PublicKey publicInstance = KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(publicBytes));
        //Шифрування та збереження
        Cipher RSA = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        RSA.init(Cipher.ENCRYPT_MODE, publicInstance);
        Files.write(Paths.get(msgPath), RSA.doFinal(msgBytes));

        System.out.println("Хеш-сума вашого повідомлення: " + new String(hashcode, StandardCharsets.UTF_8));
    }
}

