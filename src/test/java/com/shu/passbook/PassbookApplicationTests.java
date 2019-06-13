package com.shu.passbook;

import com.alibaba.fastjson.JSON;
import com.shu.passbook.service.IUserService;
import com.shu.passbook.vo.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PassbookApplicationTests {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private IUserService userService;

    @Test
    public void contextLoads() {
    }

    @Test
    public void testRedisTemplate() {
        redisTemplate.execute((RedisCallback<Object>) connection -> {
            connection.flushAll();
            return null;
        });
        assert redisTemplate.opsForValue().get("name") == null;
        redisTemplate.opsForValue().set("name","shu");
        assert redisTemplate.opsForValue().get("name") != null;
        System.out.println(redisTemplate.opsForValue().get("name"));

    }

    /**
     * 测试创建user
     * @throws Exception
     */
    @Test
    public void testCreateUser()throws Exception{
        User user = new User();
        user.setBaseInfo(new User.BaseInfo("yang",18,"m"));
        user.setOtherInfo(new User.OtherInfo("13722434322","上海"));
        System.out.println(JSON.toJSONString(userService.createUser(user)));
    }
}
