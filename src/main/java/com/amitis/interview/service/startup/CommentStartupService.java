package com.amitis.interview.service.startup;

import com.amitis.interview.model.Comment;
import com.amitis.interview.repository.CommentRepository;
import com.amitis.interview.dto.response.CommentsResponse;
import com.amitis.interview.service.mapper.CommentMapper;
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


@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
@Log4j2
public class CommentStartupService {

    private final CommentRepository commentRepository;

    @Value("${server.servlet.application-display-name}")
    private String appName;

    @Value("${api.url.comment}")
    private String commentUrl;

    private final RestTemplate restTemplate;

    @Order(value = 25)
    @EventListener(ApplicationReadyEvent.class)
    public void commentReadyEvent() {
        if (appName.equalsIgnoreCase("InterviewApplication")) {


            try {
                Object request = restTemplate.getForObject(commentUrl, Object.class);
                CommentsResponse commentsResponse = CommentMapper.INSTANCE.getResponse(request);
                List<Comment> commentList = CommentMapper.INSTANCE.convertToEntity(commentsResponse);
                commentRepository.saveAll(commentList);

            } catch (RestClientException e) {

                log.info("{}, {}", e.getClass().getCanonicalName(), e.getMessage());

                // when modified, developer can check response body
                if (e instanceof RestClientResponseException) {
                    log.info("## No Suitable MessageConverter Body ##");
                    log.info("{}", ((RestClientResponseException) e).getResponseBodyAsString());
                }
            }


        }

    }
}
