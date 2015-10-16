package com.ml.hackathon.ui.views;

import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.MulticastResult;
import com.google.android.gcm.server.Sender;
import org.primefaces.context.RequestContext;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by gurodriguez on 10/12/15.
 */
@ManagedBean(name= OrdersView.BEAN_NAME)
@ViewScoped
public class OrdersView implements Serializable{
    public static final String BEAN_NAME="ordersView";


    // The SENDER_ID here is the "Browser Key" that was generated when I
    // created the API keys for my Google APIs project.
    private static final String SENDER_ID = "598795599341";

    // This is a *cheat*  It is a hard-coded registration ID from an Android device
    // that registered itself with GCM using the same project id shown above.
    private static final String ANDROID_DEVICE = "YOUR_CAPTURED_ANDROID_DEVICE_KEY";

    private String message="Sin arrancar";

    private boolean refreshing;

    public String getMessage(){
        return message;
    }

    public boolean isRefreshing(){
        return refreshing;
    }

    public void process(){

        System.out.println("Comienza push");


        List<String> androidTargets = new ArrayList<>();
        androidTargets.add(ANDROID_DEVICE);

        // Instance of com.android.gcm.server.Sender, that does the
        // transmission of a Message to the Google Cloud Messaging service.
        Sender sender = new Sender(SENDER_ID);

        // This Message object will hold the data that is being transmitted
        // to the Android client devices.  For this demo, it is a simple text
        // string, but could certainly be a JSON object.
        Message message = new Message.Builder()

                // If multiple messages are sent using the same .collapseKey()
                // the android target device, if it was offline during earlier message
                // transmissions, will only receive the latest message for that key when
                // it goes back on-line.
                .collapseKey("")
                .timeToLive(30)
                .delayWhileIdle(true)
                .addData("message", "Mensaje para Ernesto!!!")
                .build();

        try {
            // use this for multicast messages.  The second parameter
            // of sender.send() will need to be an array of register ids.
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




           /* new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                    refreshing=true;
                    message="Primer intento";
                    Thread.sleep(5000);
                    message="Segundo intento";
                    Thread.sleep(5000);
                    message="Tercer intento";
                    Thread.sleep(5000);
                    message="Terminado";
                    refreshing=false;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();*/
    }

}
