package com.lin.missyou.core;

import com.lin.missyou.model.User;

import java.util.HashMap;
import java.util.Map;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author ${吴延昭}
 * @create 2020/4/27
 */
public class LocalUser {

    private static ThreadLocal<Map<String, Object>> threadLocal = new ThreadLocal<>();

    public static void setUser(User user, Integer scope) {
        Map<String, Object> map = new HashMap<>();
        map.put("user", user);
        map.put("scope", scope);
        LocalUser.threadLocal.set(map);
    }

    public static User getUser() {
        Map<String, Object> map = LocalUser.threadLocal.get();
        return (User) map.get("user");
    }

    public static void clear() {
        LocalUser.threadLocal.remove();
    }
}
