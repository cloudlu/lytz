/**
 * 
 */
package com.lytz.finance.service;

import java.util.List;

import com.lytz.finance.common.EquityQuery;
import com.lytz.finance.vo.Equity;

/**
 * @author cloudlu
 *
 */
public interface EquityService extends BaseService<Equity, Integer> {

    int getTotalCount(EquityQuery query);

    List<Equity> findByQuery(EquityQuery query);
}
