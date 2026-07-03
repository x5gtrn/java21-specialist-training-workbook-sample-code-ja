package com.x5gtrn.book.appendix.java21stw300.ch09_jpms_modularity;
import com.x5gtrn.book.appendix.java21stw300.framework.Sample;
import java.lang.reflect.Field;
public final class Q129JpmsReflectionAccess implements Sample {
    static final class Secret { private String token = "s3cr3t"; }
    public String chapter(){return "09_JPMS_Modularity";}
    public int problem(){return 129;}
    public String title(){return "JPMS とリフレクションアクセス";}
    public void run() throws Exception {
        Secret s = new Secret();
        Field f = Secret.class.getDeclaredField("token");
        f.setAccessible(true); // 同一(無名)モジュール内なので許可される
        System.out.println("リフレクションで private を読取 -> " + f.get(s));
        // モジュール化された JAR では、外部からの setAccessible は
        // `opens パッケージ` 宣言か実行時 --add-opens が無いと InaccessibleObjectException になる。
        System.out.println("モジュール環境では open していない package への深いリフレクションは拒否される");
    }
}
