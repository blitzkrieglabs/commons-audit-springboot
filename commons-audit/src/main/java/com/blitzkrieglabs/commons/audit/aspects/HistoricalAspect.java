package com.blitzkrieglabs.commons.audit.aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.blitzkrieglabs.commons.audit.domains.Audit;
import com.blitzkrieglabs.commons.audit.domains.Stateful;
import com.blitzkrieglabs.commons.audit.repositories.AuditableRepository;
import com.blitzkrieglabs.commons.audit.repositories.HistoricalRepository;

import jakarta.persistence.Table;

@Aspect
@Component
@Order(2)
public class HistoricalAspect {

    @Autowired
    private HistoricalRepository<Stateful, Long> historicalRepo;
    
    

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
             
             
             joinPoint.proceed(args);
             if(!HistoricalRepository.class.isAssignableFrom(joinPoint.getTarget().getClass())) {
            	 statefulEntity.setId(null);
                historicalRepo.save(statefulEntity);
             }
             Table annot = entity.getClass().getAnnotation(Table.class);
             if(annot!=null) {
            	 //TODO: what if one service will execute multiple entity save? by design, this should not happen.
            	 RequestContext.put("entity_name", annot.name());
            	 RequestContext.put("entity_id", statefulEntity.getPublicId()!=null?statefulEntity.getPublicId().toString():"");
             }	 
             
         }else {
        	 joinPoint.proceed();
         }
         
         return result;
    }
}
