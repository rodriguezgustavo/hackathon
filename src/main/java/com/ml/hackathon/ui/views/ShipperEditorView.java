package com.ml.hackathon.ui.views;

/**
 * Created by gurodriguez on 7/7/15.
 */


import com.ml.hackathon.ApplicationControllerBean;
import com.ml.hackathon.HomePageControllerBean;
import com.ml.hackathon.db.ShippersDao;
import com.ml.hackathon.domain.Shipper;
import com.ml.hackathon.ui.views.util.LazyShippersDataModel;
import org.primefaces.context.RequestContext;
import org.primefaces.model.LazyDataModel;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.util.Map;


@ManagedBean(name= ShipperEditorView.BEAN_NAME)
@ViewScoped
public class ShipperEditorView implements Serializable {
    public static final String BEAN_NAME="shipperEditorView";

    private HomePageControllerBean homePageBean;

    private ApplicationControllerBean appBean;

    private Shipper selectedShipper;

    @PostConstruct
    public void init(){
        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
        homePageBean = (HomePageControllerBean) context.getSessionMap().get(HomePageControllerBean.BEAN_NAME);
        appBean=(ApplicationControllerBean) context.getApplicationMap().get(ApplicationControllerBean.BEAN_NAME);
    }


    public void cancelEdition(){
        homePageBean.setCenterPage("shippers");
    }

    public void saveEdition(){
        FacesContext context = FacesContext.getCurrentInstance();
        if(this.selectedShipper.getId()==0){
            this.selectedShipper=ShippersDao.addShipper(this.selectedShipper);
            if(selectedShipper.getId()>0){
                context.addMessage(null, new FacesMessage("Shipper creado con éxito",  null) );
                appBean.getShippers().add(selectedShipper);
            }else{
                context.addMessage(null, new FacesMessage("No se pudo crear el shipper",  null) );
            }
        }else{
            if(ShippersDao.updateShipper(this.selectedShipper)){
                context.addMessage(null, new FacesMessage("Shipper actualizado con éxito",  null) );
            }else{
                context.addMessage(null, new FacesMessage("No se pudo actualizar el shipper",  null) );
            }
        }
        homePageBean.setCenterPage("shippers");
    }

    public Shipper getSelectedShipper(){
        return selectedShipper;
    }

    private void setSelectedShipper(Shipper selectedShipper) {
        this.selectedShipper = selectedShipper;
    }

    public void onShipperAdd() {
        setSelectedShipper(new Shipper());
        homePageBean.setCenterPage("shipperEdition");
        RequestContext.getCurrentInstance().update("mainPanel");
    }

    public void onShipperEdit(Shipper shipper) {
        setSelectedShipper(shipper);
        homePageBean.setCenterPage("shipperEdition");
        RequestContext.getCurrentInstance().update("mainPanel");
    }



}