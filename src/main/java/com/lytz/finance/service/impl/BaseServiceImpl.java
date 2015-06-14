package com.lytz.finance.service.impl;

import java.io.Serializable;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lytz.finance.dao.BaseDAO;
import com.lytz.finance.service.BaseService;
import com.lytz.finance.service.exception.DataMissingException;
import com.lytz.finance.service.exception.ExistsException;


/**
 * This class serves as the Base class for all other Managers - namely to hold
 * common CRUD methods that they might all use. You should only need to extend
 * this class when your require custom CRUD logic.
 * <p/>
 * <p>To register this class in your Spring context file, use the following XML.
 * 
 *
 * @param <T>  a type variable
 * @param <ID> the primary key for that type
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 *  Updated by jgarcia: added full text search + reindexing
 */
public abstract class BaseServiceImpl<T, ID extends Serializable> implements BaseService<T, ID> {

    //private static final Logger LOG = LoggerFactory.getLogger(BaseServiceImpl.class);

    /**
     * GenericDao instance, set by constructor of child classes
     */
    protected BaseDAO<T, ID> dao;


    public BaseServiceImpl() {
    }

    /**
     * {@inheritDoc}
     */
    
	public List<T> findAll() {
        return dao.findAll();
    }

    /**
     * {@inheritDoc}
     */
    
	public T findById(ID id) {
        return dao.findById(id);
    }

    /**
     * {@inheritDoc}
     */
    
	public boolean exists(ID id) {
        return dao.exists(id);
    }

    /**
     * {@inheritDoc}
     * @throws ExistsException 
     * @throws DataMissingException 
     */
    
	public T save(T object) { // throws ExistsException, DataMissingException {
    	
    	//T o = null;
    	
    	/*
		 * The implementation depends on when the transaction happens.
		 * 
		 * If save transaction is controlled by spring transactional in service layer,
		 * 
		 * the catch statement doesn't work due to the transaction happens at the end of
		 * 
		 * this method, spring data integrity exception or invalid exception throws in spring
		 * 
		 * junit test. If save transaction is controlled in dao layer (call flush function inside
		 * 
		 * save function), entirytexists/invalidstate can be sucessfully catched and exists/datamissing
		 * 
		 * exception can be caught in junit test.
		 * 
		 * Follow spring transaction control rules here to make sure signal center transaction
		 * 
		 * control logic
		 *  
		 */
    	
    	
    	return dao.save(object);
    	
        /*try{
        	o = dao.save(object);
        	
        } catch (EntityExistsException e) {
        	throw new ExistsException(e);
        } catch (InvalidStateException e){
        	throw new DataMissingException(e);
        } 
        
        return o;*/
    }

    /**
     * {@inheritDoc}
     */
    
	public void remove(T object) {
        	dao.remove(object);       
    }

    /**
     * {@inheritDoc}
     */
    
	public void remove(ID id) {
        	dao.remove(id);
    }
	
	public long getTotalCount() {
		return dao.getTotalCount();
	}
}
