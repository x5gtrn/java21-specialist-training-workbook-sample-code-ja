package com.x5gtrn.book.appendix.java21stw300.web;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

/** REST バリデーションのデモ用リクエスト（第15章 問題211）。 */
public record CreateUserRequest(
        @NotBlank(message = "name は必須です") String name,
        @Email(message = "email の形式が不正です") String email,
        @Min(value = 0, message = "age は 0 以上") int age) {
}
