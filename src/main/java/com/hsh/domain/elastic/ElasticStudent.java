package com.hsh.domain.elastic;

import com.hsh.common.config.ElasticIndexConfig;
import com.hsh.common.utils.EsDoc;
import io.searchbox.annotations.JestId;

import java.io.Serializable;
import java.util.List;

/**
 * @author hushihai
 * @version V1.0, 2018/11/11
 */
public class ElasticStudent implements Serializable,EsDoc {

    private static final long serialVersionUID = 8780298315002628595L;
    @JestId
    private Long id;

    private String name;

    private Address address;

    private List<Hobby> hobby;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public List<Hobby> getHobby() {
        return hobby;
    }

    public void setHobby(List<Hobby> hobby) {
        this.hobby = hobby;
    }

    @Override
    public String getDocId() {
        return id.toString();
    }

    @Override
    public String getIndex() {
        return ElasticIndexConfig.STU_INDEX;
    }

    @Override
    public String getType() {
        return ElasticIndexConfig.STU;
    }
}
