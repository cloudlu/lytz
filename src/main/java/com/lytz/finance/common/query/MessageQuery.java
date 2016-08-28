/**
 * 
 */
package com.lytz.finance.common.query;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import com.google.common.base.MoreObjects;
import com.lytz.finance.vo.MessageStatus;
import com.lytz.finance.vo.Status;

/**
 * @author cloud
 *
 */
@Getter
@Setter
@ToString
@RequiredArgsConstructor  
@AllArgsConstructor
public class MessageQuery extends Query {

    private MessageStatus status;

}
