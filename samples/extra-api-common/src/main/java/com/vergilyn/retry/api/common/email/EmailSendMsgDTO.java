package com.vergilyn.retry.api.common.email;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmailSendMsgDTO implements Serializable {

    /**
     * 发件人地址
     */
    private String fromAddress;

    /**
     * 发件人昵称
     */
    private String fromAlias;

    /**
     * 收件人地址
     *
     * @deprecated 2022-10-08，由于该字段不支持“多个收件人”，所以弃用此字段。请用 {@link #toAddresses} 代替
     */
    private String toAddress;

    /**
     * 收件人地址，必须是2的倍数。
     * <p> 格式为：["收件人名称", "收件人地址"...]，例如 ["张三", "zhangsan@zmn.cn", "李四", "lisi@zmn.cn"...]
     * <p> 如果不知道收件人名称，也可以将 收件人名称&收件人地址 写成一样，例如 ["zhangsan@zmn.cn", "zhangsan@zmn.cn", "lisi@zmn.cn", "lisi@zmn.cn"...]
     */
    private List<String> toAddresses;

    /**
     * 抄送人地址 多个用英文逗号隔开
     *
     * @deprecated 2022-10-08，弃用该格式。请用 {@link #ccAddresses} 代替。
     */
    private String ccAddress;

    /**
     * 抄送人地址，必须是2的倍数。
     * <p> 格式为：["收件人名称", "收件人地址"...]，例如 ["张三", "zhangsan@zmn.cn", "李四", "lisi@zmn.cn"...]
     * <p> 如果不知道收件人名称，也可以将 收件人名称&收件人地址 写成一样，例如 ["zhangsan@zmn.cn", "zhangsan@zmn.cn", "lisi@zmn.cn", "lisi@zmn.cn"...]
     */
    private List<String> ccAddresses;

    /**
     * 邮件标题
     */
    private String subject;

    /**
     * 邮件内容
     */
    private String content;



}
