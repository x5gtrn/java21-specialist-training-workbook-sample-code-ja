package com.x5gtrn.book.appendix.java21stw300.ch01_language_oop;

import com.x5gtrn.book.appendix.java21stw300.framework.Sample;

/**
 * 問題12: sealed 型 + permits + switch の網羅性保証。
 *
 * permits に列挙された型のみが実装でき、switch 式は全サブタイプを処理すれば default 不要。
 * permits 外の型（例: PayPal）を作るとコンパイルエラーになる。
 */
public final class Q12SealedTypes implements Sample {

    sealed interface Payment permits CreditCard, BankTransfer, Crypto {}
    record CreditCard(String number) implements Payment {}
    record BankTransfer(String iban) implements Payment {}
    static final class Crypto implements Payment {
        final String walletAddress;
        Crypto(String walletAddress) { this.walletAddress = walletAddress; }
    }
    // class PayPal implements Payment {}
    //   -> コンパイルエラー: class is not allowed to extend sealed class: Payment

    public String chapter() { return "01_Language_OOP"; }
    public int problem()    { return 12; }
    public String title()   { return "sealed 型と switch の網羅性"; }

    public void run() {
        Payment[] payments = {
            new CreditCard("4111-1111-1111-1111"),
            new BankTransfer("DE89370400440532013000"),
            new Crypto("0xABCDEF")
        };
        for (Payment p : payments) {
            // default が無くても sealed + permits で網羅性が保証される
            double feeRate = switch (p) {
                case CreditCard cc   -> 0.03;
                case BankTransfer bt -> 0.01;
                case Crypto c        -> 0.005;
            };
            System.out.printf("%-30s fee=%.3f%n", p.getClass().getSimpleName(), feeRate);
        }
    }
}
