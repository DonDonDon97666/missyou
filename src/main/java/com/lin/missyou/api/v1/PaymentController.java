package com.lin.missyou.api.v1;

import com.lin.missyou.core.annotations.ScopeLevel;
import com.lin.missyou.lib.WxNotify;
import com.lin.missyou.service.WxPaymentNotifyService;
import com.lin.missyou.service.WxPaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.Positive;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author ${吴延昭}
 * @create 2020/5/8
 */
@RestController
@RequestMapping("/payment")
@Validated
public class PaymentController {
    @Autowired
    private WxPaymentService wxPaymentService;
    @Autowired
    private WxPaymentNotifyService wxPaymentNotifyService;

    @GetMapping("/pay/order/{id}")
    @ScopeLevel
    public Map<String, String> preWxOrder(@PathVariable(name = "id") @Positive Long oid) {
        return this.wxPaymentService.preOrder(oid);
    }

    @RequestMapping("/wx/notify")
    public String payCallback(HttpServletRequest request, HttpServletResponse response) {
        InputStream s;
        try {
            s = request.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
            return WxNotify.fail();
        }

        String strXml = WxNotify.readNotify(s);
        try {
            this.wxPaymentNotifyService.processPayNotify(strXml);
        } catch (Exception e) {
            return WxNotify.fail();
        }
        return WxNotify.success();
    }
}
