package com.amitis.interview.service.startup;

import com.amitis.interview.model.UserProfile;
import com.amitis.interview.repository.UserRepository;
import com.amitis.interview.dto.response.UserResponse;
import com.amitis.interview.service.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestClientResponseException;

@Log4j2
@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class UserStartupService {

    private final UserRepository userRepository;

    @Value("${server.servlet.application-display-name}")
    private String appName;


    @Order(value = 19)
    @EventListener(ApplicationReadyEvent.class)
    public void userReadyEvent() {
        if (appName.equalsIgnoreCase("InterviewApplication")) {


            try {
                UserResponse userResponse1 = UserResponse.builder().id(1).name("User1").build();
                UserProfile user1 = UserMapper.INSTANCE.initialize(userResponse1);
                userRepository.save(user1);
                UserResponse userResponse2 = UserResponse.builder().id(2).name("User2").build();
                UserProfile user2 = UserMapper.INSTANCE.initialize(userResponse2);
                userRepository.save(user2);

                UserResponse userResponse3 = UserResponse.builder().id(3).name("User3").build();
                UserProfile user3 = UserMapper.INSTANCE.initialize(userResponse3);
                userRepository.save(user3);

                UserResponse userResponse4 = UserResponse.builder().id(4).name("User4").build();
                UserProfile user4 = UserMapper.INSTANCE.initialize(userResponse4);
                userRepository.save(user4);

                UserResponse userResponse5 = UserResponse.builder().id(5).name("User5").build();
                UserProfile user5 = UserMapper.INSTANCE.initialize(userResponse5);
                userRepository.save(user5);

                UserResponse userResponse6 = UserResponse.builder().id(6).name("User6").build();
                UserProfile user6 = UserMapper.INSTANCE.initialize(userResponse6);
                userRepository.save(user6);

                UserResponse userResponse7 = UserResponse.builder().id(7).name("User7").build();
                UserProfile user7 = UserMapper.INSTANCE.initialize(userResponse7);
                userRepository.save(user7);

                UserResponse userResponse8 = UserResponse.builder().id(8).name("User8").build();
                UserProfile user8 = UserMapper.INSTANCE.initialize(userResponse8);
                userRepository.save(user8);

                UserResponse userResponse9 = UserResponse.builder().id(9).name("User9").build();
                UserProfile user9 = UserMapper.INSTANCE.initialize(userResponse9);
                userRepository.save(user9);

                UserResponse userResponse10 = UserResponse.builder().id(10).name("User10").build();
                UserProfile user10 = UserMapper.INSTANCE.initialize(userResponse10);
                userRepository.save(user10);



            } catch (RestClientException e) {

                log.info("{}, {}", e.getClass().getCanonicalName(), e.getMessage());
                /**
                 *  when modified, developer can check response body
                 * **/
                if (e instanceof RestClientResponseException) {
                    log.info("## No Suitable MessageConverter Body ##");
                    log.info("{}", ((RestClientResponseException) e).getResponseBodyAsString());
                }
            }

        }

    }

}
