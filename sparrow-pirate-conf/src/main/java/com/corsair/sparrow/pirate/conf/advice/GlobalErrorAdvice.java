package com.corsair.sparrow.pirate.conf.advice;

import com.corsair.sparrow.pirate.core.global.RespEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.multipart.MultipartException;

import java.sql.SQLException;

/**
 * @author jack
 * 全局异常捕获，持续更新
 */
@Slf4j
@RestControllerAdvice
public class GlobalErrorAdvice {

    /**
     * 文件请求出错advice
     * @param e
     * @return
     */
    @ExceptionHandler(MultipartException.class)
    @ConditionalOnMissingBean
    public ResponseEntity<RespEntity> multipartExceptionAdvice(MultipartException e){
        log.error("multipartExceptionHandler:{}",e);
        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(RespEntity.internalServerError(e.getLocalizedMessage()));
    }

    /**
     * 请求方式不允许advice
     * @param e
     * @return
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ConditionalOnMissingBean
    public ResponseEntity<RespEntity> httpRequestMethodNotSupportedExceptionAdvice(HttpRequestMethodNotSupportedException e){
        log.error("httpRequestMethodNotSupportedException:{}",e);
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(RespEntity.methodNotAllowd(e.getMethod()));
    }

    /**
     * sql错误advice
     * @param throwable
     * @return
     */
    @ExceptionHandler({SQLException.class})
    @ConditionalOnMissingBean
    public ResponseEntity<RespEntity> sqlExceptionAdvice(Throwable throwable){
        log.error("SQLException:{}",throwable);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(RespEntity.internalServerError(throwable.getLocalizedMessage()));
    }

    /**
     * 服务器抛出异常错误
     * @param throwable
     * @return
     */
    @ExceptionHandler(Throwable.class)
    @ConditionalOnMissingBean
    public ResponseEntity<RespEntity> throwableAdvice(Throwable throwable){
        log.error("Throwable:{}",throwable);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(RespEntity.internalServerError(throwable.getLocalizedMessage()));
    }
}
