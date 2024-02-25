package com.linuxtips.edaworkshop.mscheckout.controller;


import com.linuxtips.edaworkshop.mscheckout.model.Payment;
import com.linuxtips.edaworkshop.mscheckout.producer.EventBridgeProducer;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CheckoutController {

    private EventBridgeProducer eventBridgeProducer;

    public CheckoutController(EventBridgeProducer eventBridgeProducer) {
        this.eventBridgeProducer = eventBridgeProducer;
    }


    @PostMapping("/orders")
    public void finishOrder(@RequestBody Payment payment){
        eventBridgeProducer.finishOrder(payment);
    }
}
