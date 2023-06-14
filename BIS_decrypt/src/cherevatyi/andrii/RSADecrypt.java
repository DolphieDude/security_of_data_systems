package cherevatyi.andrii;

import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import javax.crypto.Cipher;

public class RSADecrypt {
    public static void main(String[] args) throws Exception {
        String msgPath, privatePath, hashPath;

        msgPath = "C:\\lab5\\message.txt";
        privatePath = "C:\\lab5\\private.txt";
        hashPath = "C:\\lab5\\hash.txt";

        System.out.println("Дешифрування");
        System.out.print("Впишіть хеш-суму для додаткового порівняння: ");
        Scanner scanner = new Scanner(System.in);
        scanner.next();
        scanner.close();
        byte[] storedHashSum = Files.readAllBytes(Paths.get(hashPath));

        byte[] msgBytes, privateBytes, result, decryptedHash;
        //Зчитування файлу та ключа
        msgBytes = Files.readAllBytes(Paths.get(msgPath));
        privateBytes = Files.readAllBytes(Paths.get(privatePath));
        PrivateKey privateInstance = KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(privateBytes));
        //Дешифрування
        Cipher RSA = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        RSA.init(Cipher.DECRYPT_MODE, privateInstance);
        result = RSA.doFinal(msgBytes);
        Files.write(Paths.get(msgPath), result);
        //Верифікация хеш-суми
        decryptedHash = MessageDigest.getInstance("SHA-256").digest(result);
        System.out.println("Хеш-сума дешифрованого: " + new String(decryptedHash, StandardCharsets.UTF_8));
        if (MessageDigest.isEqual(storedHashSum, decryptedHash)) System.out.println("Хеш-суми співпадають.");
        else System.out.println("Хеш-суми не співпадають, можливо повідомлення підроблене.");
    }
}

