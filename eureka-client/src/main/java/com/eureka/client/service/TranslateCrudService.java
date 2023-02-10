package com.eureka.client.service;

import com.eureka.client.model.Translation;
import com.eureka.client.repository.TranslationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TranslateCrudService {
    private final TranslationRepository repository;

    public Translation save(Translation translation) {
        Optional<Translation> translationFromDb = get(translation.getMessage());
        if (translationFromDb.isEmpty()) {
            return repository.save(translation);
        }
        return translationFromDb.get();
    }

    public Optional<Translation> get(String message) {
        return repository.findByMessage(message);
    }

}
