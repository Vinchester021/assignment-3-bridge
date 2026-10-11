package com.example.notificationgateway;

public class SmsMessageSender implements MessageSender {

    private static final int MAX_LENGTH = 160;

    private boolean available;

    public SmsMessageSender(boolean available) {
        this.available = available;
    }

    @Override
    public boolean sendMessage(
            String recipient,
            String title,
            String body
    ) {
        if (!available) {
            System.out.println("[SMS] Channel is unavailable.");
            return false;
        }

        if (body.length() > MAX_LENGTH) {
            System.out.println(
                    "[SMS] Message is too long. Maximum: "
                            + MAX_LENGTH + " characters."
            );
            return false;
        }

        System.out.println("[SMS]");
        System.out.println("Phone: " + recipient);
        System.out.println(body);

        return true;
    }

    @Override
    public boolean isAvailable() {
        return available;
    }

    @Override
    public String getChannelName() {
        return "SMS";
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
}