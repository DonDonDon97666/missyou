package com.lin.missyou.exception.http;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author ${吴延昭}
 * @create 2020/4/9
 */
public class NotFoundException extends HttpException {

    public NotFoundException(Integer code){
        this.code = code;
        this.httpStatusCode = 404;
    }
}
