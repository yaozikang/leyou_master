package com.leyou.service;

import com.leyou.common.utils.CodecUtils;
import com.leyou.common.utils.NumberUtils;
import com.leyou.mapper.UserMapper;
import com.leyou.user.pojo.User;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author 要子康
 * @description UserService
 * @since 2020/7/18 14:52
 */
@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private AmqpTemplate amqpTemplate;

    /**
     * redis缓存短信code
     */
    private static final String KEY_PREFIX = "user:code:phone:";

    private static Logger logger = LoggerFactory.getLogger(UserService.class);

    /**
     * 账号手机号校验
     * @param data
     * @param type
     * @return
     */
    public Boolean checkData(String data, Integer type){
        User record = new User();
        switch (type){
            case 1:
                record.setUsername(data);
                break;
            case 2:
                record.setPhone(data);
                break;
            default:
                return null;
        }
        return this.userMapper.selectCount(record) == 0;
    }

    /**
     * 发送验证码
     * @param phone
     * @return
     */
    public Boolean sendVerifyCode(String phone){

        //手机号校验
        if (StringUtils.isBlank(phone)){
            return null;
        }

        //生成验证码
        String code = NumberUtils.generateCode(6);
        System.out.println(code);

        try {
            Map<String, String> msg = new HashMap<>();
            msg.put("phone", phone);
            msg.put("code", code);
            //发送短信
            this.amqpTemplate.convertAndSend("leyou.sms.exchange","verify.code.sms", msg);
            //将code保存到redis中
            this.redisTemplate.opsForValue().set(KEY_PREFIX + phone, code, 5, TimeUnit.MINUTES);
            return true;
        }catch (Exception e){
            logger.error("发送短信失败。phone：{}， code：{}", phone, code);
            return false;
        }

    }

    /**
     * 账号密码校验
     * @param username
     * @param password
     * @return
     */
    public User queryUser(String username, String password){
        //查询
        User record = new User();
        record.setUsername(username);
        User user = this.userMapper.selectOne(record);

        //校验用户名
        if (user == null){
            return null;
        }
        //校验密码
        if (!user.getPassword().equals(CodecUtils.md5Hex(password, user.getSalt()))){
            return null;
        }
        //用户密码都正确
        return user;
    }

    /**
     * 用户注册
     * @param user
     * @param code
     * @return
     */
    public Boolean register(User user, String code){
        //校验短信验证码
        String cacheCode = this.redisTemplate.opsForValue().get(KEY_PREFIX + user.getPhone());
        if (!StringUtils.equals(code, cacheCode)){
            return false;
        }
        //校验是否已注册

        if (StringUtils.isNotBlank(this.userMapper.selectByPhone(user.getPhone()))){
            return false;
        }
        //生成盐
        String salt = CodecUtils.generateSalt();
        user.setSalt(salt);
        //对密码加密
        user.setPassword(CodecUtils.md5Hex(user.getPassword(), salt));
        //强制设置不能指定的参数为null
        user.setId(null);
        user.setCreated(new Date());
        //添加到数据库
        boolean b = this.userMapper.insertSelective(user) == 1;
        if (b){
            //注册成功，删除redis的记录
            this.redisTemplate.delete(KEY_PREFIX + user.getPhone());
        }

        return b;

    }
}
