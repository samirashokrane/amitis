package com.amitis.interview;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface BaseRepository<T,E> extends JpaRepository<T,E>, QuerydslPredicateExecutor<T> {
}
