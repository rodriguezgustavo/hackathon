package com.ml.hackathon.services;

import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.MulticastResult;
import com.google.android.gcm.server.Sender;
import com.ml.hackathon.config.Config;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author mlabarinas
 */
public class NotificationsSender {

    public static void send(String token, Map<String, String> data) {
        List<String> androidTargets = new ArrayList<>();
        androidTargets.add(token);

        Sender sender = new Sender(Config.NOTIFICATION_KEY);
        Message message = new Message.Builder().collapseKey("")
                                               .timeToLive(30)
                                               .delayWhileIdle(true)
                                               .setData(data)
                                               .build();

        try {
            MulticastResult result = sender.send(message, androidTargets, 1);

            if (result.getResults() != null) {
                int canonicalRegId = result.getCanonicalIds();
                if (canonicalRegId != 0) {
                    System.out.print("CannRegId:"+canonicalRegId);
                }

            } else {
                int error = result.getFailure();
                System.out.println("Broadcast failure: " + error);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}