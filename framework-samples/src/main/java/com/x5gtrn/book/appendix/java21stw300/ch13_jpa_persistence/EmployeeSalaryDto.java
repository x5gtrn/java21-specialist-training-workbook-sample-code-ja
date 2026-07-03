package com.x5gtrn.book.appendix.java21stw300.ch13_jpa_persistence;
/** JPQL コンストラクタ式による射影の受け皿（問題182）。Entity を直接公開せず必要な列だけ返す。 */
public record EmployeeSalaryDto(String name, double salary) {}
