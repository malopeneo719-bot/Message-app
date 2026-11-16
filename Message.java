package chatapp;

import java.util.ArrayList;
import java.util.Random;

public final class Message {

    private static int totalMessages = 0;
    private static final ArrayList<Message> sentMessages = new ArrayList<>();

    private final String messageID;
    private final int messageNumber;
    private final String recipient;
    private final String messageText;
    private final String messageHash;

    public Message(String messageID, int messageNumber, String recipient, String messageText) {
        this.messageID = messageID;
        this.messageNumber = messageNumber;
        this.recipient = recipient;
        this.messageText = messageText;
        this.messageHash = createMessageHash();
    }

    public static String generateMessageID() {
        Random random = new Random();
        String id = "";
        for (int i = 0; i < 10; i++) {
            id = id + random.nextInt(10);
        }
        return id;
    }

    public boolean checkMessageID() {
        return messageID != null && messageID.length() <= 10;
    }

    public String checkRecipientCell() {
        boolean valid = recipient != null
                && recipient.startsWith("+")
                && recipient.length() <= 13
                && recipient.substring(1).matches("[0-9]+");

        if (valid) {
            return "Cell phone number successfully captured.";
        } else {
            return "Cell phone number is incorrectly formatted or does not contain an "
                    + "international code. Please correct the number and try again.";
        }
    }

    public String checkMessageLength() {
        if (messageText.length() <= 250) {
            return "Message ready to send.";
        } else {
            int extra = messageText.length() - 250;
            return "Message exceeds 250 characters by " + extra + ", please reduce the size.";
        }
    }

    public String createMessageHash() {
        if (messageID == null || messageID.length() < 2) return "00:0:ERROR";
        if (messageText == null || messageText.trim().isEmpty()) return messageID.substring(0,2) + ":" + messageNumber + ":EMPTY";

        String firstTwo = messageID.substring(0, 2);
        String[] words = messageText.trim().split("\\s+");
        String firstWord = words[0].replaceAll("[^A-Za-z0-9]", "");
        String lastWord = words[words.length - 1].replaceAll("[^A-Za-z0-9]", "");
        
        return (firstTwo + ":" + messageNumber + ":" + firstWord + lastWord).toUpperCase();
    }

    public String sentMessage(int choice) {
        switch (choice) {
            case 1 -> {
                sentMessages.add(this);
                totalMessages++;
                return "Message successfully sent.";
            }
            case 2 -> {
                return "Press 0 to delete the message.";
            }
            case 3 -> {
                MessageStorage.storeMessage(this);
                return "Message successfully stored.";
            }
            default -> {
                return "Invalid choice.";
            }
        }
    }

    public String getDetails() {
        return "Message ID: " + messageID
                + "\nMessage Hash: " + messageHash
                + "\nRecipient: " + recipient
                + "\nMessage: " + messageText;
    }

    public static String printMessages() {
        String all = "";
        for (Message m : sentMessages) {
            all = all + m.getDetails() + "\n\n";
        }
        return all;
    }

    public static int returnTotalMessages() { return totalMessages; }
    public static void resetAll() {
        totalMessages = 0;
        sentMessages.clear();
    }

    public String getMessageID() { return messageID; }
    public String getMessageHash() { return messageHash; }
    public String getRecipient() { return recipient; }
    public String getMessageText() { return messageText; }
}
