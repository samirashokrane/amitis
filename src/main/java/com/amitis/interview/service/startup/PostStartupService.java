package com.amitis.interview.service.startup;

import com.amitis.interview.model.Post;
import com.amitis.interview.repository.PostRepository;
import com.amitis.interview.dto.response.PostsResponse;
import com.amitis.interview.service.mapper.PostMapper;
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
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Log4j2
@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class PostStartupService {

    private final PostRepository postRepository;

    @Value("${server.servlet.application-display-name}")
    private String appName;

    @Value("${api.url.post}")
    private String postUrl;

    private final RestTemplate restTemplate;

    @Order(value = 21)
     @EventListener(ApplicationReadyEvent.class)
    public void postReadyEvent() {
        if (appName.equalsIgnoreCase("InterviewApplication")) {


            try {
                Object request = restTemplate.getForObject(postUrl, Object.class);
                PostsResponse postsResponse = PostMapper.INSTANCE.getObjectResponse(request);
                List<Post> postList = PostMapper.INSTANCE.convertToEntity(postsResponse);
                postRepository.saveAll(postList);

            } catch (RestClientException e) {


                log.info("{}, {}", e.getClass().getCanonicalName(), e.getMessage());

                /**
                 *      when modified, developer can check response body
                 */

                if (e instanceof RestClientResponseException) {
                    log.info("## No Suitable MessageConverter Body ##");
                    log.info("{}", ((RestClientResponseException) e).getResponseBodyAsString());
                }
            }

        }

    }
}
