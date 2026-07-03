package com.x5gtrn.book.appendix.java21stw300.ch09_jpms_modularity;
import com.x5gtrn.book.appendix.java21stw300.framework.Sample;
import java.lang.reflect.Field;
public final class Q130OpensMinimization implements Sample {
    static final class Entity { private String value = "secret"; }
    public String chapter(){ return "09_JPMS_Modularity"; }
    public int problem(){ return 130; }
    public String title(){ return "opens の最小化とリフレクションフレームワーク"; }
    public void run() throws Exception {
        // Jackson/Hibernate 等は private フィールドへ深いリフレクションを行う → opens が必要
        Field f = Entity.class.getDeclaredField("value");
        f.setAccessible(true); // 無名モジュールでは可。モジュール環境では opens 宣言が要る
        System.out.println("深いリフレクションで取得 -> " + f.get(new Entity()));
        System.out.println("opens com.example.entity to com.fasterxml.jackson.databind;  // 限定的に開放");
        System.out.println("要点: exports(コンパイル時参照) と opens(実行時リフレクション) は別物");
        System.out.println("最小化: 全開放 `opens x;` でなく `opens x to <framework>;` で対象と範囲を絞る");
    }
}
