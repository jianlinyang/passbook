package com.shu.passbook.service.impl;

import com.alibaba.fastjson.JSON;
import com.shu.passbook.constant.Constants;
import com.shu.passbook.constant.PassStatus;
import com.shu.passbook.dao.MerchantsDao;
import com.shu.passbook.entity.Merchants;
import com.shu.passbook.mapper.PassRowMapper;
import com.shu.passbook.service.IUserPassService;
import com.shu.passbook.vo.Pass;
import com.shu.passbook.vo.PassInfo;
import com.shu.passbook.vo.PassTemplate;
import com.shu.passbook.vo.Response;
import com.spring4all.spring.boot.starter.hbase.api.HbaseTemplate;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.filter.*;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * <h1>用户优惠券功能相关实现</h1>
 *
 * @author yang
 * @date 2019/6/11 20:26
 */
@Service
@Slf4j
public class UserPassServiceImpl implements IUserPassService {
    private HbaseTemplate hbaseTemplate;
    private final MerchantsDao merchantsDao;

    @Autowired
    public UserPassServiceImpl(HbaseTemplate hbaseTemplate, MerchantsDao merchantsDao) {
        this.hbaseTemplate = hbaseTemplate;
        this.merchantsDao = merchantsDao;
    }


    @Override
    public Response getUserPassInfo(Long userId) throws Exception {
        return getPassInfoByStatus(userId, PassStatus.UNUSED);
    }

    @Override
    public Response getUserUsedPassInfo(Long userId) throws Exception {
        return getPassInfoByStatus(userId, PassStatus.USED);
    }

    @Override
    public Response getUserAllPassInfo(Long userId) throws Exception {
        return getPassInfoByStatus(userId, PassStatus.ALL);
    }

    @Override
    public Response userUsePassInfo(Pass pass) throws Exception {
        byte[] rowPrefix = Bytes.toBytes(new StringBuilder(String.valueOf(pass.getUserId())).reverse().toString());
        Scan scan = new Scan();
        List<Filter> filters = new ArrayList<>();
        filters.add(new PrefixFilter(rowPrefix));
        filters.add(new SingleColumnValueFilter(
                Constants.PassTable.FAMILY_I.getBytes(),
                Constants.PassTable.TEMPLATE_ID.getBytes(),
                CompareFilter.CompareOp.EQUAL,
                Bytes.toBytes(pass.getTemplateId())
        ));
        filters.add(new SingleColumnValueFilter(
                Constants.PassTable.FAMILY_I.getBytes(),
                Constants.PassTable.CON_DATE.getBytes(),
                CompareFilter.CompareOp.EQUAL,
                Bytes.toBytes("-1")
        ));
        scan.setFilter(new FilterList(filters));
        List<Pass> passes = hbaseTemplate.find(Constants.PassTable.TABLE_NAME, scan, new PassRowMapper());
        if (null == passes || passes.size() != 1) {
            log.error("UserUsePass Error :{}", JSON.toJSONString(pass));
            return Response.failure("UserUsePass Error");
        }
        byte[] FAMILY_I = Constants.PassTable.FAMILY_I.getBytes();
        byte[] CON_DATE = Constants.PassTable.CON_DATE.getBytes();

        List<Mutation> datas = new ArrayList<>();
        Put put = new Put(passes.get(0).getRowKey().getBytes());
        put.addColumn(FAMILY_I, CON_DATE, Bytes.toBytes(DateFormatUtils.ISO_DATE_FORMAT.format(new Date())));
        datas.add(put);
        hbaseTemplate.saveOrUpdates(Constants.PassTable.TABLE_NAME, datas);
        return Response.success();
    }

    /**
     * <h2>根据优惠券状态获取优惠券信息</h2>
     *
     * @param userId 用户id
     * @param status {@link PassStatus}
     * @return {@link Response}
     * @throws Exception e
     */
    private Response getPassInfoByStatus(Long userId, PassStatus status) throws Exception {
        //根据userId构造行键前缀
        byte[] rowPrefix = Bytes.toBytes(new StringBuilder(String.valueOf(userId)).reverse().toString());
        CompareFilter.CompareOp compareOp =
                status == PassStatus.UNUSED ?
                        CompareFilter.CompareOp.EQUAL : CompareFilter.CompareOp.NOT_EQUAL;
        Scan scan = new Scan();
        List<Filter> filters = new ArrayList<>();
        //行键前缀过滤器
        filters.add(new PrefixFilter(rowPrefix));
        //基于列单元的过滤器
        if (status != PassStatus.ALL) {
            scan.setFilter(new SingleColumnValueFilter(Constants.PassTable.FAMILY_I.getBytes(),
                    Constants.PassTable.CON_DATE.getBytes(), compareOp, Bytes.toBytes("-1")));
        }
        scan.setFilter(new FilterList(filters));
        List<Pass> passes = hbaseTemplate.find(Constants.PassTable.TABLE_NAME, scan, new PassRowMapper());
        Map<String, PassTemplate> passTemplateMap = buildPassTemplate(passes);
        Map<Integer, Merchants> merchantsMap = buildMerchantsMap(new ArrayList<>(passTemplateMap.values()));
        ArrayList<PassInfo> result = new ArrayList<>();
        for (Pass pass : passes) {
            PassTemplate passTemplate = passTemplateMap.getOrDefault(pass.getTemplateId(), null);
            if (null == passTemplate) {
                log.error("PassTemplate Null :{}", pass.getTemplateId());
                continue;
            }
            Merchants merchants = merchantsMap.getOrDefault(passTemplate.getId(), null);
            if (null == merchants) {
                log.error("Merchants Null :{}", passTemplate.getId());
                continue;
            }
            result.add(new PassInfo(pass, passTemplate, merchants));
        }
        return new Response(result);
    }

