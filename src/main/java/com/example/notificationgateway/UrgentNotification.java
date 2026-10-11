package com.example.notificationgateway;

public class UrgentNotification extends Notification {

    private MessageSender fallbackSender;

    public UrgentNotification(
            MessageSender sender,
            MessageSender fallbackSender
    ) {
        super(sender);
        this.fallbackSender = fallbackSender;
    }

    @Override
    public void send(
            String recipient,
            String message
    ) {
        String urgentMessage = "[URGENT] " + message;
        String title = "Urgent Notification";

        System.out.println(
                "Primary channel: " + sender.getChannelName()
        );

        if (sender.isAvailable()
                && sender.sendMessage(recipient, title, urgentMessage)) {
            return;
        }

        System.out.println(
                "Primary channel failed. Trying fallback channel."
        );

        if (fallbackSender != null
                && fallbackSender.isAvailable()) {
            fallbackSender.sendMessage(
                    recipient,
                    title,
                    urgentMessage
            );
            return;
        }

        System.out.println("Fallback channel is unavailable.");
    }

    public void setFallbackSender(MessageSender fallbackSender) {
        this.fallbackSender = fallbackSender;
    }

    public MessageSender getFallbackSender() {
        return fallbackSender;
    }
}