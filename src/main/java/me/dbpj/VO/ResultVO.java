package me.dbpj.VO;

import lombok.Data;

/**
 * @Author: Jeremy
 * @Date: 2018/11/7 15:20
 */
@Data
public class ResultVO<T> {
    /**
     * 错误码.
     */
    private Integer code;

    /**
     * 提示信息.
     */
    private String msg;

    private String time;
    /**
     * 具体内容.
     */
    private T data;
}
