package com.x5gtrn.book.appendix.java21stw300.ch02_java21_features;
import com.x5gtrn.book.appendix.java21stw300.framework.Sample;
import java.lang.foreign.Arena;
import java.lang.foreign.MemoryLayout;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.ValueLayout;
public final class Q31ForeignFunctionMemory implements Sample {
    public String chapter(){return "02_Java21_Features";}
    public int problem(){return 31;}
    public String title(){return "Foreign Function & Memory API 概要";}
    @Override public boolean preview(){return true;}
    public void run(){
        // Arena がオフヒープメモリの生存期間を管理（close で確実に解放）
        try (Arena arena = Arena.ofConfined()) {
            // int を 4 要素持つ領域（sequenceLayout で 4 * 4 = 16 バイト）
            MemorySegment segment = arena.allocate(MemoryLayout.sequenceLayout(4, ValueLayout.JAVA_INT));
            for (int i = 0; i < 4; i++) segment.setAtIndex(ValueLayout.JAVA_INT, i, (i + 1) * 10);
            long sum = 0;
            for (int i = 0; i < 4; i++) sum += segment.getAtIndex(ValueLayout.JAVA_INT, i);
            System.out.println("byteSize = " + segment.byteSize() + " bytes");
            System.out.println("値の合計 = " + sum);
        } // ここで確定的に解放される
        System.out.println("Arena クローズ後はセグメントにアクセスできない（安全）");
    }
}
