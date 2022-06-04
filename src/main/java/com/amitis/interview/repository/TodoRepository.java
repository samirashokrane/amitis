package com.amitis.interview.repository;

import com.amitis.interview.BaseRepository;
import com.amitis.interview.model.Todo;
import org.springframework.stereotype.Repository;

@Repository
public interface TodoRepository extends BaseRepository<Todo, Integer> {
}
