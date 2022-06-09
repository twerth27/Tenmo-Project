package com.techelevator.tenmo.services;


import com.techelevator.tenmo.model.AuthenticatedUser;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.UserCredentials;
import com.techelevator.tenmo.model.dto.TransferDTO;

import java.math.BigDecimal;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class ConsoleService extends BaseService {

    private final Scanner scanner = new Scanner(System.in);
    private String from;
    private String to;


    public Scanner getScanner() {
        return scanner;
    }


    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public int promptForMenuSelection(String prompt) {
        int menuSelection;
        System.out.print(prompt);
        try {
            menuSelection = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            menuSelection = -1;
        }
        return menuSelection;
    }

    public void printGreeting() {
        System.out.println("*********************");
        System.out.println("* Welcome to TEnmo! *");
        System.out.println("*********************");
    }

    public void printLoginMenu() {
        System.out.println();
        System.out.println("1: Register");
        System.out.println("2: Login");
        System.out.println("0: Exit");
        System.out.println();
    }

    public void printMainMenu() {
        System.out.println();
        System.out.println("1: View your current balance");
        System.out.println("2: View your past transfers");
        System.out.println("3: View your pending requests");
        System.out.println("4: Send TE bucks");
        System.out.println("5: Request TE bucks");
        System.out.println("0: Exit");
        System.out.println();
    }

    public UserCredentials promptForCredentials() {
        String username = promptForString("Username: ");
        String password = promptForString("Password: ");
        return new UserCredentials(username, password);
    }

    public String promptForString(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    public int promptForInt(String prompt) {
        System.out.print(prompt);
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a number.");
            }
        }
    }

    public BigDecimal promptForBigDecimal(String prompt) {
        System.out.print(prompt);
        while (true) {
            try {
                return new BigDecimal(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a decimal number.");
            }
        }
    }

    public void pause() {
        System.out.println("\nPress Enter to continue...");
        scanner.nextLine();
    }

    public void printErrorMessage() {
        System.out.println("An error occurred. Check the log for details.");
    }

    public String transferRecipientSelection()
    {
        System.out.println("Please select a user to transfer money to:");
        String recipient = scanner.nextLine();
        return recipient;
    }

    public BigDecimal promptForTransferAmount(){
        System.out.println("Please enter how much money you would like to send:");
        String amount = scanner.nextLine();
        BigDecimal transferAmount = new BigDecimal(amount);

        return transferAmount;
    }

    public void printTransferList(List<TransferDTO> transfers, AuthenticatedUser currentUser){
        System.out.println();
        System.out.println("---------------------------------------------------------------------");
        System.out.println("                    " + currentUser.getUser().getUsername() +"'s Transfer History");
        System.out.println();
        System.out.println("Transfer ID: " + "  FROM:                 TO:       " + "           AMOUNT");
        System.out.println("---------------------------------------------------------------------");
        for (TransferDTO transfer: transfers){

            if (transfer.getWhoGaveIt().equalsIgnoreCase("RECEIVED")){
                from = "    FROM: " + transfer.getUsername();
                to = "TO: " + currentUser.getUser().getUsername();
            }
            else {
                to = "TO: " + transfer.getUsername();
                from = "    FROM: " + currentUser.getUser().getUsername();
            }

            System.out.println(transfer.getTransferId() + "       " +
                                from + "         " +
                                to + "          " +
                                "$ " + transfer.getTransferAmount());
            System.out.println("---------------------------------------------------------------------");
        }
    }

    public String promptForTransferID(){
        System.out.println("Please choose a Transfer ID Number to view more details (or 0 to return to main menu): ");
        String transferSelection = scanner.nextLine();
        return transferSelection;
    }

    public void displayTransferDetails(TransferDTO transfer, AuthenticatedUser currentUser) {


        if (transfer.getWhoGaveIt().equalsIgnoreCase("RECEIVED")) {
            from = transfer.getUsername();
            to = currentUser.getUser().getUsername();
        } else {
            to = transfer.getUsername();
            from = currentUser.getUser().getUsername();
        }

            System.out.println();
            System.out.println("Transfer Details");
            System.out.println("----------------------------------");
            System.out.println("ID: " + transfer.getTransferId());
            System.out.println("From: " + from);
            System.out.println("To: " + to);
            System.out.println("Amount: " + transfer.getTransferAmount());
            System.out.println("Type: " + "Send");
            System.out.println("Status: " + "Approved");
        }


}
