package com.vergilyn.retry.api.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 *
 * @author vergilyn
 * @since 2023-02-09
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SendMsgDTO<T> implements Serializable {

    /**
     * 任务ID
     */
    private Integer taskId;

    /**
     * 根据appId获取对应公众号accessToken
     */
    private String appId;

    /**
     * 平台标志: 10啄木鸟, 20言而有信, 30一步维修, 40川南环保
     */
    private Integer plat;

    /**
     * apkey
     */
    private String appkey;

    /**
     * sercet
     */
    private String sercet;

    /**
     * 2022-04-15
     * @see com.zmn.mcs.common.enums.AccountPlatEnum
     */
    private String accountPlat;

    /**
     * token
     */
    private String token;

    /**
     * 10系统类 20营销类
     */
    private Integer logType;

    /**
     * 操作记录ID
     */
    private String recordId;

    /**
     * 流水号：调用方传入，用于问题跟踪等场景使用（第三方也使用统一的流水号）
     */
    private String serialNumber;

    /**
     * 是否变量短信
     */
    private Boolean varSms;

    private T data;

}
