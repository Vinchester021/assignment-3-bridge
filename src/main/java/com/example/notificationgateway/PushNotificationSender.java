package com.example.notificationgateway;

public class PushNotificationSender implements MessageSender {

    private boolean available;

    public PushNotificationSender(boolean available) {
        this.available = available;
    }

    @Override
    public boolean sendMessage(
            String recipient,
            String title,
            String body
    ) {
        if (!available) {
            System.out.println("[Push] Service is unavailable.");
            return false;
        }

        System.out.println("[Push Notification]");
        System.out.println("Device: " + recipient);
        System.out.println("Title: " + title);
        System.out.println("Message: " + body);
        System.out.println("Status: delivered");

        return true;
    }

    @Override
    public boolean isAvailable() {
        return available;
    }

    @Override
    public String getChannelName() {
        return "Push Notification";
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
}