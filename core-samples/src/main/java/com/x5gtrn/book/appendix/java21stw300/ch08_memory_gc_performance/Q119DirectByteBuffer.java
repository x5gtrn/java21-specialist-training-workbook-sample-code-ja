package com.x5gtrn.book.appendix.java21stw300.ch08_memory_gc_performance;
import com.x5gtrn.book.appendix.java21stw300.framework.Sample;
import java.nio.ByteBuffer;
public final class Q119DirectByteBuffer implements Sample {
    public String chapter(){ return "08_Memory_GC_Performance"; }
    public int problem(){ return 119; }
    public String title(){ return "DirectByteBuffer とオフヒープメモリ"; }
    public void run(){
        ByteBuffer heap = ByteBuffer.allocate(1024);         // JVMヒープ上
        ByteBuffer direct = ByteBuffer.allocateDirect(1024); // ヒープ外(ネイティブ)
        System.out.println("allocate       -> isDirect=" + heap.isDirect());
        System.out.println("allocateDirect -> isDirect=" + direct.isDirect());
        System.out.println("Direct の利点: I/O時にヒープ⇔ネイティブのコピーを省け、GC圧を受けない");
        System.out.println("Direct の注意: 割当/解放が高コスト、解放は Cleaner 任せで遅延。長寿命I/Oバッファ向け");
        System.out.println("上限は -XX:MaxDirectMemorySize。リークすると OOM: Direct buffer memory を招く");
    }
}
