package com.lin.missyou.exception.http;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author ${吴延昭}
 * @create 2020/4/25
 */
public class UnAuthenticatedException extends HttpException {
    public UnAuthenticatedException(Integer code) {
        this.code = code;
        this.httpStatusCode = 401;
    }
}
