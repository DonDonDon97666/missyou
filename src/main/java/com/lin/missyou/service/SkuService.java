package com.lin.missyou.service;

import com.lin.missyou.model.Sku;
import com.lin.missyou.repository.SkuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author ${吴延昭}
 * @create 2020/4/29
 */
@Service
public class SkuService {

    @Autowired
    private SkuRepository skuRepository;

    public Optional<List<Sku>> getSkuListByIdsOrderByIdAsc(List<Long> ids) {
        return skuRepository.findAllByIdInOrderByIdAsc(ids);
    }
}
