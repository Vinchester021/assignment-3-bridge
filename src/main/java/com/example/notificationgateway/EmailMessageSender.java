package com.example.notificationgateway;

public class EmailMessageSender implements MessageSender {

    private boolean available;
    private final String signature;

    public EmailMessageSender(boolean available, String signature) {
        this.available = available;
        this.signature = signature;
    }

    @Override
    public boolean sendMessage(
            String recipient,
            String title,
            String body
    ) {
        if (!available) {
            System.out.println("[Email] Channel is unavailable.");
            return false;
        }

        System.out.println("[Email]");
        System.out.println("To: " + recipient);
        System.out.println("Subject: " + title);
        System.out.println(body);
        System.out.println();
        System.out.println(signature);

        return true;
    }

    @Override
    public boolean isAvailable() {
        return available;
    }

    @Override
    public String getChannelName() {
        return "Email";
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
}