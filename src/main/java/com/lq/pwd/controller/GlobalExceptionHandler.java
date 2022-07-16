package com.lq.pwd.controller;

import com.baomidou.mybatisplus.extension.api.IErrorCode;
import com.baomidou.mybatisplus.extension.api.R;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public R handlerException(Throwable e){
        String msg = e.getMessage()==null?"9999-交易失败": e.getMessage();
        long code = 9999;
        if (msg.matches("\\d{4}-.*")){
            code = Long.parseLong(msg.split("-")[0]);
           // msg = msg.split("-")[1];
        }
        final long errCode = code;
        final String errMsg = msg;
        return R.restResult("交易失败", new IErrorCode() {
            @Override
            public long getCode() {
                return errCode;
            }

            @Override
            public String getMsg() {
                return msg;
            }
        });
//        return R.failed(e.getMessage()==null?"9999-交易失败": e.getMessage());
    }

}
