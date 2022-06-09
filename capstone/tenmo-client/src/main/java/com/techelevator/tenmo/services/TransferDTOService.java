package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.dto.TransferDTO;
import com.techelevator.util.BasicLogger;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TransferDTOService extends BaseService {
    private String baseUrl = "http://localhost:8080/transferdto";
    RestTemplate restTemplate = new RestTemplate();

    public List<TransferDTO> listTransfersByAccount() {
        List<TransferDTO> listOfTransfers = new ArrayList<>();
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBearerAuth(getAuthToken());

            HttpEntity<TransferDTO[]> entity = new HttpEntity<>(headers);

            ResponseEntity<TransferDTO[]> transfers = restTemplate.exchange(baseUrl, HttpMethod.GET, entity, TransferDTO[].class);
            TransferDTO[] returnedList = transfers.getBody();

            listOfTransfers.addAll(Arrays.asList(returnedList));
        } catch (Exception e) {
            BasicLogger.log(e.getMessage());
        }

        return listOfTransfers;
    }
}
