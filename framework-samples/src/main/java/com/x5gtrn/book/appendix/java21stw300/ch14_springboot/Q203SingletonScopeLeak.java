package com.x5gtrn.book.appendix.java21stw300.ch14_springboot;
import com.x5gtrn.book.appendix.java21stw300.springapp.FrameworkSample;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;
@Component
public class Q203SingletonScopeLeak implements FrameworkSample {
    public String chapter(){ return "14_SpringBoot"; }
    public int problem(){ return 203; }
    public String title(){ return "シングルトンスコープとメモリリーク"; }
    public void run(){
        // Bean は既定でシングルトン。可変の状態を貯め込み続けると解放されずリークする（デモは局所変数で再現）
        List<byte[]> unbounded = new ArrayList<>();   // 破棄されないコレクションのつもり
        for (int i=0;i<1000;i++) unbounded.add(new byte[1024]);
        System.out.println("上限なしで蓄積した要素数 = " + unbounded.size() + "（シングルトン内なら永続的に残存）");
        System.out.println("危険例: シングルトンBeanのフィールドに List/Map をキャッシュ代わりに無制限追加");
        System.out.println("対策: 状態を持たない設計 / 上限付きキャッシュ(Caffeine等)でエビクション / スコープの見直し");
    }
}
