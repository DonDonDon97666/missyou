package com.lin.missyou.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lin.missyou.exception.http.ParameterException;
import com.lin.missyou.model.User;
import com.lin.missyou.repository.UserRepository;
import com.lin.missyou.util.JwtToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author ${吴延昭}
 * @create 2020/4/23
 */
@Service
public class WxAuthenticationService {

    @Value("${wx.appid}")
    private String appid;
    @Value("${wx.appsecret}")
    private String appsecret;
    @Value("${wx.code2session}")
    private String code2sessionUrl;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private UserRepository userRepository;

    @SuppressWarnings("all")
    public String code2Session(String code) {
        Map<String, Object> session = new HashMap<>();
        String url = MessageFormat.format(this.code2sessionUrl, appid, appsecret, code);
        RestTemplate rest = new RestTemplate();
        String sessionText = rest.getForObject(url, String.class);
        try {
            session = mapper.readValue(sessionText, Map.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return this.registerUser(session);
    }

    private String registerUser(Map<String, Object> session) {
        String openId = (String) session.get("openid");
        if (StringUtils.isEmpty(openId)) {
            throw new ParameterException(20004);
        }
        Optional<User> userOptional = userRepository.findByOpenid(openId);
        if (userOptional.isPresent()) {
            //TODO 返回JWT令牌
            return JwtToken.makeToken(userOptional.get().getId());
        }
        User user = User.builder()
                .openid(openId)
                .build();
        userRepository.save(user);
        //TODO 返回JWT令牌
        return JwtToken.makeToken(user.getId());
    }
}
