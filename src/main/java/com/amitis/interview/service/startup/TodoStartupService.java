package com.amitis.interview.service.startup;

import com.amitis.interview.model.Todo;
import com.amitis.interview.repository.TodoRepository;
import com.amitis.interview.dto.response.TodosResponse;
import com.amitis.interview.service.mapper.TodoMapper;
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
public class TodoStartupService {

    private final TodoRepository todoRepository;

    @Value("${server.servlet.application-display-name}")
    private String appName;

    @Value("${api.url.todo}")
    private String todoUrl;

    private final RestTemplate restTemplate;

    @Order(value = 20)
    @EventListener(ApplicationReadyEvent.class)
    public void todoReadyEvent() {
        if (appName.equalsIgnoreCase("InterviewApplication")) {


            try {
                Object request = restTemplate.getForObject(todoUrl, Object.class);
                TodosResponse todosResponse = TodoMapper.INSTANCE.getObjectResponse(request);
                List<Todo> todoList = TodoMapper.INSTANCE.convertToEntity(todosResponse);
                todoRepository.saveAll(todoList);

            } catch (RestClientException e) {

                log.info("{}, {}", e.getClass().getCanonicalName(), e.getMessage());

                /** when modified, developer can check response body
                 */
                if (e instanceof RestClientResponseException) {
                    log.info("## No Suitable MessageConverter Body ##");
                    log.info("{}", ((RestClientResponseException) e).getResponseBodyAsString());
                }
            }

        }

    }

}
