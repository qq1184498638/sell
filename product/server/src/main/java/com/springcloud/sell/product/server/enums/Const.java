package com.springcloud.sell.product.server.enums;

import lombok.Getter;

public class Const {
    /**
     * 商品状态
     */
    public enum ProductStatusEnum{
        ON_SALE(0, "在架"),
        DOWN_SALE(1, "下架");

        private String value;
        private int code;

        ProductStatusEnum(int code, String value) {
            this.value = value;
            this.code = code;
        }

        public String getValue() {
            return value;
        }

        public int getCode() {
            return code;
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
        PRODUCT_NOT_EXIST(1, "商品不存在"),
        PRODUCT_STOCK_ERROR(2, "库存有误"),
        ;

        private Integer code;

        private String message;

        ResultEnum(Integer code, String message) {
            this.code = code;
            this.message = message;
        }
    }
}
