/**
 * 
 */
package com.lytz.finance.vo;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author cloudlu
 *
 */
@Getter
@Setter
@ToString
@RequiredArgsConstructor  
@AllArgsConstructor
public class News implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1177363368722700375L;

    @NonNull private String time;
    
    @NonNull private String title;
    
    private String link;
    
    @NonNull private String content;
}
