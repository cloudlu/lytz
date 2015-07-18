/**
 * 
 */
package com.lytz.finance.dao.impl;

import org.springframework.stereotype.Repository;

import com.lytz.finance.dao.CommentDAO;
import com.lytz.finance.vo.Comment;

/**
 * @author cloud
 *
 */
@Repository("commentDAO")
public class CommentDAOImpl extends BaseDAOImpl<Comment, Integer> implements
        CommentDAO {

}
