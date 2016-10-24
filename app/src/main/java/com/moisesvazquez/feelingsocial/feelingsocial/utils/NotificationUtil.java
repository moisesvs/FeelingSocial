package com.moisesvazquez.feelingsocial.feelingsocial.utils;

/**
 * BSD-style license; for more info see http://pmd.sourceforge.net/license.html
 */

import android.app.Notification;
import android.content.Context;

import com.moisesvazquez.feelingsocial.feelingsocial.R;

import br.com.goncalves.pugnotification.notification.PugNotification;

/**
 * A number of String-specific utility methods for use by PMD or its IDE
 * plugins.
 */
public final class NotificationUtil {

    /**
     * Show simple notification mode
     *
     * @param title   the title notification
     * @param message the message notification
     */
    public static void showSimpleNotificationMode(Context context, String title, String message) {
        PugNotification .with(context)
                        .load()
                        .title(title)
                        .message(message)
                        .smallIcon(R.drawable.pugnotification_ic_launcher)
                        .largeIcon(R.drawable.pugnotification_ic_launcher)
                        .flags(Notification.DEFAULT_ALL)
                        .simple()
                        .build();

    }

}