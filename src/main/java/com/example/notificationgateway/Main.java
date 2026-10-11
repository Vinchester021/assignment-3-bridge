package com.example.notificationgateway;

import java.util.List;

public class Main {

    public static void main(String[] args) {

        TelegramMessageSender telegram =
                new TelegramMessageSender(true);

        EmailMessageSender email =
                new EmailMessageSender(
                        true,
                        "Notification Gateway Team"
                );

        SmsMessageSender sms =
                new SmsMessageSender(true);

        KafkaEventSender kafka =
                new KafkaEventSender(true);

        PushNotificationSender push =
                new PushNotificationSender(true);


        System.out.println(
                "=== 1. Urgent Notification + Telegram ==="
        );

        Notification urgent =
                new UrgentNotification(telegram, sms);

        urgent.send(
                "vilgelm",
                "Suspicious login detected."
        );


        System.out.println(
                "\n=== 2. Encrypted Notification + Email ==="
        );

        Notification encrypted =
                new EncryptedNotification(email);

        encrypted.send(
                "user@example.com",
                "Your verification code is 4821."
        );


        System.out.println(
                "\n=== 3. Scheduled Batch Notification + SMS ==="
        );

        ScheduledBatchNotification batch =
                new ScheduledBatchNotification(
                        sms,
                        List.of(
                                "+7 777 111 11 11",
                                "+7 777 222 22 22",
                                "+7 777 333 33 33"
                        )
                );

        batch.sendBatch(
                "The system will be updated tonight."
        );


        System.out.println(
                "\n=== 4. Runtime Switching: Telegram to Email ==="
        );

        Notification switchingNotification =
                new MarketingNotification(telegram);

        switchingNotification.send(
                "vilgelm",
                "New service is now available."
        );

        switchingNotification.setSender(email);

        switchingNotification.send(
                "user@example.com",
                "New service is now available."
        );


        System.out.println(
                "\n=== 5. Telegram Unavailable: Fallback to SMS ==="
        );

        telegram.setAvailable(false);

        Notification fallbackNotification =
                new UrgentNotification(telegram, sms);

        fallbackNotification.send(
                "+7 777 123 45 67",
                "Security warning."
        );


        System.out.println(
                "\n=== 6. Marketing Notification + Push ==="
        );

        Notification marketing =
                new MarketingNotification(push);

        marketing.send(
                "device-001",
                "Get a 20% discount this weekend!"
        );


        System.out.println(
                "\n=== 7. Additional Kafka Demonstration ==="
        );

        Notification kafkaNotification =
                new MarketingNotification(kafka);

        kafkaNotification.send(
                "analytics-service",
                "A new campaign has started."
        );
    }
}