    /**
     * <h2>通过获取的pass对象构造map</h2>
     *
     * @param passes {@link Pass}
     * @return {@link PassTemplate}
     * @throws Exception e
     */
    private Map<String, PassTemplate> buildPassTemplate(List<Pass> passes) throws Exception {
        String[] patterns = new String[]{"yyyy-MM-dd"};
        byte[] FAMILY_B = Constants.PassTemplateTable.FAMILY_B.getBytes();
        byte[] ID = Constants.PassTemplateTable.ID.getBytes();
        byte[] TITLE = Constants.PassTemplateTable.TITLE.getBytes();
        byte[] SUMMARY = Constants.PassTemplateTable.SUMMARY.getBytes();
        byte[] DESC = Constants.PassTemplateTable.DESC.getBytes();
        byte[] HAS_TOKEN = Constants.PassTemplateTable.HAS_TOKEN.getBytes();
        byte[] BACKGROUND = Constants.PassTemplateTable.BACKGROUND.getBytes();

        byte[] FAMILY_C = Constants.PassTemplateTable.FAMILY_C.getBytes();
        byte[] LIMIT = Constants.PassTemplateTable.LIMIT.getBytes();
        byte[] START = Constants.PassTemplateTable.START.getBytes();
        byte[] END = Constants.PassTemplateTable.END.getBytes();

        List<String> templateIds = passes.stream().map(
                Pass::getTemplateId
        ).collect(Collectors.toList());

        List<Get> templateGets = new ArrayList<>(templateIds.size());
        templateIds.forEach(t -> templateGets.add(new Get(Bytes.toBytes(t))));


        Result[] results = hbaseTemplate.getConnection()
                .getTable(TableName.valueOf(Constants.PassTemplateTable.TABLE_NAME))
                .get(templateGets);
        //构造 PassTemplateId -> PassTemplate 的map
        Map<String, PassTemplate> passTemplate2Object = new HashMap<>();
        for (Result result : results) {
            PassTemplate passTemplate = new PassTemplate();
            passTemplate.setSummary(Bytes.toString(result.getValue(FAMILY_B, SUMMARY)));
            passTemplate.setDesc(Bytes.toString(result.getValue(FAMILY_B, DESC)));
            passTemplate.setHasToken(Bytes.toBoolean(result.getValue(FAMILY_B, HAS_TOKEN)));
            passTemplate.setId(Bytes.toInt(result.getValue(FAMILY_B, ID)));
            passTemplate.setTitle(Bytes.toString(result.getValue(FAMILY_B, TITLE)));
            passTemplate.setBackground(Bytes.toInt(result.getValue(FAMILY_B, BACKGROUND)));

            passTemplate.setLimit(Bytes.toLong(result.getValue(FAMILY_C, LIMIT)));
            passTemplate.setStart(DateUtils.parseDate(Bytes.toString(result.getValue(FAMILY_C, START)), patterns));
            passTemplate.setEnd(DateUtils.parseDate(Bytes.toString(result.getValue(FAMILY_C, END)), patterns));

            passTemplate2Object.put(Bytes.toString(result.getRow()), passTemplate);
        }
        return passTemplate2Object;
    }

    /**
     * <h2>通过PassTemplates 构造 Merchants</h2>
     *
     * @param passTemplates {@link PassTemplate}
     * @return {@link Merchants}
     */
    private Map<Integer, Merchants> buildMerchantsMap(List<PassTemplate> passTemplates) {
        Map<Integer, Merchants> merchantsMap = new HashMap<>();
        List<Integer> merchantsIds = passTemplates.stream().map(
                PassTemplate::getId
        ).collect(Collectors.toList());
        List<Merchants> merchants = merchantsDao.findAll(merchantsIds);
        merchants.forEach(m -> merchantsMap.put(m.getId(), m));
        return merchantsMap;
    }
}
