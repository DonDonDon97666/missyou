package com.lin.missyou.exception.http;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author ${吴延昭}
 * @create 2020/4/9
 */
public class ParameterException extends HttpException {

    public ParameterException(Integer code){
        this.code = code;
        this.httpStatusCode = 400;
    }
}
