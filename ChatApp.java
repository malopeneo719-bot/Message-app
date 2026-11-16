package chatapp;

import java.util.Scanner;

/**
 * ChatApp: the main class. Runs the menu and talks to the user.
 */
public class ChatApp {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        Login login = new Login();

        // ---------- Register and log in ----------
        System.out.print("Register - enter username: ");
        String user = input.nextLine();
        System.out.print("Register - enter password: ");
        String pass = input.nextLine();
        System.out.println(login.registerUser(user, pass));

        System.out.print("Login - enter username: ");
        String loginUser = input.nextLine();
        System.out.print("Login - enter password: ");
        String loginPass = input.nextLine();

        boolean loggedIn = login.loginUser(loginUser, loginPass);
        System.out.println(login.returnLoginStatus(loggedIn));

        // Users may only send messages if they logged in successfully
        if (!loggedIn) {
            return;   // stop the program
        }

        // ---------- Welcome and message limit ----------
        System.out.println("Welcome to QuickChat.");
        System.out.print("How many messages do you wish to enter? ");
        int maxMessages = Integer.parseInt(input.nextLine());
        int messagesEntered = 0;   // counts messages used so far

        // ---------- Menu loop: runs until the user picks Quit ----------
        int choice = 0;
        while (choice != 3) {
            System.out.println("\n1) Send Messages");
            System.out.println("2) Show recently sent messages");
            System.out.println("3) Quit");
            System.out.print("Choose an option: ");
            choice = Integer.parseInt(input.nextLine());

            switch (choice) {
                case 1 -> {
                    // Only allow the number of messages the user set at the start
                    if (messagesEntered >= maxMessages) {
                        System.out.println("You have reached your limit of " + maxMessages + " messages.");
                    } else {
                        sendOneMessage(input, messagesEntered);
                        messagesEntered++;
                    }
                }
                case 2 -> System.out.println("Coming Soon.");
                case 3 -> {
                    // Show total and sent messages before exiting
                    System.out.println("\nMessages sent:\n" + Message.printMessages());
                    System.out.println("Total messages sent: " + Message.returnTotalMessages());
                }
                default -> System.out.println("Please choose 1, 2 or 3.");
            }
        }
    }

    // Collects the details for one message and handles send/disregard/store
    private static void sendOneMessage(Scanner input, int messageNumber) {
        String id = Message.generateMessageID();
        System.out.println("Message ID generated: " + id);

        // Keep asking until the recipient number is valid
        String recipient;
        while (true) {
            System.out.print("Enter recipient cell number (e.g. +27718693002): ");
            recipient = input.nextLine();
            Message check = new Message(id, messageNumber, recipient, "x");
            String result = check.checkRecipientCell();
            System.out.println(result);
            if (result.startsWith("Cell phone number successfully")) {
                break;
            }
        }

        // Keep asking until the message is 250 characters or less
        String text;
        while (true) {
            System.out.print("Enter your message: ");
            text = input.nextLine();
            Message check = new Message(id, messageNumber, recipient, text);
            String result = check.checkMessageLength();
            System.out.println(result);
            if (result.equals("Message ready to send.")) {
                break;
            }
        }

        // Create the real message
        Message message = new Message(id, messageNumber, recipient, text);

        // Ask what to do with it
        System.out.println("1) Send Message");
        System.out.println("2) Disregard Message");
        System.out.println("3) Store Message to send later");
        System.out.print("Choose: ");
        int option = Integer.parseInt(input.nextLine());

        System.out.println(message.sentMessage(option));

        if (option == 1) {
            // Show the full details after sending
            System.out.println("\n" + message.getDetails());
        } else if (option == 2) {
            System.out.print("Enter 0 to delete: ");
            input.nextLine();   // message is simply not kept
        }
    }
}