package com.ml.hackathon.ui.views;

import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.MulticastResult;
import com.google.android.gcm.server.Sender;
import com.ml.hackathon.ApplicationControllerBean;
import com.ml.hackathon.HomePageControllerBean;
import com.ml.hackathon.ui.views.util.LazyOrdersDataModel;
import com.ml.hackathon.ui.views.util.LazyShippersDataModel;
import org.primefaces.context.RequestContext;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
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

    private LazyOrdersDataModel ordersDataModel;
    private ApplicationControllerBean appBean;

    @PostConstruct
    public void init(){
        appBean=(ApplicationControllerBean) FacesContext.getCurrentInstance().getExternalContext().getApplicationMap().get(ApplicationControllerBean.BEAN_NAME);
        ordersDataModel= new LazyOrdersDataModel(appBean.getOrders());
    }



    // The SENDER_ID here is the "Browser Key" that was generated when I
    // created the API keys for my Google APIs project.
    private static final String API_KEY = "AIzaSyCyalL6pxqpD29ap3yYLdZ3CAEldhhuwhI";

    // This is a *cheat*  It is a hard-coded registration ID from an Android device
    // that registered itself with GCM using the same project id shown above.
    private static final String ANDROID_DEVICE = "eVyd8APINs0:APA91bG33oxeZqTndKXwbI6I6qiqLef9ECn9yp5PktSHlpFr4NUjbMv5lWkfNeIvBUB5G-aQvdDAayLOvK7WAu5HGt2vVauG4ezQkIuKigczNk610AkgGvI_gJEOIWi5huJZkYVsQTk7";

    private String message="Sin arrancar";

    private boolean refreshing;

    public String getMessage(){
        return message;
    }

    public boolean isRefreshing(){
        return refreshing;
    }





    public void process(){

        List<String> androidTargets = new ArrayList<>();
        androidTargets.add(ANDROID_DEVICE);

        // Instance of com.android.gcm.server.Sender, that does the
        // transmission of a Message to the Google Cloud Messaging service.
        Sender sender = new Sender(API_KEY);


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
                .addData("title", "Mensaje para Ernesto!!!")
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
