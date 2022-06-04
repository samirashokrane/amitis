package com.amitis.interview.repository;

import com.amitis.interview.BaseRepository;
import com.amitis.interview.model.UserProfile;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends BaseRepository<UserProfile, Integer> {
}
