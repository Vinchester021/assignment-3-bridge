package com.example.notificationgateway;

public class TelegramMessageSender implements MessageSender {

    private boolean available;

    public TelegramMessageSender(boolean available) {
        this.available = available;
    }

    @Override
    public boolean sendMessage(
            String recipient,
            String title,
            String body
    ) {
        if (!available) {
            System.out.println("[Telegram] Channel is unavailable.");
            return false;
        }

        System.out.println("[Telegram]");
        System.out.println("Recipient: @" + recipient);
        System.out.println("**" + title + "**");
        System.out.println(body);

        return true;
    }

    @Override
    public boolean isAvailable() {
        return available;
    }

    @Override
    public String getChannelName() {
        return "Telegram";
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
}