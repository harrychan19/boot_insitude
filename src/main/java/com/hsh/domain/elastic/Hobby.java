package com.hsh.domain.elastic;

import java.io.Serializable;

/**
 * @author hushihai
 * @version V1.0, 2018/11/11
 */
public class Hobby implements Serializable{
    private static final long serialVersionUID = -8342198486902382843L;
    private String category;

    private String name;

    public String getCategory(){
        return this.category;
    }
    public void setCategory(String category){
        this.category = category;
    }

    public String getName(){
        return this.name;
    }
    public void setName(String name){
        this.name = name;
    }
}
