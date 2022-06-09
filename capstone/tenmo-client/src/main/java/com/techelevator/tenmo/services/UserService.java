package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.User;
import com.techelevator.util.BasicLogger;
import org.springframework.http.*;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

public class UserService extends BaseService
{
    private String baseUrl = "http://localhost:8080/users/";
    RestTemplate restTemplate = new RestTemplate();

    public User getUserByUsername(String userName)
    {
        User user = null;
        try
        {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBearerAuth(getAuthToken());

            HttpEntity<User> entity = new HttpEntity<>(headers);


            ResponseEntity<User> accountResponse = restTemplate.exchange(baseUrl, HttpMethod.GET, entity, User.class);
            user = accountResponse.getBody();
        }
        catch(RestClientResponseException e)
        {
            BasicLogger.log(e.getMessage());
        }

        return user;

    }

}
