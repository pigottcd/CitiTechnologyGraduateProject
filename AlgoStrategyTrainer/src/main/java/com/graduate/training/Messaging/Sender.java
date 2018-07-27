package com.graduate.training.Messaging;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.jms.core.JmsTemplate;
@Service


public class Sender {
    private JmsTemplate template;
}
