package com.ml.hackathon.jobs;

import com.ml.hackathon.algorithms.Pricer;
import com.ml.hackathon.algorithms.Scorer;
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

                    String fromJson = "{\"latitude\":" + from.getLatitude() + ", \"longitude\":" + from.getLongitude() + "}";
                    String toJson = "{\"latitude\":" + to.getLatitude() + ", \"longitude\":" + to.getLongitude() + "}";


                    for(ShipperScore shipperScore : shippersScores) {
                        Map<String, String> data = new HashMap<String, String>();
                        data.put("order_id", order.getSellerAddress());
                        data.put("address_from", order.getSellerAddress());
                        data.put("address_to", order.getReceiverAddress());
                        data.put("geo_from", fromJson);
                        data.put("geo_to", toJson);
                        data.put("shipping_price",order.getShippingPrice().toString());

                        NotificationsSender.send(shipperScore.getShipper().getToken(), data);
                    }


                }

            } catch (Exception e) {
                log.error("Unexpected exception processing BroadcastNotificationsJob", e);
            }
        }
    }

}
