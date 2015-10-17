package com.ml.hackathon;

import com.ml.hackathon.db.OrderDao;
import com.ml.hackathon.db.ShippersDao;
import com.ml.hackathon.domain.Order;
import com.ml.hackathon.domain.Shipper;
import org.apache.log4j.Logger;

import javax.annotation.PostConstruct;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import java.io.Serializable;
import java.util.List;

/**
 * Created by gurodriguez
 */
@ManagedBean(name = ApplicationControllerBean.BEAN_NAME, eager = true)
@ApplicationScoped
public class ApplicationControllerBean implements Serializable {

    static final Logger log = Logger.getLogger(ApplicationControllerBean.class);

    public static final String BEAN_NAME="application";

    private List<Shipper> shippers;
    private List<Order> orders;

    @PostConstruct
    public void init() {
        try {
            shippers = ShippersDao.getShippers();
            orders = OrderDao.getOrders();

        } catch(Exception e) {
            log.error("Unexpected error in init method", e);
        }

        //new Thread(new BroadcastNotificationsJob()).start();
    }

    public List<Shipper> getShippers(){
        return shippers;
    }

    public List<Order> getOrders(){
        return orders;
    }

}