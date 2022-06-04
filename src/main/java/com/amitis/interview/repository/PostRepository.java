package com.amitis.interview.repository;

import com.amitis.interview.BaseRepository;
import com.amitis.interview.model.Comment;
import com.amitis.interview.model.Post;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends BaseRepository<Post, Integer> {

    @Query("select c from Comment c, Post p where c.postId= p.id and p.id =:postId")
    List<Comment> findByPostId(@Param("postId") Integer postId);
}
