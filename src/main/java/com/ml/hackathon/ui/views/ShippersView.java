package com.ml.hackathon.ui.views;

/**
 * Created by gurodriguez on 5/29/15.
 */

import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import com.ml.hackathon.ApplicationControllerBean;
import com.ml.hackathon.domain.Shipper;
import com.ml.hackathon.ui.views.util.LazyShippersDataModel;
import org.primefaces.model.LazyDataModel;

@ManagedBean(name= ShippersView.BEAN_NAME)
@ViewScoped
public class ShippersView implements Serializable {
    public static final String BEAN_NAME="shippersView";

    private ApplicationControllerBean appBean;

    private LazyShippersDataModel shippersDataModel;

    @PostConstruct
    public void init(){
        appBean=(ApplicationControllerBean) FacesContext.getCurrentInstance().getExternalContext().getApplicationMap().get(ApplicationControllerBean.BEAN_NAME);
        shippersDataModel= new LazyShippersDataModel(appBean.getShippers());
    }

    public LazyDataModel<Shipper> getLazyModel() {
        return shippersDataModel;
    }

}