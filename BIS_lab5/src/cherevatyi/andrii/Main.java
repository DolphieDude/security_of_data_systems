package cherevatyi.andrii;

import java.io.*;
import java.nio.file.*;
import java.security.*;
import java.security.spec.*;
import javax.crypto.*;

public class Main {
    public static void main(String[] args) {
        String messageFile = "C:/RSA/message.txt";
        String hashsumFile = "C:/RSA/hashsum.txt";
        String senderPublicKeyFile = "C:/RSA/sender_public_key.txt";
        String userPrivateKeyFile = "C:/RSA/user_private_key.txt";

        System.out.println("Choose an option:");
        System.out.println("1. Encrypt and rewrite message.txt using user's private key.");
        System.out.println("2. Decrypt and verify message.txt using sender's public key.");

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String option = null;
        try {
            option = reader.readLine();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        if (option != null) {
            if (option.equals("1")) {
                encryptAndRewriteMessage(messageFile, hashsumFile, userPrivateKeyFile);
            }
            else if (option.equals("2")) {
                decryptAndVerifyMessage(messageFile, hashsumFile, senderPublicKeyFile);
            }
            else {
                System.out.println("Invalid option selected.");
            }
        }
    }

    public static void encryptAndRewriteMessage(String messageFile, String hashsumFile, String userPrivateKeyFile) {
        try {
            byte[] message = Files.readAllBytes(Paths.get(messageFile));
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashSum = digest.digest(message);
                Files.write(Paths.get(hashsumFile), hashSum);
            byte[] userPrivateKeyBytes = Files.readAllBytes(Paths.get(userPrivateKeyFile));
            PrivateKey userPrivateKey = KeyFactory.getInstance("RSA")
                    .generatePrivate(new PKCS8EncodedKeySpec(userPrivateKeyBytes));
            byte[] encryptedMessage = encrypt(message, userPrivateKey);
            Files.write(Paths.get(messageFile), encryptedMessage);
            System.out.println("Message encrypted and rewritten successfully.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void decryptAndVerifyMessage(String messageFile, String hashsumFile, String senderPublicKeyFile) {
        try {
            byte[] encryptedMessage = Files.readAllBytes(Paths.get(messageFile));
            byte[] senderPublicKeyBytes = Files.readAllBytes(Paths.get(senderPublicKeyFile));
            PublicKey senderPublicKey = KeyFactory.getInstance("RSA")
                    .generatePublic(new X509EncodedKeySpec(senderPublicKeyBytes));
            byte[] decryptedMessage = decrypt(encryptedMessage, senderPublicKey);
            byte[] storedHashSum = Files.readAllBytes(Paths.get(hashsumFile));
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] decryptedHashSum = digest.digest(decryptedMessage);
            boolean decryptionCorrect = MessageDigest.isEqual(storedHashSum, decryptedHashSum);
            if (decryptionCorrect) {
                Files.write(Paths.get(messageFile), decryptedMessage);
                System.out.println("Message decrypted and verified successfully.");
            }
            else {
                System.out.println("Decryption failed or message modified.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static byte[] encrypt(byte[] input, PrivateKey privateKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.ENCRYPT_MODE, privateKey);
        return cipher.doFinal(input);
    }

    public static byte[] decrypt(byte[] input, PublicKey publicKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.DECRYPT_MODE, publicKey);
        return cipher.doFinal(input);
    }
}




