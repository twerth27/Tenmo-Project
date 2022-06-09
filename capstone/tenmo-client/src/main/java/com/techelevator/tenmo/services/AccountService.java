package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.util.BasicLogger;
import org.springframework.http.*;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

public class AccountService extends BaseService {
    private String baseUrl = "http://localhost:8080/accounts/";
    RestTemplate restTemplate = new RestTemplate();


    public Account getByUserId(Long id)
    {
        Account account = null;

        try
        {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBearerAuth(getAuthToken());

            HttpEntity<Account> entity = new HttpEntity<>(headers);


           ResponseEntity<Account> accountResponse = restTemplate.exchange(baseUrl + id, HttpMethod.GET, entity, Account.class);
           account = accountResponse.getBody();
        }
        catch(RestClientResponseException e)
        {
            BasicLogger.log(e.getMessage());
        }

        return account;
    }

    public Account getByAccountId(Long id)
    {
        Account account = null;

        try
        {
            account = restTemplate.getForObject(baseUrl + id, Account.class);
        }
        catch(RestClientResponseException e)
        {
            BasicLogger.log(e.getMessage());
        }

        return account;
    }





}
