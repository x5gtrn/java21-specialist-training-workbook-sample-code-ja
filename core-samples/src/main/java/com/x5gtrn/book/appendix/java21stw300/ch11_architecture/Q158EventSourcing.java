package com.x5gtrn.book.appendix.java21stw300.ch11_architecture;
import com.x5gtrn.book.appendix.java21stw300.framework.Sample;
import java.util.ArrayList;
import java.util.List;
public final class Q158EventSourcing implements Sample {
    // 状態ではなく「起きた事実（イベント）」を追記保存し、畳み込みで現在状態を再構築する
    sealed interface AccountEvent permits Opened, Deposited, Withdrawn {}
    record Opened(String owner) implements AccountEvent {}
    record Deposited(long amount) implements AccountEvent {}
    record Withdrawn(long amount) implements AccountEvent {}
    record Account(String owner, long balance) {
        Account apply(AccountEvent e){
            return switch (e) {
                case Opened o -> new Account(o.owner(), 0);
                case Deposited d -> new Account(owner, balance + d.amount());
                case Withdrawn w -> new Account(owner, balance - w.amount());
            };
        }
    }
    public String chapter(){return "11_Architecture";}
    public int problem(){return 158;}
    public String title(){return "イベントソーシングの適用判断";}
    public void run(){
        List<AccountEvent> eventStore = new ArrayList<>(List.of(
            new Opened("Alice"), new Deposited(1000), new Withdrawn(300), new Deposited(500)));
        Account state = new Account("", 0);
        for (AccountEvent e : eventStore) { state = state.apply(e); System.out.println("apply " + e + " -> " + state); }
        System.out.println("再構築された現在状態 = " + state + "（イベント列が唯一の真実）");
    }
}
