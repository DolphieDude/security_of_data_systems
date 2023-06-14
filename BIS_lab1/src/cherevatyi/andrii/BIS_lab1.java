package cherevatyi.andrii;

public class BIS_lab1 {
    final static int KEY = 15;

    public static void main(String[] args)
    {
        String toEncrypt = "А ви думали, що Україна так просто. Україна – це супер. Україна – це\n" +
                "ексклюзив. По ній пройшли всі катки історії. На ній відпрацьовані всі\n" +
                "види випробувань. Вона загартована найвищим гартом. В умовах\n" +
                "сучасного світу їй немає ціни.";

        long time1 = System.currentTimeMillis();
        String encrypted = encryption(toEncrypt);
        long time2 = System.currentTimeMillis();
        System.out.println("Encryption: " + encrypted);
        System.out.println(time2 - time1 + "ms");

        System.out.println("Decryption: " + encryption(encrypted));
    }

    static String encryption(String toEncrypt) {
        String encrypted = "";
        for (int i = 0; i < toEncrypt.length(); i++) encrypted += Character.toString((char) (toEncrypt.charAt(i) ^ KEY));
        return encrypted;
    }

}
