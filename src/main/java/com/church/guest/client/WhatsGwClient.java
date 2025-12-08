package com.church.guest.client;


import com.church.guest.client.request.WhatsGwRequest;
import com.church.guest.client.response.WhatsGwResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient( name = "WhatsGwClient", url = "${notification.whatsGw.base-url}" )
public interface WhatsGwClient {

    @PostMapping( "/Send" )
    WhatsGwResponse send( @RequestBody WhatsGwRequest whatsGwRequest );
}
