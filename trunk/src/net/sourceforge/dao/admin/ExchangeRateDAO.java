/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package net.sourceforge.dao.admin;

import java.util.List;
import java.util.Map;

import net.sourceforge.model.admin.Currency;
import net.sourceforge.model.admin.ExchangeRate;
import net.sourceforge.model.admin.Site;
import net.sourceforge.model.admin.query.ExchangeRateQueryOrder;

/**
 * �������ExchangeRate�Ľӿ�
 * @author ych
 * @version 1.0 (Nov 13, 2005)
 */
public interface ExchangeRateDAO {

    /**
     * �����ݿ�ȡ��ָ��id��ExchangeRate
     * 
     * @param id
     *            ExchangeRate��id
     * @return ����ָ����ExchangeRate
     */
    public ExchangeRate getExchangeRate(Integer id);

    /**
     * �����ݿ�ȡ��ָ��currency��site��ExchangeRate
     * 
     * @param currency
     *            ExchangeRate��currency
     * @param site
     *            ExchangeRate��site
     * 
     * @return ����ָ����ExchangeRate
     */
    public ExchangeRate getExchangeRate(Currency currency,Site site);

    /**
     * ���ط��ϲ�ѯ������ExchangeRate�������
     * 
     * @param conditions
     *            ������ѯ����������ֵӳ���Map�����в�ѯ����Ӧ������ExchangeRateQueryCondition���Ԥ���峣��
     * @return ���ϲ�ѯ������Currency�������
     */
    public int getExchangeRateListCount(Map conditions);

    /**
     * ���ط��ϲ�ѯ������ExchangeRate�����б�
     * 
     * @param conditions
     *            ������ѯ����������ֵӳ���Map�����в�ѯ����Ӧ������ExchangeRateQueryCondition���Ԥ���峣��
     * @param pageNo
     *            �ڼ�ҳ����pageSizeΪҳ�Ĵ�С��pageSizeΪ-1ʱ���Ըò���
     * @param pageSize
     *            ҳ�Ĵ�С��-1��ʾ����ҳ
     * @param order
     *            ����������null��ʾ������
     * @param descend
     *            false��ʾ����true��ʾ����
     * @return ���ϲ�ѯ������ExchangeRate�����б�
     */
    public List getExchangeRateList(Map conditions, int pageNo, int pageSize, ExchangeRateQueryOrder order, boolean descend);

    /**
     * ����ָ����ExchangeRate�������ݿ�
     * 
     * @param exchangeRate
     *            Ҫ�����ExchangeRate����
     * @return ������ExchangeRate����
     */
    //public ExchangeRate insertExchangeRate(ExchangeRate exchangeRate);

    /**
     * ����ָ����ExchangeRate�������ݿ�
     * 
     * @param exchangeRate
     *            Ҫ���µ�ExchangeRate����
     * @return ���º��ExchangeRate����
     */
    //public ExchangeRate updateExchangeRate(ExchangeRate exchangeRate);

    /**
     * ����ָ����ExchangeRate�������ݿ�
     * 
     * @param exchangeRate
     *            Ҫ���µ�ExchangeRate����
     * @return ���º��ExchangeRate����
     */
    public ExchangeRate saveExchangeRate(ExchangeRate exchangeRate);
}