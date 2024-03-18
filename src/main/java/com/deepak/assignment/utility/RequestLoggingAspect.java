package com.deepak.assignment.utility;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.databind.ObjectMapper;

//Problem 16.

@Aspect
@Component
public class RequestLoggingAspect {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Pointcut("within(@org.springframework.web.bind.annotation.RestController *)")
    public void restController() {
    }

    @AfterReturning(pointcut = "restController()", returning = "response")
    public void logRequest(JoinPoint joinPoint, Object response) {
        String methodName = joinPoint.getSignature().getName();
        String apiEndpoint = joinPoint.getTarget().getClass().getSimpleName() + "." + methodName;
        String requestJson = getRequestJson(joinPoint.getArgs());

        // String userId = getUserId(); // Extract user ID from the request or session
        String status = "success"; // You may need to determine the status based on the response
        String errorCode = ""; // Set error code if applicable
        String errorMessage = ""; // Set error message if applicable
        // String clientReqId = getClientRequestId(); // Extract client request ID from
        // the request or session

        logger.info("user=<userId>|api={}|status={}|error_code={}|error_message={}|client_reqid=<_reqid>|req={}",
                apiEndpoint, status, errorCode, errorMessage, requestJson);
    }

    private String getRequestJson(Object[] args) {
        try {
            return objectMapper.writeValueAsString(args);
        } catch (Exception e) {
            logger.error("Error converting request to JSON: {}", e.getMessage());
            return "";
        }
    }
}
