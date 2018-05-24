package cn.zzuzl.web;

import cn.zzuzl.common.util.ResultUtil;
import cn.zzuzl.domain.User;
import cn.zzuzl.domain.vo.ListResult;
import cn.zzuzl.domain.vo.Result;
import cn.zzuzl.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Administrator on 2017/9/10.
 */
@Controller
@RequestMapping
public class HelloController {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Resource
    private UserService userService;

    @RequestMapping("/")
    public String index() {
        return "index";
    }

    @RequestMapping("/listUser")
    @ResponseBody
    public ListResult<User> listUser(String username) {
        ListResult<User> result = null;
        try {
            List<User> users = userService.selectUser(username);
            result = new ListResult<User>(users, true);
        } catch (Exception e) {
            logger.error("selectUser,exception", e);
            result = new ListResult<User>(false, e.getMessage());
        }
        return result;
    }

    @RequestMapping(value = "/deleteUser", method = RequestMethod.POST)
    @ResponseBody
    public Result deleteUser(Long id) {
        Result result = null;
        try {
            Assert.notNull(id, "id为空");
            boolean success = userService.deleteUser(id);
            Assert.isTrue(success, "删除失败");
            result = ResultUtil.successResult();
        } catch (Exception e) {
            logger.error("deleteUser,exception", e);
            result = ResultUtil.exceptionResult(e);
        }
        return result;
    }

    @RequestMapping(value = "/updateUser", method = RequestMethod.POST)
    @ResponseBody
    public Result updateUser(Long id, String name) {
        Result result = null;
        try {
            Assert.notNull(id, "id为空");
            Assert.hasText(name, "name为空");
            boolean success = userService.updateUser(id, name);
            Assert.isTrue(success, "修改失败");
            result = ResultUtil.successResult();
        } catch (Exception e) {
            logger.error("updateUser,exception", e);
            result = ResultUtil.exceptionResult(e);
        }
        return result;
    }

    @RequestMapping(value = "/addUser", method = RequestMethod.POST, consumes = "application/json")
    @ResponseBody
    public Result addUser(@RequestBody(required = true) User user) {
        Result result = null;
        try {
            Assert.notNull(user, "参数为空");
            user.verify();
            boolean success = userService.addUser(user);
            Assert.isTrue(success, "添加失败");
            result = ResultUtil.successResult();
        } catch (Exception e) {
            logger.error("addUser,exception", e);
            result = ResultUtil.exceptionResult(e);
        }
        return result;
    }
}
