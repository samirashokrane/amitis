package com.amitis.interview.repository;

import com.amitis.interview.BaseRepository;
import com.amitis.interview.model.Comment;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends BaseRepository<Comment, Integer> {
}
