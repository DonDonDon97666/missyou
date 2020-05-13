package com.lin.missyou.api.v1;

import com.lin.missyou.exception.http.NotFoundException;
import com.lin.missyou.model.Activity;
import com.lin.missyou.service.ActivityService;
import com.lin.missyou.vo.ActivityCouponVO;
import com.lin.missyou.vo.ActivityPureVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author ${吴延昭}
 * @create 2020/4/26
 */
@RestController
@RequestMapping("/activity")
public class ActivityController {

    @Autowired
    private ActivityService activityService;

    @GetMapping("/name/{name}")
    public ActivityPureVO getHomeActivity(@PathVariable String name){
        Optional<Activity> activity = activityService.getByName(name);
        if (!activity.isPresent()){
            throw new NotFoundException(40001);
        }
        ActivityPureVO vo = new ActivityPureVO(activity.get());
        return vo;
    }

    @GetMapping("/name/{name}/with_coupon")
    public ActivityCouponVO getActivityWithCoupon(@PathVariable String name){
        Optional<Activity> activity = activityService.getByName(name);
        if(!activity.isPresent()){
            throw new NotFoundException(40001);
        }
        ActivityCouponVO vo = new ActivityCouponVO(activity.get());
        return vo;
    }
}
