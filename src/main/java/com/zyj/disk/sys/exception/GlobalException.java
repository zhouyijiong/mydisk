package com.zyj.disk.sys.exception;

import lombok.Getter;

/** 业务异常 */
@Getter
public final class GlobalException extends RuntimeException{
    private final int code;
    private final String msg;
    private static final long serialVersionUID = 631924228114738472L;

    public GlobalException(Throwable throwable){
        GlobalException temp = (GlobalException)throwable;
        this.code = temp.code;
        this.msg = temp.msg;
    }

    public GlobalException(Client client){
        this.code = client.code;
        this.msg = client.msg;
    }

    public GlobalException(Server server){
        this.code = server.code;
        this.msg  = server.msg;
    }

    public GlobalException(User use, String...infos){
        this.code = use.code;
        this.msg  = use.msg + String.join("; ",infos);
    }
}