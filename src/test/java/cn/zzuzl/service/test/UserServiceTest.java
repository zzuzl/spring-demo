package cn.zzuzl.service.test;

import cn.zzuzl.domain.User;
import cn.zzuzl.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * Created by Administrator on 2017/9/10.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/spring-config.xml")
public class UserServiceTest {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Resource
    private UserService userService;

    @Test
    public void testAddUser() {
        User user = new User();
        user.setUsername("bb");
        user.setPassword("bb");
        user.setName("bb");
        logger.info("" + userService.addUser(user));
        // DuplicateKeyException
    }
}
