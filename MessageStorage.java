package chatapp;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/*
 * MessageStorage class: keeps stored messages and saves them to a JSON file.
 */
public class MessageStorage {

    // Messages the user chose to "store to send later"
    private static final ArrayList<Message> storedMessages = new ArrayList<>();

    // Adds the message to the list and rewrites the JSON file
    public static void storeMessage(Message message) {
        storedMessages.add(message);

        // try-with-resources closes the file automatically
        try (FileWriter writer = new FileWriter("messages.json")) {
            writer.write(buildJson());
        } catch (IOException e) {
            System.out.println("Could not save to JSON file: " + e.getMessage());
        }
    }

    // Builds the JSON text by hand, e.g. [ { "messageID": "...", ... }, ... ]
    private static String buildJson() {
        String json = "[\n";
        for (int i = 0; i < storedMessages.size(); i++) {
            Message m = storedMessages.get(i);
            json = json + "  {\n"
                    + "    \"messageID\": \"" + clean(m.getMessageID()) + "\",\n"
                    + "    \"messageHash\": \"" + clean(m.getMessageHash()) + "\",\n"
                    + "    \"recipient\": \"" + clean(m.getRecipient()) + "\",\n"
                    + "    \"message\": \"" + clean(m.getMessageText()) + "\"\n"
                    + "  }";
            if (i < storedMessages.size() - 1) {
                json = json + ",";   // comma between items, not after the last
            }
            json = json + "\n";
        }
        return json + "]";
    }

    // Escapes quotes and backslashes so the JSON stays valid
    private static String clean(String text) {
        return text.replace("\\", "\\\\").replace("\"", "\\\"");
    }
}
      