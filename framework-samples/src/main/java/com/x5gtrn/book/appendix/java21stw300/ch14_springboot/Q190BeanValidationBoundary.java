package com.x5gtrn.book.appendix.java21stw300.ch14_springboot;
import com.x5gtrn.book.appendix.java21stw300.springapp.FrameworkSample;
import com.x5gtrn.book.appendix.java21stw300.web.CreateUserRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.springframework.stereotype.Component;
import java.util.Set;
@Component
public class Q190BeanValidationBoundary implements FrameworkSample {
    public String chapter(){ return "14_SpringBoot"; }
    public int problem(){ return 190; }
    public String title(){ return "Bean バリデーションと Request 境界"; }
    public void run(){
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        CreateUserRequest bad = new CreateUserRequest("", "not-an-email", -1);
        Set<ConstraintViolation<CreateUserRequest>> violations = validator.validate(bad);
        System.out.println("不正リクエストの違反数 = " + violations.size());
        violations.forEach(v -> System.out.println("  " + v.getPropertyPath() + ": " + v.getMessage()));
        CreateUserRequest ok = new CreateUserRequest("Alice", "alice@example.com", 30);
        System.out.println("正当リクエストの違反数 = " + validator.validate(ok).size() + "（境界で弾き、内部を汚さない）");
    }
}
