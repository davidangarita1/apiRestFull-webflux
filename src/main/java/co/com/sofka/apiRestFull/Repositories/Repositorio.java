package co.com.sofka.apiRestFull.Repositories;

import co.com.sofka.apiRestFull.Collections.Dato;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface Repositorio extends ReactiveMongoRepository<Dato, String> {
}