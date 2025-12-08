package com.church.guest.client.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class WhatsGwResponse {

    private String result;
    private Integer message_id;
    private String contact_phone_number;
    private String phone_state;

}
