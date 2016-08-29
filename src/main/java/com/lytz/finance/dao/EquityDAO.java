/**
 * 
 */
package com.lytz.finance.dao;

import java.util.List;

import com.lytz.finance.common.query.EquityQuery;
import com.lytz.finance.vo.Equity;

/**
 * @author cloudlu
 *
 */
public interface EquityDAO extends BaseDAO<Equity, Integer> {

    List<Equity> findByQuery(EquityQuery query);
    
    int getTotalCount(EquityQuery query);
}
