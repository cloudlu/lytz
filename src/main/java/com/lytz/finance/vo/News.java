/**
 * 
 */
package com.lytz.finance.vo;

import java.io.Serializable;

/**
 * @author cloudlu
 *
 */
public class News implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1177363368722700375L;

    private String time;
    
    private String title;
    
    private String link;
    
    private String content;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
