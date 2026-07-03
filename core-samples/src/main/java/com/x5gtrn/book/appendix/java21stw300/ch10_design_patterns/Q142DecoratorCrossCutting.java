package com.x5gtrn.book.appendix.java21stw300.ch10_design_patterns;
import com.x5gtrn.book.appendix.java21stw300.framework.Sample;
public final class Q142DecoratorCrossCutting implements Sample {
    interface Repository { String find(String id); }
    static final class RealRepository implements Repository {
        public String find(String id){ return "data(" + id + ")"; }
    }
    // 横断的関心事（ログ/計測）を本体に手を入れず追加するデコレータ
    static final class LoggingRepository implements Repository {
        private final Repository delegate;
        LoggingRepository(Repository d){ this.delegate = d; }
        public String find(String id){
            long t = System.nanoTime();
            String r = delegate.find(id);
            System.out.println("  [log] find(" + id + ") -> " + r + " (" + (System.nanoTime()-t) + "ns)");
            return r;
        }
    }
    public String chapter(){return "10_Design_Patterns";}
    public int problem(){return 142;}
    public String title(){return "Decorator Pattern と横断的関心事";}
    public void run(){
        Repository repo = new LoggingRepository(new RealRepository());
        System.out.println("結果 = " + repo.find("42"));
    }
}
