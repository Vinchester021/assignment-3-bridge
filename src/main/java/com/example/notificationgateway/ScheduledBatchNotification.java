package com.example.notificationgateway;

import java.util.ArrayList;
import java.util.List;

public class ScheduledBatchNotification extends Notification {

    private final List<String> recipients;

    public ScheduledBatchNotification(
            MessageSender sender,
            List<String> recipients
    ) {
        super(sender);
        this.recipients = new ArrayList<>(recipients);
    }

    @Override
    public void send(
            String recipient,
            String message
    ) {
        List<String> targets = new ArrayList<>(recipients);

        if (recipient != null
                && !recipient.isBlank()
                && !targets.contains(recipient)) {
            targets.add(recipient);
        }

        int successful = 0;
        int failed = 0;

        for (String currentRecipient : targets) {
            boolean result = sender.isAvailable()
                    && sender.sendMessage(
                    currentRecipient,
                    "Scheduled Batch Notification",
                    message
            );

            if (result) {
                successful++;
            } else {
                failed++;
            }
        }

        System.out.println();
        System.out.println("Batch summary:");
        System.out.println("Total: " + targets.size());
        System.out.println("Successful: " + successful);
        System.out.println("Failed: " + failed);
    }

    public void sendBatch(String message) {
        send("", message);
    }

    public List<String> getRecipients() {
        return List.copyOf(recipients);
    }
}