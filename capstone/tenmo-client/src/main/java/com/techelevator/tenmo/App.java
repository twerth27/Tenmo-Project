package com.techelevator.tenmo;

import com.techelevator.tenmo.model.*;
import com.techelevator.tenmo.model.dto.TransferDTO;
import com.techelevator.tenmo.services.*;

import java.math.BigDecimal;
import java.util.List;

public class App {

    private static final String API_BASE_URL = "http://localhost:8080/";

    private final ConsoleService consoleService = new ConsoleService();
    private final AuthenticationService authenticationService = new AuthenticationService(API_BASE_URL);

    private AuthenticatedUser currentUser;

    public static void main(String[] args) {
        App app = new App();
        app.run();
    }

    private void run() {
        consoleService.printGreeting();
        loginMenu();
        if (currentUser != null) {
            mainMenu();
        }
    }
    private void loginMenu() {
        int menuSelection = -1;
        while (menuSelection != 0 && currentUser == null) {
            consoleService.printLoginMenu();
            menuSelection = consoleService.promptForMenuSelection("Please choose an option: ");
            if (menuSelection == 1) {
                handleRegister();
            } else if (menuSelection == 2) {
                handleLogin();
            } else if (menuSelection != 0) {
                System.out.println("Invalid Selection");
                consoleService.pause();
            }
        }
    }

    private void handleRegister() {
        System.out.println("Please register a new user account");
        UserCredentials credentials = consoleService.promptForCredentials();
        if (authenticationService.register(credentials)) {
            System.out.println("Registration successful. You can now login.");
        } else {
            consoleService.printErrorMessage();
        }
    }

    private void handleLogin() {
        UserCredentials credentials = consoleService.promptForCredentials();
        currentUser = authenticationService.login(credentials);
        //set auth-token in baseService here
        if (currentUser != null) {
            BaseService.setAuthToken(currentUser.getToken());
        }
        if (currentUser == null) {
            consoleService.printErrorMessage();
        }
    }

    private void mainMenu() {
        int menuSelection = -1;
        while (menuSelection != 0) {
            consoleService.printMainMenu();
            menuSelection = consoleService.promptForMenuSelection("Please choose an option: ");
            if (menuSelection == 1) {
                viewCurrentBalance();
            } else if (menuSelection == 2) {
                viewTransferHistory();
            } else if (menuSelection == 3) {
                viewPendingRequests();
            } else if (menuSelection == 4) {
                sendBucks();
            } else if (menuSelection == 5) {
                requestBucks();
            } else if (menuSelection == 0) {
                continue;
            } else {
                System.out.println("Invalid Selection");
            }
            consoleService.pause();
        }
    }

	private void viewCurrentBalance() {
        AccountService accountService = new AccountService();
        Long currentUserId = currentUser.getUser().getId();

        Account userAccount = accountService.getByUserId(currentUserId);

        BigDecimal balance = userAccount.getBalance();
        System.out.println("Current Balance: $" + balance);

	}

	private void viewTransferHistory() {
		TransferDTOService transferDTOService = new TransferDTOService();
        List<TransferDTO> listOfTransfers = transferDTOService.listTransfersByAccount();

        //print list of transfers
        consoleService.printTransferList(listOfTransfers, currentUser);
        String transferDetailID = consoleService.promptForTransferID();

        //display details for transfer
        Transfer detailedTransfer;
        for (TransferDTO transfer : listOfTransfers){
            if (transfer.getTransferId() == Integer.parseInt(transferDetailID)){
                consoleService.displayTransferDetails(transfer, currentUser);
                break;
            }
        }

	}

	private void viewPendingRequests() {
		// TODO Auto-generated method stub
		
	}

	private void sendBucks() {
        TransferService transferService = new TransferService();
        List<User> userList = transferService.listUsers();
        System.out.println("List of Users: ");
        for (User user: userList)
        {
            if (!user.getUsername().equals(currentUser.getUser().getUsername())){
                System.out.println(user.getUsername());
            }
        }

        String recipient = consoleService.transferRecipientSelection();


        //get transfer amount from user
        BigDecimal transferAmount = consoleService.promptForTransferAmount();

        int recipientAccountId = transferService.getAccountIdByUsername(recipient);
        int senderAccountId = transferService.getAccountIdByUsername(currentUser.getUser().getUsername());

        //create Transfer object
        Transfer transfer = new Transfer(transferAmount, recipientAccountId,
                                           senderAccountId,
                                            2,
                                            2);

        //call create transfer and pass transfer object
        Transfer returnedTransfer = transferService.createTransfer(transfer);

       if(returnedTransfer == null)
       {
           System.out.println("Your transfer was not created due to insufficient funds, or you attempted to send zero or negative bucks");
       }
        else System.out.println("Transfer created successfully.");
	}

	private void requestBucks() {
		// TODO Auto-generated method stub
		
	}


}
