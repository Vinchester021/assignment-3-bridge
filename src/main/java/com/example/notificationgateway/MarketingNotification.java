package com.example.notificationgateway;

public class MarketingNotification extends Notification {

    public MarketingNotification(MessageSender sender) {
        super(sender);
    }

    @Override
    public void send(
            String recipient,
            String message
    ) {
        if (!sender.isAvailable()) {
            System.out.println(
                    sender.getChannelName() + " is unavailable."
            );
            return;
        }

        String marketingMessage =
                "[MARKETING] " + message;

        sender.sendMessage(
                recipient,
                "Special Offer",
                marketingMessage
        );
    }
}