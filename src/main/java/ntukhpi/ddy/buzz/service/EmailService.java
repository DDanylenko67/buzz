package ntukhpi.ddy.buzz.service;

import ntukhpi.ddy.buzz.entity.EmailDetails;

public interface EmailService {

    String sendSimpleMail(EmailDetails details);
    String sendMailWithAttachment(EmailDetails details);
}