package com.lin.missyou.exception;

import com.lin.missyou.exception.http.HttpException;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author ${吴延昭}
 * @create 2020/4/28
 */
public class DeleteSuccess extends HttpException {
    public DeleteSuccess(Integer code){
        this.code = code;
        this.httpStatusCode = 200;
    }
}
