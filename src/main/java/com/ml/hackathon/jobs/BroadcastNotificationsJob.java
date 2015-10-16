package com.ml.hackathon.jobs;

import com.ml.hackathon.db.OrderDao;
import com.ml.hackathon.domain.Order;
import com.ml.hackathon.domain.OrderStatus;
import com.ml.hackathon.domain.Shipper;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

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
                    // Shipper from Serivce
                    List<Shipper> shippers = new ArrayList<Shipper>();

                    for(Shipper shipper : shippers) {
                        // Broadcast notifications
                    }

                    OrderDao.updateOrderStatus(order.getOrderId(), OrderStatus.PROCESSED.toString());
                }

            } catch (Exception e) {
                log.error("Unexpected exception processing BroadcastNotificationsJob", e);
            }
        }
    }

}