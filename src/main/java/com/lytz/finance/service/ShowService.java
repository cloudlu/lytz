/**
 * 
 */
package com.lytz.finance.service;

import java.util.List;

import com.lytz.finance.common.ShowQuery;
import com.lytz.finance.vo.Show;

/**
 * @author cloudlu
 *
 */
public interface ShowService extends BaseService<Show, Integer> {

    int getTotalCount(ShowQuery query);

    List<Show> findByQuery(ShowQuery query);

}
