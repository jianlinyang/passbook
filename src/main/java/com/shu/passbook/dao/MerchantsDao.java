package com.shu.passbook.dao;

import com.shu.passbook.entity.Merchants;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * <h1>Merchants Dao 接口</h1>
 *
 * @author yang
 * @date 2019/6/11 14:15
 */
public interface MerchantsDao extends JpaRepository<Merchants, Integer> {
    /**
     * <h2>通过id获取对象</h2>
     *
     * @param id 商户id
     * @return {@link Merchants}
     */
    Merchants findById(Integer id);

    /**
     * <h2>根据商户名称获取对象</h2>
     *
     * @param name 商户名称
     * @return {@link Merchants}
     */
    Merchants findByName(String name);

    /**
     * <h2>通过id获取对象</h2>
     *
     * @param ids 商户id
     * @return {@link Merchants}
     */
    Merchants findById(List<Integer> ids);
}
