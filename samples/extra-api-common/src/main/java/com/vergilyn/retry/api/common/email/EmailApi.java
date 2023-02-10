package com.vergilyn.retry.api.common.email;

import com.vergilyn.retry.api.common.dto.SendMsgDTO;

public interface EmailApi {

    /**
     * 发送邮件，HTML 格式
     *
     * @param sendMsgDTO
     * @param logPrefix
     *
     * @return
     *
     * @throws Exception
     * @see <a href="https://github.com/bbottema/simple-java-mail">SDK, simple-java-mail</a>
     */
    EmailApiResponse sendHTMLEmailSync(SendMsgDTO<EmailSendMsgDTO> sendMsgDTO) throws Exception;
}
