package com.ml.hackathon.jobs;

import com.google.gson.Gson;
import com.ml.hackathon.algorithms.Scorer;
import com.ml.hackathon.config.Config;
import com.ml.hackathon.db.OrderDao;
import com.ml.hackathon.db.ShippersDao;
import com.ml.hackathon.domain.*;
import com.ml.hackathon.services.Geocoding;
import com.ml.hackathon.services.NotificationsSender;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author mlabarinas
 */
public class BroadcastNotificationsJob implements Runnable {

    static final Logger log = Logger.getLogger(BroadcastNotificationsJob.class);

    private Gson gson = new Gson();

    public void run() {
        log.info("Start BroadcastNotifications job");

        while(true) {
            try {
                List<Order> orders = OrderDao.getOrdersByStatus(OrderStatus.PENDING.toString());

                for(Order order : orders) {
                    OrderDao.updateOrderStatus(order.getOrderId(), OrderStatus.INVITATION_SENT);

                    List<Shipper> shippers = ShippersDao.getShippers();
                    List<ShipperScore> shippersScores = Scorer.getShippersForOrder(order, shippers);

                    LatLon from = Geocoding.geocode(order.getSellerAddress());
                    LatLon to   = Geocoding.geocode(order.getReceiverAddress());

                    Map<String, Double> geo = new HashMap<String, Double>();
                    geo.put("latitude", from.getLatitude());
                    geo.put("longitude", from.getLongitude());

                    String fromJson = gson.toJson(geo);

                    geo.put("latitude", to.getLatitude());
                    geo.put("longitude", to.getLongitude());

                    String toJson = gson.toJson(geo);

                    for(ShipperScore shipperScore : shippersScores) {
                        Map<String, String> data = new HashMap<String, String>();
                        data.put("order_id", order.getOrderId().toString());
                        data.put("address_from", order.getSellerAddress());
                        data.put("address_to", order.getReceiverAddress());
                        data.put("geo_from", fromJson);
                        data.put("geo_to", toJson);
                        data.put("shipping_price",order.getShippingPrice().toString());

                        NotificationsSender.send(shipperScore.getShipper().getToken(), data);
                    }
                }

                if(orders.isEmpty()) {
                    Thread.sleep(Config.BROADCAST_NOTIFICATIONS_JOB_SLEEP);
                }

            } catch (Exception e) {
                log.error("Unexpected exception processing BroadcastNotificationsJob", e);
            }
        }
    }

}
