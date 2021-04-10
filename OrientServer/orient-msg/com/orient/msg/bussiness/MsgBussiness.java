package com.orient.msg.bussiness;

import com.orient.config.SystemMngConfig;
import com.orient.msg.bean.CommonConsumerMsg;
import com.orient.msg.bean.MailMsg;
import com.orient.sysmodel.domain.mq.CwmMsg;
import com.orient.sysmodel.service.mq.impl.MsgService;
import com.orient.utils.FileOperator;
import com.orient.utils.StringUtil;
import com.orient.web.base.BaseBusiness;
import com.orient.utils.exception.OrientBaseAjaxException;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.ArrayList;
import java.util.List;

/**
 * ${DESCRIPTION}
 *
 * @author Administrator
 * @create 2016-12-15 14:59
 */
@Service
@Transactional
public class MsgBussiness extends BaseBusiness {
    @Autowired
    private MsgService msgService;

    @Autowired
    JavaMailSender mailSender;

    public void saveCommonMsg(CommonConsumerMsg consumerMsg) {
        CwmMsg msg = new CwmMsg();
        msg.setTitle(consumerMsg.getTitle());
        msg.setContent(consumerMsg.getContent());
        msg.setData(consumerMsg.getData());
        msg.setTimestamp(consumerMsg.getTimestamp());
        msg.setUserId(consumerMsg.getUserId());
        msg.setType(consumerMsg.getType());
        msg.setSrc(consumerMsg.getSrc());
        msg.setDest(consumerMsg.getDest());
        msg.setReaded(consumerMsg.getReaded());
        msgService.save(msg);
    }

    public List<CwmMsg> getMsgByUserId(Long userId, Boolean readed) {
        List<CwmMsg> msgs = msgService.list(Restrictions.eq("userId", userId), Restrictions.eq("readed", readed));
        if (msgs == null) {
            msgs = new ArrayList<>();
        }
        return msgs;
    }

    public int getMsgCntByUserId(Long userId, Boolean readed) {
        return msgService.getMsgCntByUserId(userId, readed);
    }

    public void markReaded(List<Long> idList, Boolean readed) {
        List<CwmMsg> msgs = msgService.list(Restrictions.in("id", idList));
        for (CwmMsg msg : msgs) {
            msg.setReaded(readed);
            msgService.save(msg);
        }
    }

    public void deleteMsgs(List<Long> idList) {
        for (Long id : idList) {
            msgService.delete(id);
        }
    }

    public Boolean sendEmail(MailMsg mailMsg) {
        if (null == mailMsg || StringUtil.isEmpty(mailMsg.getTo())) {
            throw new OrientBaseAjaxException("100101", "请填写邮件接收人");
        }
        if (StringUtil.isEmpty(mailMsg.getFilePath())) {
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage.setFrom(SystemMngConfig.SYSTEM_MAIL_SENDER);
            simpleMailMessage.setTo(mailMsg.getTo());
            simpleMailMessage.setSubject(mailMsg.getSubject());
            simpleMailMessage.setText(mailMsg.getText());
            simpleMailMessage.setCc(mailMsg.getCopys());
            mailSender.send(simpleMailMessage);
        } else {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            try {
                MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
                mimeMessageHelper.setFrom(SystemMngConfig.SYSTEM_MAIL_SENDER);
                mimeMessageHelper.setTo(mailMsg.getTo());
                mimeMessageHelper.setSubject(mailMsg.getSubject());
                mimeMessageHelper.setText(mailMsg.getText());
                mimeMessageHelper.setCc(mailMsg.getCopys());
                FileSystemResource fileSystemResource = new FileSystemResource(mailMsg.getFilePath());
                mimeMessageHelper.addAttachment(FileOperator.getFileName(mailMsg.getFilePath()), fileSystemResource);
                mailSender.send(mimeMessage);
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        }
        return true;
    }
}
