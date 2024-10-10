package co.com.bancolombia.r2dbc.helper;

import com.google.gson.Gson;
import org.springframework.data.domain.Example;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public abstract class ReactiveAdapterOperations<E, D, I, R extends ReactiveCrudRepository<D, I> & ReactiveQueryByExampleExecutor<D>> {
    protected R repository;
    protected Gson gson;
    private final Class<D> dataClass;
    private final Type entityType;

    @SuppressWarnings("unchecked")
    protected ReactiveAdapterOperations(R repository, Gson gson, Class<E> entityClass) {
        this.repository = repository;
        this.gson = gson;
        ParameterizedType genericSuperclass = (ParameterizedType) this.getClass().getGenericSuperclass();
        this.dataClass = (Class<D>) genericSuperclass.getActualTypeArguments()[1];
        this.entityType = entityClass;
    }


    public Mono<E> save(E entity) {
        return saveData(toData(entity))
                .map(this::toEntity);
    }

    protected Mono<D> saveData(D data) {
        return repository.save(data);
    }

    public Mono<E> findById(I id) {
        return repository.findById(id).map(this::toEntity);
    }

    public Flux<E> findByExample(E entity) {
        return repository.findAll(Example.of(toData(entity)))
                .map(this::toEntity);
    }

    public Flux<E> findAll() {
        return repository.findAll()
                .map(this::toEntity);
    }

    protected D toData(E entity) {
        String json = gson.toJson(entity);
        return gson.fromJson(json, dataClass);
    }

    protected E toEntity(D data) {
        String json = gson.toJson(data);
        return gson.fromJson(json, entityType);
    }
}
