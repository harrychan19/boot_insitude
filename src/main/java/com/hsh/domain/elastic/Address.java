package com.hsh.domain.elastic;

import java.io.Serializable;

/**
 * @author hushihai
 * @version V1.0, 2018/11/11
 */
public class Address implements Serializable{
    private static final long serialVersionUID = -4054306353849705673L;

    private String province;

    private City city;

    public String getProvince(){
        return this.province;
    }
    public void setProvince(String province){
        this.province = province;
    }

    public City getCity(){
        return this.city;
    }
    public void setCity(City city){
        this.city = city;
    }
}
