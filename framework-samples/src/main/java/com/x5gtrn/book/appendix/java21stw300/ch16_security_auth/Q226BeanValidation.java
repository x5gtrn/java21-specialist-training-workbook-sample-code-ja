package com.x5gtrn.book.appendix.java21stw300.ch16_security_auth;
import com.x5gtrn.book.appendix.java21stw300.springapp.FrameworkSample;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.stereotype.Component;
import java.util.Set;
@Component
public class Q226BeanValidation implements FrameworkSample {
    record SignupForm(
        @NotBlank(message = "ユーザー名は必須") @Size(min = 3, max = 20, message = "3〜20文字") String username,
        @Email(message = "メール形式が不正") String email) {}
    public String chapter(){ return "16_Security_Auth"; }
    public int problem(){ return 226; }
    public String title(){ return "Bean Validation による入力検証"; }
    public void run(){
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        // 入力検証はセキュリティの第一関門。境界で宣言的に検証し、不正値を内部へ入れない。
        for (SignupForm form : new SignupForm[]{
                new SignupForm("ab", "bad-email"), new SignupForm("alice", "alice@example.com")}) {
            Set<ConstraintViolation<SignupForm>> v = validator.validate(form);
            System.out.println(form + " -> 違反 " + v.size() + " 件");
            v.forEach(c -> System.out.println("    " + c.getPropertyPath() + ": " + c.getMessage()));
        }
        System.out.println("検証はクライアント任せにせずサーバ側で必ず行う（改ざん可能な入力を信頼しない）");
    }
}
