package com.x5gtrn.book.appendix.java21stw300.ch16_security_auth;
import com.x5gtrn.book.appendix.java21stw300.framework.Sample;
import java.io.*;
public final class Q246UnsafeDeserialization implements Sample {
    record Dto(String name, int value) implements Serializable {}
    public String chapter(){return "16_Security_Auth";}
    public int problem(){return 246;}
    public String title(){return "安全でないデシリアライゼーション";}
    public void run() throws Exception {
        byte[] bytes;
        try (var bos = new ByteArrayOutputStream(); var oos = new ObjectOutputStream(bos)) {
            oos.writeObject(new Dto("ok", 1)); oos.flush(); bytes = bos.toByteArray();
        }
        // 許可リスト方式の ObjectInputFilter で、想定クラス以外の復元を拒否する
        System.out.println("許可クラスのみ: " + readWithFilter(bytes, "com.x5gtrn.**;java.lang.*;!*"));
        System.out.println("全拒否フィルタ: " + readWithFilter(bytes, "!*"));
        System.out.println("信頼できない入力の readObject はガジェット連鎖で RCE を招く。許可リストか安全な形式(JSON)を使う");
    }
    private String readWithFilter(byte[] bytes, String pattern){
        try (var ois = new ObjectInputStream(new ByteArrayInputStream(bytes))) {
            ois.setObjectInputFilter(ObjectInputFilter.Config.createFilter(pattern));
            return "復元成功 -> " + ois.readObject();
        } catch (Exception e) { return "復元拒否 (" + e.getClass().getSimpleName() + ")"; }
    }
}
