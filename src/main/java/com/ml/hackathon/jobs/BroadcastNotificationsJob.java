package com.ml.hackathon.jobs;

import com.ml.hackathon.algorithms.Scorer;
import com.ml.hackathon.db.OrderDao;
import com.ml.hackathon.db.ShippersDao;
import com.ml.hackathon.domain.Order;
import com.ml.hackathon.domain.OrderStatus;
import com.ml.hackathon.domain.Shipper;
import com.ml.hackathon.domain.ShipperScore;
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
                    List<Shipper> shippers = ShippersDao.getShippers();
                    List<ShipperScore> shippersScores = Scorer.getShippersForOrder(order, shippers);

                    for(ShipperScore shipperScore : shippersScores) {
                        Map<String, String> data = new HashMap<String, String>();

                       NotificationsSender.send(shipperScore.getShipper().getToken(), data);
                    }

                    OrderDao.updateOrderStatus(order.getOrderId(), OrderStatus.PROCESSED.toString());
                }

            } catch (Exception e) {
                log.error("Unexpected exception processing BroadcastNotificationsJob", e);
            }
        }
    }

}