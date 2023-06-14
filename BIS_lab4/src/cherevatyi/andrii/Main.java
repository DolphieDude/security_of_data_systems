package cherevatyi.andrii;


import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

public class Main {
    private static final String MSG_HEX = "736D6F6C64617279";
    private static final String KEY_HEX = "70617373776F7264";

    private static byte[] convertToBytes(String str) {
        byte[] result = new byte[str.length()/2];
        for (int i = 0;i<str.length();i+=2) {
            result[i/2] = (byte)((Character.digit(str.charAt(i), 16) << 4)+Character.digit(str.charAt(i+1), 16));
        }
        return result;
    }

    private static String convertToHex(byte[] bytes) {
        String result = "";
        for (int i = 0;i<bytes.length;i++) result += String.format("%02X", bytes[i]);
        return result;
    }

    public static void main(String[] args) throws java.io.IOException, java.security.GeneralSecurityException {
        System.out.println("hex вхідне="+MSG_HEX+
                "\nhex пароль="+KEY_HEX);
        byte[] msgByte = convertToBytes(MSG_HEX);
        byte[] keyByte = convertToBytes(KEY_HEX);
        System.out.println("utf-8 вхідне="+new String(msgByte, "UTF-8") +
                "\nutf-8 пароль="+new String(keyByte, "UTF-8"));
        SecretKey secretKey = new SecretKeySpec(keyByte, "DES");
        Cipher des = Cipher.getInstance("DES/ECB/NoPadding");
        des.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] result = des.doFinal(msgByte);
        String resultHex = convertToHex(result);
        System.out.println(resultHex+"\n"+new String(result, "UTF-8"));
    }

}






