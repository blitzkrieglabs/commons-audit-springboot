package com.blitzkrieglabs.commons.audit.aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.blitzkrieglabs.commons.audit.domains.Audit;
import com.blitzkrieglabs.commons.audit.domains.Stateful;
import com.blitzkrieglabs.commons.audit.repositories.AuditableRepository;
import com.blitzkrieglabs.commons.audit.repositories.HistoricalRepository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Table;

@Aspect
@Component
@Order(2)
public class HistoricalAspect {

    @Autowired
    private HistoricalRepository<Stateful, Long> historicalRepo;
    
    @Qualifier("historicalEntityManager")
    private final EntityManager historyEntityManager;

    
    public HistoricalAspect(@Qualifier("historicalEntityManager") EntityManager historyEntityManager) {
        this.historyEntityManager = historyEntityManager;
    }
    

    @Around("execution(* org.springframework.data.jpa.repository.JpaRepository+.save(..))")
    public Object captureStatefulEntities(ProceedingJoinPoint joinPoint) throws Throwable {
    	Object result = executeAndSave(joinPoint);
    	
        return result;
    }
    
    
    
    @Around("execution(* org.springframework.data.jpa.repository.JpaRepository+.delete(..))")
    public Object captureDeletedEntities(ProceedingJoinPoint joinPoint) throws Throwable {
    	Object result = executeAndSave(joinPoint);
    	
        return result;
    }
    
    
    private Object executeAndSave(ProceedingJoinPoint joinPoint) throws Throwable {
    	 Object[] args = joinPoint.getArgs();
    	 Object entity = args[0];
    	 
         // Proceed with the original save call
         Object result = null;

         if (entity instanceof Stateful) {
             Stateful statefulEntity = (Stateful) entity;
             statefulEntity.setReferenceId(RequestContext.get("reference_id").toString());
             args[0] = statefulEntity;
             
             
             result = joinPoint.proceed(args);
             if(!HistoricalRepository.class.isAssignableFrom(joinPoint.getTarget().getClass())) {
            	save(statefulEntity);
                
             }
             Table annot = entity.getClass().getAnnotation(Table.class);
             if(annot!=null) {
            	 //TODO: what if one service will execute multiple entity save? by design, this should not happen.
            	 RequestContext.put("entity_name", annot.name());
            	 RequestContext.put("entity_id", statefulEntity.getPublicId()!=null?statefulEntity.getPublicId().toString():"");
             }	 
             
         }else {
        	result= joinPoint.proceed();
         }
         
         return result;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    private <T extends Stateful> void save(T statefulEntity){
        try{
        	@SuppressWarnings("unchecked")
			T historyEntity = (T) statefulEntity.getClass().getDeclaredConstructor().newInstance();
        	
        	BeanUtils.copyProperties(statefulEntity, historyEntity);
        	//historyEntityManager.detach(statefulEntity);
        	historyEntity.setId(null);
            historicalRepo.save(historyEntity);
        }catch(Exception e){
            System.out.println("IGNORE: ");
        }
    }
}
