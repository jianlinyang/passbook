package com.shu.passbook.service.impl;

import com.shu.passbook.constant.Constants;
import com.shu.passbook.dao.MerchantsDao;
import com.shu.passbook.entity.Merchants;
import com.shu.passbook.mapper.PassTemplateRowMapper;
import com.shu.passbook.service.IInventoryService;
import com.shu.passbook.service.IUserPassService;
import com.shu.passbook.utils.RowKeyGenUtil;
import com.shu.passbook.vo.*;
import com.spring4all.spring.boot.starter.hbase.api.HbaseTemplate;
import lombok.extern.slf4j.Slf4j;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.filter.CompareFilter;
import org.apache.hadoop.hbase.filter.FilterList;
import org.apache.hadoop.hbase.filter.LongComparator;
import org.apache.hadoop.hbase.filter.SingleColumnValueFilter;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * <h1>获取库存信息,只返回用户没有领取的</h1>
 *
 * @author yang
 * @date 2019/6/11 21:38
 */
@Slf4j
@Service
public class InventoryServiceImpl implements IInventoryService {

    private final HbaseTemplate hbaseTemplate;

    private final MerchantsDao merchantsDao;

    private IUserPassService userPassService;

    @Autowired
    public InventoryServiceImpl(HbaseTemplate hbaseTemplate, MerchantsDao merchantsDao, IUserPassService userPassService) {
        this.hbaseTemplate = hbaseTemplate;
        this.merchantsDao = merchantsDao;
        this.userPassService = userPassService;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Response getInventoryInfo(Long userId) throws Exception {
        Response allUserPass = userPassService.getUserAllPassInfo(userId);
        List<PassInfo> passInfos = (List<PassInfo>) allUserPass.getData();
        List<PassTemplate> excludeObject = passInfos.stream().map(
                PassInfo::getPassTemplate
        ).collect(Collectors.toList());
        List<String> excludeIds = new ArrayList<>();
        excludeObject.forEach(e -> excludeIds.add(
                RowKeyGenUtil.genPassTemplateRowKey(e)
        ));
        return new Response(new InventoryResponse(userId, buildPassTemplateInfo(getAvailablePassTemplate(excludeIds))));
    }

    /**
     * <h2>获取系统中可用优惠券</h2>
     *
     * @param excludeIds 需要排除的优惠券
     * @return {@link PassTemplate}
     */
    private List<PassTemplate> getAvailablePassTemplate(List<String> excludeIds) {
        FilterList filterList = new FilterList(FilterList.Operator.MUST_PASS_ONE);
        filterList.addFilter(
                new SingleColumnValueFilter(
                        Bytes.toBytes(Constants.PassTemplateTable.FAMILY_C),
                        Bytes.toBytes(Constants.PassTemplateTable.LIMIT),
                        CompareFilter.CompareOp.GREATER,
                        new LongComparator(0L)
                ));
        filterList.addFilter(
                new SingleColumnValueFilter(
                        Bytes.toBytes(Constants.PassTemplateTable.FAMILY_C),
                        Bytes.toBytes(Constants.PassTemplateTable.LIMIT),
                        CompareFilter.CompareOp.EQUAL,
                        Bytes.toBytes("-1")
                ));
        Scan scan = new Scan();
        scan.setFilter(filterList);

        List<PassTemplate> validaTemplates = hbaseTemplate.find(
                Constants.PassTemplateTable.TABLE_NAME, scan, new PassTemplateRowMapper());
        List<PassTemplate> availablePassTemplates = new ArrayList<>();
        Date cur = new Date();
        for (PassTemplate validaTemplate : validaTemplates) {
            if (excludeIds.contains(RowKeyGenUtil.genPassTemplateRowKey(validaTemplate))) {
                continue;
            }
            if (cur.getTime() >= validaTemplate.getStart().getTime() &&
                    cur.getTime() <= validaTemplate.getEnd().getTime()) {
                availablePassTemplates.add(validaTemplate);
            }
        }
        return availablePassTemplates;
    }

    /**
     * <h2>构造优惠券的信息</h2>
     *
     * @param passTemplates {@link PassTemplate}
     * @return {@link PassTemplateInfo}
     */
    private List<PassTemplateInfo> buildPassTemplateInfo(List<PassTemplate> passTemplates) {
        Map<Integer, Merchants> merchantsMap = new HashMap<>();
        List<Integer> merchantsIds = passTemplates.stream().map(
                PassTemplate::getId
        ).collect(Collectors.toList());
        List<PassTemplateInfo> result = new ArrayList<>(passTemplates.size());

        List<Merchants> merchants = merchantsDao.findAll(merchantsIds);
        merchants.forEach(m -> merchantsMap.put(m.getId(), m));
        for (PassTemplate passTemplate : passTemplates) {
            Merchants mc = merchantsMap.getOrDefault(passTemplate.getId(), null);
            if (mc == null) {
                log.error("Merchants Error :{}", passTemplate.getId());
                continue;
            }
            result.add(new PassTemplateInfo(passTemplate, mc));
        }
        return result;
    }
}
