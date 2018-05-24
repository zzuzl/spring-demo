package cn.zzuzl.service;

import cn.zzuzl.dao.UserDao;
import cn.zzuzl.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Administrator on 2017/9/10.
 */
@Service
public class UserService {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Resource
    private UserDao userDao;

    public boolean addUser(User user) {
        return userDao.addUser(user) > 0;
    }

    public boolean updateUser(Long id, String name) {
        return userDao.updateUser(id, name) == 1;
    }

    public List<User> selectUser(String username) {
        if(!StringUtils.hasText(username)) {
            username = null;
        }
        return userDao.selectUser(username);
    }

    public User getById(Long id) {
        return userDao.getById(id);
    }

    public boolean deleteUser(Long id) {
        return userDao.deleteUser(id) == 1;
    }
}
