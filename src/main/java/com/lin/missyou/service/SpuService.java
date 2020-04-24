package com.lin.missyou.service;

import com.lin.missyou.model.Spu;
import com.lin.missyou.repository.SpuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author ${吴延昭}
 * @create 2020/4/16
 */
@Service
public class SpuService {
    @Autowired
    private SpuRepository spuRepository;

    public Spu getSpu(Long id){
        return this.spuRepository.findOneById(id);
    }

    public Page<Spu> getLatestPagingSpu(Integer page, Integer count){
        Pageable pageable = PageRequest.of(page, count, Sort.by("createTime").descending());
        return this.spuRepository.findAll(pageable);
    }

    public Page<Spu> getByCategoryId(Long cid, Boolean isRoot, Integer pageNum, Integer count){
        Pageable pageable = PageRequest.of(pageNum, count);
        if(isRoot){
            return this.spuRepository.findByRootCategoryIdOrderByCreateTimeDesc(cid, pageable);
        }
        return this.spuRepository.findByCategoryIdOrderByCreateTimeDesc(cid, pageable);
    }
}
