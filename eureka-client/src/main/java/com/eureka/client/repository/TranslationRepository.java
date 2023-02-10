package com.eureka.client.repository;

import com.eureka.client.model.Translation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TranslationRepository extends JpaRepository<Translation, String> {
    Optional<Translation> findByMessage(String message);
}
