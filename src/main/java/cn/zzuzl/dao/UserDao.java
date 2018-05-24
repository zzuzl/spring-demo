package cn.zzuzl.dao;

import cn.zzuzl.domain.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by Administrator on 2017/9/10.
 */
public interface UserDao {
    int addUser(User user);

    int updateUser(@Param("id") Long id, @Param("name") String name);

    List<User> selectUser(@Param("username") String username);

    User getById(@Param("id")Long id);

    int deleteUser(@Param("id")Long id);

}
