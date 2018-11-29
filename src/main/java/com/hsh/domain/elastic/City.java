package com.hsh.domain.elastic;

import java.io.Serializable;

/**
 * @author hushihai
 * @version V1.0, 2018/11/11
 */
public class City implements Serializable {
    private static final long serialVersionUID = 7989325227970342183L;

    private String name;

    private String code;

    public String getName(){
        return this.name;
    }
    public void setName(String name){
        this.name = name;
    }

    public String getCode(){
        return this.code;
    }
    public void setCode(String code){
        this.code = code;
    }
}
