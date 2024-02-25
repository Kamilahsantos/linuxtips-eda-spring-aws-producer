package com.linuxtips.edaworkshop.mscheckout.producer;


import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.services.eventbridge.AmazonEventBridge;
import com.amazonaws.services.eventbridge.AmazonEventBridgeClient;
import com.amazonaws.services.eventbridge.model.PutEventsRequest;
import com.amazonaws.services.eventbridge.model.PutEventsRequestEntry;
import com.amazonaws.services.eventbridge.model.PutEventsResult;
import com.linuxtips.edaworkshop.mscheckout.model.Payment;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class EventBridgeProducer {


    @Bean
    private AmazonEventBridge amazonEventBridge(){
      return AmazonEventBridgeClient.builder()
              .withRegion("sa-east-1")
              .withCredentials(new DefaultAWSCredentialsProviderChain())
              .build();
    }

    public void finishOrder(Payment payment){
        PutEventsRequestEntry eventsRequestEntry = new PutEventsRequestEntry();
        eventsRequestEntry.withSource(payment.origem)
                .withDetail("{\"valor\": \"" +payment.valor +"\"}")
                .withDetailType(payment.status)
                .withEventBusName("status-pedido-bus");

        final PutEventsRequest request = new PutEventsRequest();
        request.withEntries(eventsRequestEntry);

        final PutEventsResult result =
                amazonEventBridge().putEvents(request);

        System.out.println(result);


    }
}
