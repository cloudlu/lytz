/**
 * 
 */
package com.lytz.finance.web.equity;

import java.util.Date;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.lytz.finance.common.Pager;
import com.lytz.finance.common.EquityQuery;
import com.lytz.finance.service.EquityService;
import com.lytz.finance.vo.Equity;

/**
 * @author cloudlu
 *
 */
@Controller
@SessionAttributes({"equityQuery","equityPager"})
public class EquityController {
    private static final Logger LOG = LoggerFactory.getLogger(EquityController.class);

    @Value("${pager.size}")
    private int pageSize;
    
    private EquityService equityService = null;

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class,
                new CustomDateEditor(LYTZUtils.getSafeDateFormat(), true));
    }
    
    @Autowired
    public void setEquityService(EquityService equityService) {
        this.equityService = equityService;
    }
    
    @ModelAttribute(value="equitySearchQuery")
    public EquityQuery createSearchQuery(){
        EquityQuery query = new EquityQuery();
        query.setQuerySize(pageSize);
        return query;
    }
    
    @ModelAttribute(value="equityQuery")
    public EquityQuery createQuery(){
        EquityQuery query = new EquityQuery();
        query.setQuerySize(pageSize);
        return query;
    }
    
    @ModelAttribute(value="equityPager")
    public Pager createPager(){
        Pager pager = new Pager();
        return pager;
    }
    
    
    @RequestMapping(value="/equity", method = RequestMethod.GET)
    public String search(@ModelAttribute(value="equitySearchQuery") EquityQuery query, Model model) throws Exception {
        if(LOG.isTraceEnabled()){
            LOG.trace("entering 'search' method...with query: " + query);
        }
        if(query.getQuerySize() == null){
            query.setQuerySize(pageSize);
        }
        Pager pager = new Pager(equityService.getTotalCount(query), query.getQuerySize());
        query.setStartRow(pager.getStartRow());
        model.addAttribute(Constants.EQUITY_LIST, equityService.findByQuery(query));
        model.addAttribute("equityPager", pager);
        EquityQuery searchQuery = new EquityQuery(query);
        model.addAttribute("equityQuery", searchQuery);
        if(LOG.isTraceEnabled()){
            LOG.trace("finish 'search' method...with query: " + query + " pager: " + pager);
        }
        return "service/equity/equityList";
    }

    @RequestMapping(value="/equity/list", method = RequestMethod.GET)
    public String list(@ModelAttribute(value="equityQuery") EquityQuery query, @ModelAttribute(value="equityPager") Pager pager, @RequestParam(value="pageNum", required=false) int pageNum, Model model) throws Exception {
        if(LOG.isTraceEnabled()){
            LOG.trace("entering 'list' method...with query: " + query + " pager: " + pager);
        }
        if(null == pager || null == query || !pager.isConfigured()){
            return "forward:/equity";
        }
        pager.setCurrentPage(pageNum);
        query.setStartRow(pager.getStartRow());
        model.addAttribute(Constants.EQUITY_LIST, equityService.findByQuery(query));
        //model.addAttribute("equityPager", pager);
        if(LOG.isTraceEnabled()){
            LOG.trace("finish 'list' method...with query: " + query + " pager: " + pager);
        }
        return "service/equity/equityList";
    }
   
    @RequestMapping(value = "/equity/view/{id}", method = RequestMethod.GET)
    public String viewDetail(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("equity", equityService.findById(id));
        return "service/equity/equityDetail";
    }
    
    @RequestMapping(value = "/admin/equity/new", method = RequestMethod.GET)
    public String newForm(Model model) {
        return "service/admin/equity/adminEquityForm";
    }
    
    @RequestMapping(value = "/admin/equity/update/{id}", method = RequestMethod.GET)
    public String updateForm(@PathVariable("id") Integer id, Model model) {
        if(id != null){
            model.addAttribute("equity", equityService.findById(id));
        }
        return "service/admin/equity/adminEquityForm";
    }
      
    @RequestMapping(value = "admin/equity/save", method = RequestMethod.POST)
    public String update(@Valid @ModelAttribute("equity") Equity equity, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        //equity.setStatus(EquityStatus.COMPLETED);
        if(LOG.isTraceEnabled()){
            LOG.trace(equity.toString());
        }
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("message", "输入信息不正确");
            redirectAttributes.addFlashAttribute(BindingResult.MODEL_KEY_PREFIX + "equity", bindingResult);
            if(null != equity.getId()){
                return "redirect:/equity/view/"+equity.getId();
            } else {
                return "redirect:/admin/equity/new";
            }
        }
        equityService.save(equity);
        redirectAttributes.addFlashAttribute("message", "更新信息" + equity.getTitle() + "成功");
        return "redirect:/equity";
    }

    @RequestMapping(value = "admin/equity/delete/{id}")
    public String delete(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
        Equity equity = equityService.findById(id);
        equityService.remove(id);
        //TODO remove uploaded img
        redirectAttributes.addFlashAttribute("message", "删除信息" + equity.getTitle() + "成功");
        return "redirect:/equity";
    }
}
