package com.hsh.service.mongo;

import com.hsh.domain.mongo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

/**
 * @author hushihai
 * @version V1.0, 2018/11/6
 */
@Service
public class UserService {

    @Autowired
    MongoTemplate mongoTemplate;

    /**
     * 插入用户
     *  */
    public void insert(User user) {
        mongoTemplate.insert(user);
    }

    /**
     * 根据id查找用户
     * */
    @Cacheable(key = "'findUserById'+#id",value = "boot")
    public User findUserById(String id) {
        return mongoTemplate.findById(id,User.class);
    }

    /**
     * 更新用户信息
     * */
    public void updateUser(User user){
        Query query = new Query().addCriteria(Criteria.where("id").is(user.getId()));
        Update update = new Update();
        mongoTemplate.updateFirst(query,update,User.class);
    }
}
