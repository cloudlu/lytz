/**
 * 
 */
package com.lytz.finance.web.show;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.lytz.finance.common.Constants;
import com.lytz.finance.common.Pager;
import com.lytz.finance.common.ShowQuery;
import com.lytz.finance.service.ShowService;
import com.lytz.finance.vo.Show;

/**
 * @author cloudlu
 *
 */
@Controller
@SessionAttributes({"showQuery","showPager"})
public class ShowController {
    private static final Logger LOG = LoggerFactory.getLogger(ShowController.class);

    @Value("${pager.size}")
    private int pageSize;
    
    private ShowService showService = null;

    @Autowired
    public void setShowService(ShowService showService) {
        this.showService = showService;
    }
    
    @ModelAttribute(value="showSearchQuery")
    public ShowQuery createQuery(){
        ShowQuery query = new ShowQuery();
        query.setQuerySize(pageSize);
        return query;
    }
    
    @RequestMapping(value="/show", method = RequestMethod.GET)
    public String search(@ModelAttribute(value="showSearchQuery") ShowQuery query, Model model) throws Exception {
        if(LOG.isTraceEnabled()){
            LOG.trace("entering 'search' method...with query: " + query);
        }
        Pager pager = new Pager(showService.getTotalCount(query), query.getQuerySize());
        query.setStartRow(pager.getStartRow());
        model.addAttribute(Constants.USER_LIST, showService.findByQuery(query));
        model.addAttribute("showPager", pager);
        ShowQuery searchQuery = new ShowQuery(query);
        model.addAttribute("showQuery", searchQuery);
        if(LOG.isTraceEnabled()){
            LOG.trace("finish 'search' method...with query: " + query + " pager: " + pager);
        }
        return "service/show/showList";
    }

    @RequestMapping(value="/show/list", method = RequestMethod.GET)
    public String list(@ModelAttribute(value="showQuery") ShowQuery query, @ModelAttribute(value="showPager") Pager pager, @RequestParam(value="pageNum", required=false) int pageNum, Model model) throws Exception {
        if(LOG.isTraceEnabled()){
            LOG.trace("entering 'list' method...with query: " + query + " pager: " + pager);
        }
        pager.setCurrentPage(pageNum);
        query.setStartRow(pager.getStartRow());
        model.addAttribute(Constants.SHOW_LIST, showService.findByQuery(query));
        model.addAttribute("showPager", pager);
        if(LOG.isTraceEnabled()){
            LOG.trace("finish 'list' method...with query: " + query + " pager: " + pager);
        }
        return "service/show/showList";
    }
    
    @RequestMapping(value = "admin/show/view", method = RequestMethod.GET)
    public String updateForm(@RequestParam(value="id", required=false) Integer id, Model model) {
        if(id != null){
            model.addAttribute("show", showService.findById(id));
        }
        return "service/admin/show/adminShowForm";
    }
      
    @RequestMapping(value = "admin/show/save", method = RequestMethod.POST)
    public String update(@Valid @ModelAttribute("show") Show show, RedirectAttributes redirectAttributes) {
        showService.save(show);
        redirectAttributes.addFlashAttribute("message", "更新演唱会信息" + show.getTitle() + "成功");
        return "redirect:/show";
    }

    @RequestMapping(value = "admin/show/delete/{id}")
    public String delete(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
        Show show = showService.findById(id);
        showService.remove(id);
        //TODO remove uploaded img
        redirectAttributes.addFlashAttribute("message", "删除演唱会信息" + show.getTitle() + "成功");
        return "redirect:/show";
    }
}
