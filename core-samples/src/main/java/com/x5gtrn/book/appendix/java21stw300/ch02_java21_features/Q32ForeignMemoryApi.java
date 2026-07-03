package com.x5gtrn.book.appendix.java21stw300.ch02_java21_features;
import com.x5gtrn.book.appendix.java21stw300.framework.Sample;
import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.ValueLayout;
public final class Q32ForeignMemoryApi implements Sample {
    public String chapter(){ return "02_Java21_Features"; }
    public int problem(){ return 32; }
    public String title(){ return "Foreign Function & Memory API のメモリ安全性"; }
    public boolean preview(){ return true; } // Java 21 では Preview（--enable-preview 必須）
    public void run(){
        // Arena がネイティブメモリの生存期間を管理。try-with-resources を抜けると自動解放される。
        try (Arena arena = Arena.ofConfined()) {
            MemorySegment seg = arena.allocateArray(ValueLayout.JAVA_LONG, 4);
            for (int i = 0; i < 4; i++) seg.setAtIndex(ValueLayout.JAVA_LONG, i, (i + 1) * 100L);
            System.out.println("seg[2] = " + seg.getAtIndex(ValueLayout.JAVA_LONG, 2));
            try {
                seg.getAtIndex(ValueLayout.JAVA_LONG, 4); // 範囲外アクセスは境界チェックで弾かれる
            } catch (IndexOutOfBoundsException e) {
                System.out.println("範囲外アクセスを検出: 空間安全性が保証される（sun.misc.Unsafe と異なる）");
            }
        } // ここでネイティブメモリ解放（時間安全性: 解放後アクセスは IllegalStateException）
        System.out.println("FFM は Unsafe に代わる安全なオフヒープ/ネイティブ連携の標準API（21ではPreview）");
    }
}
