package com.dgm.springjms;

import com.dgm.springjms.senders.MessageSender;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SpringjmsApplicationTests {

    @Autowired
    MessageSender messageSender;

    @Test
    void testSendAndReceive() {
        messageSender.send("Hello Spring JMS!!!");
    }
}
