package com.ml.hackathon.ui.views;

import com.ml.hackathon.ApplicationControllerBean;
import com.ml.hackathon.HomePageControllerBean;
import com.ml.hackathon.domain.Shipper;
import org.primefaces.event.map.OverlaySelectEvent;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.util.List;

/**
 * Created by gurodriguez on 10/11/15.
 */
@ManagedBean(name= MapLocationView.BEAN_NAME)
@ViewScoped
public class MapLocationView implements Serializable {

    public static final String BEAN_NAME="mapLocationView";

    private static final Double  BS_AS_CENTER_LAT=-34.5933382d;
    private static final Double  BS_AS_CENTER_LONG=-58.4360692d;

    private ApplicationControllerBean appBean;

    private  MapModel mapModel;

    @PostConstruct
    public void init() {
        appBean=(ApplicationControllerBean)FacesContext.getCurrentInstance().getExternalContext().getApplicationMap().get(ApplicationControllerBean.BEAN_NAME);
        mapModel = new DefaultMapModel();
    }

    public Double getMapCenterLatitude(){
        return BS_AS_CENTER_LAT;
    }
    public Double getMapCenterLongitude(){
        return BS_AS_CENTER_LONG;
    }

    public MapModel getSimpleModel() {
        mapModel.getMarkers().clear();
        List<Shipper> shippers=appBean.getShippers();
        for(Shipper shipper:shippers){
            if(shipper.isActive()){
                LatLng coord = new LatLng(shipper.getLatitude(), shipper.getLongitude());
                mapModel.addOverlay(new Marker(coord, shipper.getName()));
            }
        }
       return mapModel;
    }

}
