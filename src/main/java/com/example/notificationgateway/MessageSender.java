package com.example.notificationgateway;

public interface MessageSender {

    boolean sendMessage(
            String recipient,
            String title,
            String body
    );

    boolean isAvailable();

    String getChannelName();
}