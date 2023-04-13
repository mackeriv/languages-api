package br.com.alura.languages.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
public class LanguageController {

    @Autowired
    private LanguageRepository repository;

    @GetMapping("/languages")
    public List<Language> retrieveLanguage(){
        return repository.findByOrderByRanking();
    }

    @PostMapping("/languages")
    public ResponseEntity<Language> registerLanguage(@RequestBody Language language) {
        Language savedLanguage = repository.save(language);
        return new ResponseEntity<>(savedLanguage, HttpStatus.CREATED);
    }

    @GetMapping("/languages/{id}")
    public Language retrieveLanguageById(@PathVariable String id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/languages/{id}")
    public Language updateLinguagem(@PathVariable String id, @RequestBody Language language) {
        if (!repository.existsById(id)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        language.setId(id);
        return repository.save(language);
    }

    @DeleteMapping("/languages/{id}")
    public void deleteLanguage(@PathVariable String id) { repository.deleteById(id); }
}