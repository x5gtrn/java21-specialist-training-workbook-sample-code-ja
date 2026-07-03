package com.x5gtrn.book.appendix.java21stw300.ch06_io_nio;
import com.x5gtrn.book.appendix.java21stw300.framework.Sample;
import java.io.*;
public final class Q86SerializationSecurity implements Sample {
    record Account(String owner, long balance) implements Serializable {}
    public String chapter(){return "06_IO_NIO";}
    public int problem(){return 86;}
    public String title(){return "Java シリアライゼーションのセキュリティ";}
    public void run() throws Exception {
        Account original = new Account("Alice", 5000);
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        try (ObjectOutputStream oos = new ObjectOutputStream(bytes)) { oos.writeObject(original); }
        System.out.println("直列化サイズ = " + bytes.size() + " bytes");
        try (ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(bytes.toByteArray()))) {
            Account restored = (Account) ois.readObject();
            System.out.println("復元 = " + restored);
        }
        // 危険: 信頼できないデータの readObject は任意コード実行につながり得る。
        // ObjectInputFilter によるクラス許可リストや、JSON など安全な形式の採用を検討する。
        System.out.println("注意: 信頼できない入力を直接 readObject してはならない（ObjectInputFilter で防御）");
    }
}
