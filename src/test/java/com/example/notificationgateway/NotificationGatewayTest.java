package com.example.notificationgateway;

import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class NotificationGatewayTest {

    @Test
    void telegramSuccessfullySendsMessage() {
        TelegramMessageSender telegram =
                new TelegramMessageSender(true);

        boolean result = telegram.sendMessage(
                "vilgelm",
                "Test",
                "Hello!"
        );

        assertTrue(result);
        assertEquals("Telegram", telegram.getChannelName());
    }

    @Test
    void unavailableProviderReturnsFalse() {
        EmailMessageSender email =
                new EmailMessageSender(false, "Team");

        boolean result = email.sendMessage(
                "user@example.com",
                "Test",
                "Hello!"
        );

        assertFalse(result);
        assertFalse(email.isAvailable());
    }

    @Test
    void urgentNotificationUsesFallback() {
        RecordingMessageSender primary =
                new RecordingMessageSender(false, "Primary");

        RecordingMessageSender fallback =
                new RecordingMessageSender(true, "Fallback");

        UrgentNotification notification =
                new UrgentNotification(primary, fallback);

        notification.send("user", "Danger detected");

        assertEquals(0, primary.getSendCount());
        assertEquals(1, fallback.getSendCount());
        assertTrue(fallback.getLastBody().startsWith("[URGENT]"));
    }

    @Test
    void smsAcceptsMessageWith160Characters() {
        SmsMessageSender sms =
                new SmsMessageSender(true);

        boolean result = sms.sendMessage(
                "+7 777 123 45 67",
                "Test",
                "A".repeat(160)
        );

        assertTrue(result);
    }

    @Test
    void smsRejectsMessageLongerThan160Characters() {
        SmsMessageSender sms =
                new SmsMessageSender(true);

        boolean result = sms.sendMessage(
                "+7 777 123 45 67",
                "Test",
                "A".repeat(161)
        );

        assertFalse(result);
    }

    @Test
    void encryptedNotificationTransformsMessage() {
        RecordingMessageSender sender =
                new RecordingMessageSender(true, "Test");

        EncryptedNotification notification =
                new EncryptedNotification(sender);

        notification.send("user", "Secret message");

        String expected = Base64.getEncoder()
                .encodeToString(
                        "Secret message".getBytes(
                                StandardCharsets.UTF_8
                        )
                );

        assertEquals(expected, sender.getLastBody());
        assertNotEquals("Secret message", sender.getLastBody());
    }

    @Test
    void batchNotificationSendsToEveryRecipient() {
        RecordingMessageSender sender =
                new RecordingMessageSender(true, "Test");

        ScheduledBatchNotification notification =
                new ScheduledBatchNotification(
                        sender,
                        List.of("user1", "user2", "user3")
                );

        notification.sendBatch("Batch message");

        assertEquals(3, sender.getSendCount());
    }

    @Test
    void notificationCanSwitchSenderAtRuntime() {
        RecordingMessageSender telegram =
                new RecordingMessageSender(true, "Telegram");

        RecordingMessageSender email =
                new RecordingMessageSender(true, "Email");

        Notification notification =
                new MarketingNotification(telegram);

        notification.send("user1", "First message");
        notification.setSender(email);
        notification.send("user2", "Second message");

        assertEquals(1, telegram.getSendCount());
        assertEquals(1, email.getSendCount());
        assertSame(email, notification.getSender());
    }

    @Test
    void pushNotificationSuccessfullySendsMessage() {
        PushNotificationSender push =
                new PushNotificationSender(true);

        boolean result = push.sendMessage(
                "device-001",
                "Test",
                "Push message"
        );

        assertTrue(result);
        assertEquals(
                "Push Notification",
                push.getChannelName()
        );
    }

    @Test
    void marketingNotificationAddsPrefix() {
        RecordingMessageSender sender =
                new RecordingMessageSender(true, "Test");

        MarketingNotification notification =
                new MarketingNotification(sender);

        notification.send("user", "New offer");

        assertEquals(
                "[MARKETING] New offer",
                sender.getLastBody()
        );

        assertEquals(
                "Special Offer",
                sender.getLastTitle()
        );
    }

    @Test
    void urgentNotificationDoesNotSendWhenAllProvidersUnavailable() {
        RecordingMessageSender primary =
                new RecordingMessageSender(false, "Primary");

        RecordingMessageSender fallback =
                new RecordingMessageSender(false, "Fallback");

        UrgentNotification notification =
                new UrgentNotification(primary, fallback);

        notification.send("user", "Emergency");

        assertEquals(0, primary.getSendCount());
        assertEquals(0, fallback.getSendCount());
    }


    private static class RecordingMessageSender
            implements MessageSender {

        private final boolean available;
        private final String channelName;

        private int sendCount;
        private String lastTitle;
        private String lastBody;

        private RecordingMessageSender(
                boolean available,
                String channelName
        ) {
            this.available = available;
            this.channelName = channelName;
        }

        @Override
        public boolean sendMessage(
                String recipient,
                String title,
                String body
        ) {
            if (!available) {
                return false;
            }

            sendCount++;
            lastTitle = title;
            lastBody = body;

            return true;
        }

        @Override
        public boolean isAvailable() {
            return available;
        }

        @Override
        public String getChannelName() {
            return channelName;
        }

        public int getSendCount() {
            return sendCount;
        }

        public String getLastTitle() {
            return lastTitle;
        }

        public String getLastBody() {
            return lastBody;
        }
    }
}