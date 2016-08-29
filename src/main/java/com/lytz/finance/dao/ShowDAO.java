package com.lytz.finance.dao;

import java.util.List;

import com.lytz.finance.common.query.ShowQuery;
import com.lytz.finance.vo.Show;

public interface ShowDAO extends BaseDAO<Show, Integer>{

    List<Show> findByQuery(ShowQuery query);
    
    int getTotalCount(ShowQuery query);
}
