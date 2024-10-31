package com.blitzkrieglabs.commons.audit.aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.blitzkrieglabs.commons.audit.annotations.Auditable;
import com.blitzkrieglabs.commons.audit.domains.Audit;
import com.blitzkrieglabs.commons.audit.repositories.AuditableRepository;

import jakarta.servlet.http.HttpServletRequest;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Objects;
import java.util.UUID;

@Aspect
@Component
@Order(1)
public class AuditableAspect {
	
	@Autowired
	private AuditableRepository auditableRepo;
	

	@Around(
            "@annotation(auditable)")
    public Object logAnnotatedMethods(ProceedingJoinPoint joinPoint, Auditable auditable) throws Throwable {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = Objects.requireNonNull(attributes).getRequest();
        
        
        //use reference_id as identifier/key to map an audit record to the entity history, this will ensure that the API call is always binded to the entity change
        UUID referenceId = UUID.randomUUID();
        RequestContext.put("reference_id", referenceId);

        // Get the request path
        String path = request.getMethod() + " " + getURI(joinPoint);
        
        
        
        // Proceed with the method execution
        Object result = joinPoint.proceed();

        // Get the HTTP status
        String status = getStatus(result);

        // Log the path and status
        //TODO: evaluate whether we need to log request parameters as well.

        Object entity = RequestContext.get("entity_name");
        Object entityId = RequestContext.get("entity_name");
        Audit audit = Audit.builder()
        					.referenceId(referenceId.toString())
        					.resource(path)
        					.status(status)
        					.entity(entity!=null?entity.toString():"")
        					.entityId(entityId!=null?entityId.toString():"")
        					.sessionId(RequestContext.getSession())
        					.initiator(RequestContext.getInitiator())
        					.build();
        System.out.println("path: "+ path +", result: " + status );
        
        					
        auditableRepo.save(audit);
        
        return result;
    }

    private String getStatus(Object result) {
        if (result instanceof ResponseEntity) {
        	ResponseEntity<?> res = ((ResponseEntity<?>) result);
            return res.getStatusCode().toString();
        } else if (result instanceof org.springframework.http.HttpStatus) {
        	org.springframework.http.HttpStatus stat = ((org.springframework.http.HttpStatus) result);
            return stat.name()+" " + stat.value();
        } else {
            return "404";
        }
    }
    
    private String getURI(ProceedingJoinPoint joinPoint) throws NoSuchMethodException, SecurityException, IllegalAccessException, InvocationTargetException {
    	 MethodSignature signature = (MethodSignature) joinPoint.getSignature();
         Method method = signature.getMethod();
         Class<? extends Annotation> clazz = getRestAnnotation(method);
         if(clazz!=null) {
        	 Annotation annotation = method.getAnnotation(clazz);
        	 Method valueMethod = annotation.annotationType().getDeclaredMethod("value");
             Object value = valueMethod.invoke(annotation);
             if(value instanceof String[])
            	 return  ((String[])value)[0];
         }
         
         return null;
          
    }
    
    @SuppressWarnings("unchecked")
	private Class<? extends Annotation> getRestAnnotation(Method method) {
        // Define an array of REST controller annotations
        Class<? extends Annotation>[] restAnnotations = new Class[]{
                RequestMapping.class,
                GetMapping.class,
                PostMapping.class,
                PutMapping.class,
                DeleteMapping.class,
                PatchMapping.class
        };

        // Check if the method is annotated with any of the REST annotations
        for (Class<? extends Annotation> annotationClass : restAnnotations) {
            if (method.isAnnotationPresent(annotationClass)) {
                return annotationClass;
            }
        }
        return null;
    }
    
    
}
