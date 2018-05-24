package cn.zzuzl.domain;

import org.springframework.util.Assert;

/**
 * Created by Administrator on 2017/9/10.
 */
public class User {
    private Long id;
    private String username;
    private String password;
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void verify() {
        Assert.hasText(username, "username为空");
        Assert.hasText(password, "password为空");
        Assert.hasText(name, "name为空");
    }
}
