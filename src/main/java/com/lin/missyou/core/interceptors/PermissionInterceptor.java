package com.lin.missyou.core.interceptors;

import com.auth0.jwt.interfaces.Claim;
import com.lin.missyou.core.LocalUser;
import com.lin.missyou.core.annotations.ScopeLevel;
import com.lin.missyou.exception.http.ForbiddenException;
import com.lin.missyou.exception.http.NotFoundException;
import com.lin.missyou.exception.http.UnAuthenticatedException;
import com.lin.missyou.model.User;
import com.lin.missyou.service.UserService;
import com.lin.missyou.util.JwtToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.Optional;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author ${吴延昭}
 * @create 2020/4/25
 */
public class PermissionInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private UserService userService;

    public PermissionInterceptor() {
        super();
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Optional<ScopeLevel> scopeLevel = this.getScopeLevel(handler);
        if (!scopeLevel.isPresent()) {
            return true;
        }

        String token = this.getToken(request);

        Optional<Map<String, Claim>> claims = JwtToken.getClaims(token);

        Map<String, Claim> claimMap = claims.orElseThrow(() -> new ForbiddenException(10005));

        boolean hasPermission = this.hasPermission(scopeLevel, claimMap);
        if (hasPermission) {
            this.setUserToThreadLocal(claimMap);
        }
        return hasPermission;
    }

    private void setUserToThreadLocal(Map<String, Claim> claimMap) {
        Long uid = claimMap.get("uid").asLong();
        Integer scope = claimMap.get("scope").asInt();
        Optional<User> user = userService.getUserById(uid);
        if (!user.isPresent()) {
            throw new NotFoundException(10004);
        }
        LocalUser.setUser(user.get(), scope);
    }

    private boolean hasPermission(Optional<ScopeLevel> scopeLevel, Map<String, Claim> claimMap) {
        Integer level = scopeLevel.get().value();
        Integer scope = claimMap.get("scope").asInt();
        return level <= scope;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        LocalUser.clear();
        super.afterCompletion(request, response, handler, ex);
    }

    private Optional<ScopeLevel> getScopeLevel(Object handler) {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            ScopeLevel scopeLevel = handlerMethod.getMethod().getAnnotation(ScopeLevel.class);
            if (scopeLevel == null) {
                return Optional.empty();
            }
            return Optional.of(scopeLevel);
        }
        return Optional.empty();
    }

    private String getToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.isEmpty(bearerToken) ||
                !bearerToken.startsWith("Bearer")) {
            throw new UnAuthenticatedException(10004);
        }
        String[] token = bearerToken.split(" ");
        if (token.length != 2) {
            throw new UnAuthenticatedException(10004);
        }
        return token[1];
    }
}
