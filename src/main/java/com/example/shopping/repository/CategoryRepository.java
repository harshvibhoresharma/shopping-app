package com.example.shopping.repository;

import com.example.shopping.model.Category;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface CategoryRepository extends JpaRepository<Category,Long> {



    Category findByName(String name);

    boolean existsByName(@NotNull String name);


}
