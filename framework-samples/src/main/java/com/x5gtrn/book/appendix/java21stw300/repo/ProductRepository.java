package com.x5gtrn.book.appendix.java21stw300.repo;

import com.x5gtrn.book.appendix.java21stw300.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
