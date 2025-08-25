package com.bootcamp.r2dbc;

import com.bootcamp.model.user.User;
import com.bootcamp.model.user.gateways.UserRepository;
import com.bootcamp.r2dbc.entity.UserEntity;
import com.bootcamp.r2dbc.helper.ReactiveAdapterOperations;
import lombok.extern.java.Log;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Log
@Repository
public class UserReactiveRepositoryAdapter extends ReactiveAdapterOperations<
        User,
        UserEntity,
        String,
        UserReactiveRepository
> implements UserRepository {
    UUID uuid = UUID.randomUUID();

    public UserReactiveRepositoryAdapter(UserReactiveRepository repository, ObjectMapper mapper) {
        /**
         *  Could be use mapper.mapBuilder if your domain model implement builder pattern
         *  super(repository, mapper, d -> mapper.mapBuilder(d,ObjectModel.ObjectModelBuilder.class).build());
         *  Or using mapper.map with the class of the object model
         */
        super(repository, mapper, userEntity -> mapper.map(userEntity, User.class));
    }

    @Transactional
    @Override
    public Flux<User> findAllUsers() {
        log.info("UserReactiveRepositoryAdapter findAllUsers");
        return super.findAll();
    }

    @Transactional
    @Override
    public Mono<User> saveUser(User user) {
        log.info("UserReactiveRepositoryAdapter saveUser" + user.toString());
        return super.save(user);
    }

    @Transactional
    @Override
    public Mono<User> updateUser(User user) {
        log.info("UserReactiveRepositoryAdapter updateUser" + user.toString());
        return super.save(user);
    }
}
