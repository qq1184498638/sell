package com.springcloud.sell.order.server.enums;

import lombok.Getter;

public class Const {
    @Getter
    public enum  OrderStatusEnum {
        NEW(0, "新订单"),
        FINISHED(1, "完结"),
        CANCEL(2, "取消"),
        ;
        private Integer code;

        private String message;

        OrderStatusEnum(Integer code, String message) {
            this.code = code;
            this.message = message;
        }
    }

    @Getter
    public enum PayStatusEnum {
        WAIT(0, "等待支付"),
        SUCCESS(1, "支付成功"),
        ;
        private Integer code;

        private String message;

        PayStatusEnum(Integer code, String message) {
            this.code = code;
            this.message = message;
        }
    }

    /**
     *  请求响应的状态码
     */
    public enum ResponseCode {

        SUCCESS(0,"SUCCESS"),
        ERROR(1,"ERROR"),
        NEED_LOGIN(10,"NEED_LOGIN"),
        ILLEGAL_ARGUMENT(2,"ILLEGAL_ARGUMENT");

        private final int code;
        private final String desc;


        ResponseCode(int code,String desc){
            this.code = code;
            this.desc = desc;
        }

        public int getCode(){
            return code;
        }
        public String getDesc(){
            return desc;
        }

    }

    @Getter
    public enum ResultEnum {
        PARAM_ERROR(1, "参数错误"),
        CART_EMPTY(2, "购物车为空")

        ;

        private Integer code;

        private String message;

        ResultEnum(Integer code, String message) {
            this.code = code;
            this.message = message;
        }
    }
}
