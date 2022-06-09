package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import com.techelevator.util.BasicLogger;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TransferService extends BaseService {
    private String baseUrl = "http://localhost:8080/transfer";
    RestTemplate restTemplate = new RestTemplate();




    public List<User> listUsers() {

//        revisit to prevent user from sending money to themselves
        List<User> listOfUsers = new ArrayList<>();
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setBearerAuth(getAuthToken());
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<User[]> entity = new HttpEntity<>(headers);

            ResponseEntity<User[]> users = restTemplate.exchange(baseUrl,HttpMethod.GET,entity, User[].class);

            listOfUsers.addAll(Arrays.asList(users.getBody()));
        } catch (Exception e) {
            BasicLogger.log(e.getMessage());
        }

        return listOfUsers;
    }

    public Transfer createTransfer(Transfer transfer) {

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBearerAuth(getAuthToken());

            HttpEntity<Transfer> entity = new HttpEntity<>(transfer, headers);

            ResponseEntity<Transfer> response = restTemplate.exchange(baseUrl, HttpMethod.POST, entity, Transfer.class);
            Transfer returnedTransfer = response.getBody();
            return returnedTransfer;
//            System.out.println("DEBUG: " + returnedTransfer);

        } catch (Exception e) {
            BasicLogger.log(e.getMessage());
        }
        return null;
    }

    public Boolean checkForCreatedTransfer(Transfer transfer) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBearerAuth(getAuthToken());

            HttpEntity<Transfer> entity = new HttpEntity<>(headers);

            String url = baseUrl + "/check";

            ResponseEntity<Transfer> response = restTemplate.exchange(url, HttpMethod.GET, entity, Transfer.class);
            Transfer returnedTransfer = response.getBody();


            if (transfer.compareTransfers(returnedTransfer)) {
                return true;
            }


        } catch (Exception e) {
            BasicLogger.log(e.getMessage());
        }
         return false;
    }

    public int getAccountIdByUsername(String username)
    {
        int returnedInt = 0;
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBearerAuth(getAuthToken());

            HttpEntity<String> entity = new HttpEntity<>(username, headers);

            String url = baseUrl + "/search/" + username;

            ResponseEntity<Integer> response = restTemplate.exchange(url, HttpMethod.GET, entity, int.class);

            returnedInt = response.getBody();



        } catch (Exception e) {
            BasicLogger.log(e.getMessage());
        }
        return returnedInt;
    }

    public List<Transfer> listTransfersByAccount(){
        List<Transfer> listOfTransfers = new ArrayList<>();
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBearerAuth(getAuthToken());

            HttpEntity<List<Transfer>> entity = new HttpEntity<>(headers);
            String url = baseUrl + "/list";
            ResponseEntity<Transfer[]> transfers = restTemplate.exchange(url,HttpMethod.GET, entity, Transfer[].class);
            Transfer[] returnedList = transfers.getBody();

            listOfTransfers.addAll(Arrays.asList(returnedList));
        } catch (Exception e) {
            BasicLogger.log(e.getMessage());
        }

        return listOfTransfers;
    }

}
