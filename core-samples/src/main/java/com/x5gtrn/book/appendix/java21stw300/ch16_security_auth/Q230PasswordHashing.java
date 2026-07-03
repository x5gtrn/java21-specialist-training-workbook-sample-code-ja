package com.x5gtrn.book.appendix.java21stw300.ch16_security_auth;
import com.x5gtrn.book.appendix.java21stw300.framework.Sample;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Base64;
public final class Q230PasswordHashing implements Sample {
    public String chapter(){return "16_Security_Auth";}
    public int problem(){return 230;}
    public String title(){return "パスワードハッシュの選択";}
    public void run() throws Exception {
        String password = "correct horse battery staple";
        byte[] salt = new byte[16]; new SecureRandom().nextBytes(salt);   // ユーザーごとにランダムな salt
        byte[] hash = pbkdf2(password, salt);
        System.out.println("salt = " + Base64.getEncoder().encodeToString(salt));
        System.out.println("hash = " + Base64.getEncoder().encodeToString(hash));
        // 検証は同じ salt で再計算し、定数時間比較（タイミング攻撃対策）
        boolean ok = MessageDigest.isEqual(hash, pbkdf2(password, salt));
        boolean ng = MessageDigest.isEqual(hash, pbkdf2("wrong", salt));
        System.out.println("正しいパスワード -> " + ok + " / 誤り -> " + ng);
        System.out.println("MD5/SHA-256 単体は高速すぎて総当りに弱い。PBKDF2/bcrypt/Argon2 等の低速・適応的KDFを使う");
    }
    private byte[] pbkdf2(String pw, byte[] salt) throws Exception {
        var spec = new PBEKeySpec(pw.toCharArray(), salt, 100_000, 256); // 反復回数で意図的に低速化
        return SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256").generateSecret(spec).getEncoded();
    }
}
