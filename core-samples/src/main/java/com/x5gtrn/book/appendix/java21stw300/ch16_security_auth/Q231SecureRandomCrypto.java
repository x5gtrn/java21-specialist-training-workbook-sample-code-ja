package com.x5gtrn.book.appendix.java21stw300.ch16_security_auth;
import com.x5gtrn.book.appendix.java21stw300.framework.Sample;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import java.security.SecureRandom;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Arrays;
public final class Q231SecureRandomCrypto implements Sample {
    public String chapter(){return "16_Security_Auth";}
    public int problem(){return 231;}
    public String title(){return "セキュアな乱数生成と暗号化ベストプラクティス";}
    public void run() throws Exception {
        SecureRandom random = new SecureRandom();          // 予測不能な乱数（Random は暗号用途に不可）
        KeyGenerator kg = KeyGenerator.getInstance("AES"); kg.init(256, random);
        SecretKey key = kg.generateKey();
        byte[] iv = new byte[12]; random.nextBytes(iv);    // GCM は 96bit IV。毎回ランダムに生成する
        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
        cipher.init(Cipher.ENCRYPT_MODE, key, new GCMParameterSpec(128, iv));
        byte[] plain = "secret message".getBytes(StandardCharsets.UTF_8);
        byte[] encrypted = cipher.doFinal(plain);
        System.out.println("暗号文(Base64) = " + Base64.getEncoder().encodeToString(encrypted));
        Cipher dec = Cipher.getInstance("AES/GCM/NoPadding");
        dec.init(Cipher.DECRYPT_MODE, key, new GCMParameterSpec(128, iv));
        byte[] decrypted = dec.doFinal(encrypted);
        System.out.println("復号一致 = " + Arrays.equals(plain, decrypted) + " -> " + new String(decrypted, StandardCharsets.UTF_8));
    }
}
