/**
 * 
 */
package com.lytz.finance.web.topic;

import java.util.Date;

import javax.validation.Valid;

import lombok.extern.log4j.Log4j2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.lytz.finance.common.Constants;
import com.lytz.finance.common.LYTZUtils;
import com.lytz.finance.common.query.Pager;
import com.lytz.finance.common.query.TopicQuery;
import com.lytz.finance.service.TopicService;
import com.lytz.finance.vo.Comment;
import com.lytz.finance.vo.Topic;

/**
 * @author cloudlu
 *
 */
@Log4j2
@Controller
@SessionAttributes({"topicQuery","topicPager"})
public class TopicController {

    @Value("${pager.size}")
    private int pageSize;
    
    private TopicService topicService = null;

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class,
                new CustomDateEditor(LYTZUtils.getSafeDateFormat(), true));
    }
    
    @Autowired
    public void setTopicService(TopicService topicService) {
        this.topicService = topicService;
    }
    
    @ModelAttribute(value="topicSearchQuery")
    public TopicQuery createSearchQuery(){
        TopicQuery query = new TopicQuery();
        query.setQuerySize(pageSize);
        return query;
    }
    
    @ModelAttribute(value="topicQuery")
    public TopicQuery createQuery(){
        TopicQuery query = new TopicQuery();
        query.setQuerySize(pageSize);
        return query;
    }
    
    @ModelAttribute(value="topicPager")
    public Pager createPager(){
        Pager pager = new Pager();
        return pager;
    }
    
    
    @RequestMapping(value="/topic", method = RequestMethod.GET)
    public String search(@ModelAttribute(value="topicSearchQuery") TopicQuery query, Model model) throws Exception {
        if(LOG.isTraceEnabled()){
            LOG.trace("entering 'search' method...with query: " + query);
        }
        if(query.getQuerySize() == null){
            query.setQuerySize(pageSize);
        }
        Pager pager = new Pager(topicService.getTotalCount(query), query.getQuerySize());
        query.setStartRow(pager.getStartRow());
        model.addAttribute(Constants.TOPIC_LIST, topicService.findByQuery(query));
        model.addAttribute("topicPager", pager);
        TopicQuery searchQuery = new TopicQuery(query);
        model.addAttribute("topicQuery", searchQuery);
        model.addAttribute("topicStatus", LYTZUtils.TOPIC_STATUS_MAP);
        if(LOG.isTraceEnabled()){
            LOG.trace("finish 'search' method...with query: " + query + " pager: " + pager);
        }
        return "service/topic/topicList";
    }

    @RequestMapping(value="/topic/list", method = RequestMethod.GET)
    public String list(@ModelAttribute(value="topicQuery") TopicQuery query, @ModelAttribute(value="topicPager") Pager pager, @RequestParam(value="pageNum", required=false) int pageNum, Model model) throws Exception {
        if(LOG.isTraceEnabled()){
            LOG.trace("entering 'list' method...with query: " + query + " pager: " + pager);
        }
        if(null == pager || null == query || !pager.isConfigured()){
            return "forward:/topic";
        }
        pager.setCurrentPage(pageNum);
        query.setStartRow(pager.getStartRow());
        model.addAttribute(Constants.TOPIC_LIST, topicService.findByQuery(query));
        model.addAttribute("topicStatus", LYTZUtils.TOPIC_STATUS_MAP);
        //model.addAttribute("topicPager", pager);
        if(LOG.isTraceEnabled()){
            LOG.trace("finish 'list' method...with query: " + query + " pager: " + pager);
        }
        return "service/topic/topicList";
    }

    @RequestMapping(value = "/topic/view/{id}", method = RequestMethod.GET)
    public String viewDetail(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("topic", topicService.findById(id));
        return "service/topic/topicDetail";
    }
    
    @RequestMapping(value = "/topic/new", method = RequestMethod.GET)
    public String newForm(Model model) {
        return "service/topic/topicForm";
    }
    
    @RequestMapping(value = "/topic/update/{id}", method = RequestMethod.GET)
    public String updateForm(@PathVariable("id") Integer id, Model model) {
        if(id != null){
            model.addAttribute("topic", topicService.findById(id));
        }
        return "service/topic/topicForm";
    }
      
    @RequestMapping(value = "/topic/save", method = RequestMethod.POST)
    public String update(@Valid @ModelAttribute("topic") Topic topic, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        //topic.setStatus(TopicStatus.COMPLETED);
        if(LOG.isTraceEnabled()){
            LOG.trace(topic.toString());
        }
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("message", "输入信息不正确");
            redirectAttributes.addFlashAttribute(BindingResult.MODEL_KEY_PREFIX + "topic", bindingResult);
            if(null != topic.getId()){
                return "redirect:/topic/view/"+topic.getId();
            } else {
                return "redirect:/topic/new";
            }
        }
        topicService.save(topic);
        redirectAttributes.addFlashAttribute("message", "更新信息" + topic.getTitle() + "成功");
        return "redirect:/topic";
    }
    
    @RequestMapping(value = "/topic/addComment", method = RequestMethod.POST)
    public String addComment(@Valid @ModelAttribute("comment") Comment comment, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        //topic.setStatus(TopicStatus.COMPLETED);
        if(LOG.isTraceEnabled()){
            LOG.trace(comment.toString());
        }
        if (null == comment.getTopic().getId() || bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("message", "输入信息不正确");
            redirectAttributes.addFlashAttribute(BindingResult.MODEL_KEY_PREFIX + "comment", bindingResult);
            return "redirect:/topic/view/"+comment.getTopic().getId();
        }
        topicService.addComment(comment.getTopic().getId(), comment);
        redirectAttributes.addFlashAttribute("message", "更新信息成功");
        return "redirect:/topic/view/"+comment.getTopic().getId();
    }

    @RequestMapping(value = "/topic/delete/{id}")
    public String delete(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
        Topic topic = topicService.findById(id);
        topicService.remove(id);
        //TODO remove uploaded img
        redirectAttributes.addFlashAttribute("message", "删除信息" + topic.getTitle() + "成功");
        return "redirect:/topic";
    }
}
