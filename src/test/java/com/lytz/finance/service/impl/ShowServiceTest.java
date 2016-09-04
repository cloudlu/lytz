/**
 * 
 */
package com.lytz.finance.service.impl;

import static org.junit.Assert.assertEquals;
import static org.powermock.api.mockito.PowerMockito.when;

import java.util.Collections;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.rule.PowerMockRule;

import com.lytz.finance.common.LYTZUtils;
import com.lytz.finance.dao.ShowDAO;
import com.lytz.finance.service.FileService;
import com.lytz.finance.service.ShowService;
import com.lytz.finance.vo.Show;

/**
 * @author cloudlu
 *
 */
@PrepareForTest({ShowService.class, LYTZUtils.class})
public class ShowServiceTest {

    private static final int SHOW_SIZE = 3;

    @Rule
    public PowerMockRule rule = new PowerMockRule();

    @Mock
    private ShowDAO dao;
    
    @Mock
    private FileService fileService;
    
    @InjectMocks
    private ShowServiceImpl service;
    
    private Show save = new Show();
    
    private Show update = new Show();
    
    @Before
    public void setup() throws Exception{
        MockitoAnnotations.initMocks(this);
        when(dao.getTotalCount()).thenReturn(SHOW_SIZE);
        update.setId(1);
        when(dao.findById(update.getId())).thenReturn(update);
        PowerMockito.mockStatic(LYTZUtils.class);
        when(LYTZUtils.class,"getFilePathFromContent", save.getContent()).thenReturn(Collections.emptyList());
        when(LYTZUtils.class,"getFilePathFromContent", update.getContent()).thenReturn(Collections.emptyList());
    }
    
    @After
    public void cleanup(){
        
    }
    
    @Test public void testRead(){
        assertEquals(SHOW_SIZE,service.getTotalCount());
    }
    
    @Test public void testSave(){
        service.save(save);
        Mockito.verify(dao).save(save);
        service.save(update);
        Mockito.verify(dao).findById(update.getId());
        Mockito.verify(dao).save(update);
    }
}
