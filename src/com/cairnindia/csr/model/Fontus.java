package com.cairnindia.csr.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by sahil on 2/6/17.
 */
@XmlRootElement(name = "production")
public class Fontus {

    private String area_name;
    private String launch_date;


    @XmlElement
    public void setArea_name(String area_name){
        this.area_name = area_name;
    }

    public String getArea_name(){
        return area_name;
    }

    @XmlElement
    public void setLaunch_date(String launch_date){
        this.launch_date = launch_date;
    }

    public String getLaunch_date(){
        return launch_date;
    }


}
