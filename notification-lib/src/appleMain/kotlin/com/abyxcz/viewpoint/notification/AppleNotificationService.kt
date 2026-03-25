package com.abyxcz.viewpoint.notification

import platform.UserNotifications.UNAuthorizationOptionAlert
import platform.UserNotifications.UNAuthorizationOptionBadge
import platform.UserNotifications.UNAuthorizationOptionSound
import platform.UserNotifications.UNMutableNotificationContent
import platform.UserNotifications.UNNotification
import platform.UserNotifications.UNNotificationPresentationOptionAlert
import platform.UserNotifications.UNNotificationPresentationOptionSound
import platform.UserNotifications.UNNotificationPresentationOptions
import platform.UserNotifications.UNNotificationRequest
import platform.UserNotifications.UNTimeIntervalNotificationTrigger
import platform.UserNotifications.UNUserNotificationCenter
import platform.UserNotifications.UNUserNotificationCenterDelegateProtocol
import platform.darwin.NSObject

class AppleNotificationService : NotificationService {
    private val notificationCenter = UNUserNotificationCenter.currentNotificationCenter()
    private val delegate = NotificationDelegate()

    init {
        notificationCenter.delegate = delegate
        requestAuthorization()
    }

    private fun requestAuthorization() {
        notificationCenter.requestAuthorizationWithOptions(
            UNAuthorizationOptionAlert or UNAuthorizationOptionSound or UNAuthorizationOptionBadge,
        ) { granted, error ->
            if (granted) {
                println("Notification permission granted")
            } else {
                println("Notification permission denied: ${error?.localizedDescription}")
            }
        }
    }

    override fun showNotification(
        title: String,
        message: String,
    ) {
        val content =
            UNMutableNotificationContent().apply {
                setTitle(title)
                setBody(message)
                setSound(platform.UserNotifications.UNNotificationSound.defaultSound())
            }

        val trigger = UNTimeIntervalNotificationTrigger.triggerWithTimeInterval(1.0, false)
        val request =
            UNNotificationRequest.requestWithIdentifier(
                identifier = "notification_${platform.Foundation.NSUUID().UUIDString()}",
                content = content,
                trigger = trigger,
            )

        notificationCenter.addNotificationRequest(request) { error ->
            if (error != null) {
                println("Error showing notification: ${error.localizedDescription}")
            }
        }
    }

    private class NotificationDelegate : NSObject(), UNUserNotificationCenterDelegateProtocol {
        override fun userNotificationCenter(
            center: UNUserNotificationCenter,
            willPresentNotification: UNNotification,
            withCompletionHandler: (UNNotificationPresentationOptions) -> Unit,
        ) {
            withCompletionHandler(UNNotificationPresentationOptionAlert or UNNotificationPresentationOptionSound)
        }
    }
}
