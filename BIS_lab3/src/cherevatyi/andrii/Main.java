package cherevatyi.andrii;

import java.nio.charset.StandardCharsets;

public class Main {
    public static void main(String[] args) {
        String message = "А ви думали, що Україна так просто. Україна – це супер. " +
                "Україна – це ексклюзив. По ній пройшли всі катки історії. На ній відпрацьовані " +
                "всі види випробувань. Вона загартована найвищим гартом. В умовах сучасного світу їй немає ціни. Ліна КОСТЕНКО";
        String key = "110000011110010100110010010110011110";

        long time1 = System.currentTimeMillis();
        byte[] messageBytes = message.getBytes(StandardCharsets.UTF_8);
        byte[] keyBytes = toBytes(key);
        byte[] cipheredBytes = cipher(messageBytes, keyBytes);
        String cipheredMessage = new String(cipheredBytes, StandardCharsets.UTF_8);
        long time2 = System.currentTimeMillis();
        System.out.println("Encrypted message: " + cipheredMessage);
        System.out.println(time2 - time1 + "ms");

        byte[] decipheredBytes = cipher(cipheredBytes, keyBytes);
        String decipheredMessage = new String(decipheredBytes, StandardCharsets.UTF_8);
        System.out.println("Decrypted message: " + decipheredMessage);
    }

    private static byte[] toBytes(String str) {
        int len = str.length() / 6;
        byte[] bytes = new byte[len];
        for (int i = 0; i < len; i++) {
            int val = Integer.parseInt(str.substring(i * 6, (i + 1) * 6), 2);
            bytes[i] = (byte) val;
        }
        return bytes;
    }

    private static byte[] cipher(byte[] messageBytes, byte[] keyBytes) {
        byte[] cipheredBytes = new byte[messageBytes.length];
        for (int i = 0; i < messageBytes.length; i++) {
            cipheredBytes[i] = (byte) (messageBytes[i] ^ keyBytes[i % keyBytes.length]);
        }
        return cipheredBytes;
    }
}

