package com.hsh.common.utils;

/**
 * @author hushihai
 * @version V1.0, 2018/11/12
 */
public interface ESDoc {

    //自定义索引文档ID，需要在实现类中添加一个文档id字段并使用@JestId注解,如果返回空则使用ES自动生成的文档ID
    String getDocId();

    //文档所属的索引名，一般为XX-XXX-yyyy.MM.dd
    String getIndex();

    //ES允许每个索引包含多个Type的文档
    String getType();
}