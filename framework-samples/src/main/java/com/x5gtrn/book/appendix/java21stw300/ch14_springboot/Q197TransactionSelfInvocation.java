package com.x5gtrn.book.appendix.java21stw300.ch14_springboot;
import com.x5gtrn.book.appendix.java21stw300.springapp.FrameworkSample;
import org.springframework.stereotype.Component;
@Component
public class Q197TransactionSelfInvocation implements FrameworkSample {
    public String chapter(){ return "14_SpringBoot"; }
    public int problem(){ return 197; }
    public String title(){ return "@Transactional の自己呼び出し"; }
    public void run(){
        // Spring の @Transactional は「プロキシ」で効く。同一Bean内の this.method() 呼び出しはプロキシを通らない。
        outer();
        System.out.println("問題: outer() から this.inner() を呼ぶと inner の @Transactional が効かない");
        System.out.println("理由: プロキシは外部からの呼び出しにのみ介入し、内部の直接呼び出しは素通りする");
        System.out.println("対策: 別Beanへ分離 / 自己注入(ObjectProvider) / AopContext.currentProxy() / プログラム的Tx");
    }
    private void outer(){ inner(); }                 // ← プロキシを経由しない自己呼び出し
    private void inner(){ /* @Transactional を付けても効かない */ }
}
