package com.example.notificationgateway;

public class KafkaEventSender implements MessageSender {

    private boolean available;

    public KafkaEventSender(boolean available) {
        this.available = available;
    }

    @Override
    public boolean sendMessage(
            String recipient,
            String title,
            String body
    ) {
        if (!available) {
            System.out.println("[Kafka] Service is unavailable.");
            return false;
        }

        System.out.println("[Kafka Event]");
        System.out.println("Topic: notifications");
        System.out.println("Recipient: " + recipient);
        System.out.println("Event title: " + title);
        System.out.println("Event body: " + body);
        System.out.println("Status: published");

        return true;
    }

    @Override
    public boolean isAvailable() {
        return available;
    }

    @Override
    public String getChannelName() {
        return "Kafka";
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
}