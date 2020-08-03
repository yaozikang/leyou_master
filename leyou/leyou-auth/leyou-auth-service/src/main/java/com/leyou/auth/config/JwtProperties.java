package com.leyou.auth.config;

import com.leyou.auth.utils.RsaUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.annotation.PostConstruct;
import java.io.File;
import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * @author 要子康
 * @description JwtProperties
 * @since 2020/7/23 21:45
 */
@ConfigurationProperties(prefix = "leyou.jwt")
public class JwtProperties {

    /**
     * 密钥
     */
    private String secret;

    /**
     * 公钥
     */
    private String pubKeyPath;

    /**
     * 私钥
     */
    private String prikeyPath;

    /**
     * token过期时间
     */
    private int expire;

    /**
     * 公钥
     */
    private PublicKey publicKey;

    /**
     * 私钥
     */
    private PrivateKey privateKey;

    /**
     * cookie名称
     */
    private String cookieName;

    /**
     * cookie有效时间
     */
    private Integer cookieMaxAge;

    private static final Logger logger = LoggerFactory.getLogger(JwtProperties.class);

    /**
     * @PostContruct: 在构造方法执行之后执行该方法
     */
    @PostConstruct
    public void init(){
        try{
            File pubKey = new File(pubKeyPath);
            File priKey = new File(prikeyPath);
            if (!pubKey.exists() || !priKey.exists()){
                //生成公钥私钥
                RsaUtils.generateKey(pubKeyPath, prikeyPath, secret);
            }
            //获取公钥和私钥
            this.publicKey = RsaUtils.getPublicKey(pubKeyPath);
            this.privateKey = RsaUtils.getPrivateKey(prikeyPath);
        }catch (Exception e){
            logger.error("初始化公钥和私钥失败!", e);
            throw new RuntimeException();
        }
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getPubKeyPath() {
        return pubKeyPath;
    }

    public void setPubKeyPath(String pubKeyPath) {
        this.pubKeyPath = pubKeyPath;
    }

    public String getPrikeyPath() {
        return prikeyPath;
    }

    public void setPrikeyPath(String prikeyPath) {
        this.prikeyPath = prikeyPath;
    }

    public int getExpire() {
        return expire;
    }

    public void setExpire(int expire) {
        this.expire = expire;
    }

    public PublicKey getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(PublicKey publicKey) {
        this.publicKey = publicKey;
    }

    public PrivateKey getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(PrivateKey privateKey) {
        this.privateKey = privateKey;
    }

    public String getCookieName() {
        return cookieName;
    }

    public void setCookieName(String cookieName) {
        this.cookieName = cookieName;
    }

    public Integer getCookieMaxAge() {
        return cookieMaxAge;
    }

    public void setCookieMaxAge(Integer cookieMaxAge) {
        this.cookieMaxAge = cookieMaxAge;
    }

    @Override
    public String toString() {
        return "JwtProperties{" +
                "secret='" + secret + '\'' +
                ", pubKeyPath='" + pubKeyPath + '\'' +
                ", prikeyPath='" + prikeyPath + '\'' +
                ", expire=" + expire +
                ", publicKey=" + publicKey +
                ", privateKey=" + privateKey +
                ", cookieName='" + cookieName + '\'' +
                ", cookieMaxAge=" + cookieMaxAge +
                '}';
    }
}
