package com.headout.user_invite_service.repository;

import com.headout.user_invite_service.model.UserInviteModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserInviteRepository extends MongoRepository<UserInviteModel, String> {
    UserInviteModel findByUsername(String username);
}
