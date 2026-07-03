package com.x5gtrn.book.appendix.java21stw300.ch08_memory_gc_performance;
import com.x5gtrn.book.appendix.java21stw300.framework.Sample;
import java.nio.charset.StandardCharsets;
public final class Q109CompactStrings implements Sample {
    public String chapter(){ return "08_Memory_GC_Performance"; }
    public int problem(){ return 109; }
    public String title(){ return "コンパクト文字列（Compact Strings）"; }
    public void run(){
        // Java 9+ の String は内部 byte[] + coder。全文字が Latin-1 に収まれば 1 バイト/文字で格納される。
        String ascii = "Hello World";                 // Latin-1 表現（1バイト/文字）
        String japanese = "こんにちは世界";              // UTF-16 表現（2バイト/文字）
        System.out.println("ASCII 内部表現の目安    : " + ascii.length() + " 文字 ≒ "
                + ascii.getBytes(StandardCharsets.ISO_8859_1).length + " バイト (LATIN1)");
        System.out.println("非ASCII 内部表現の目安  : " + japanese.length() + " 文字 ≒ "
                + japanese.length() * 2 + " バイト (UTF16)");
        System.out.println("効果: 英数字主体のアプリはヒープの文字列領域を最大約半減できる（Java 9 で既定有効）");
        System.out.println("無効化: -XX:-CompactStrings（通常は不要）。非ASCIIを1文字含むと全体がUTF16になる");
    }
}
