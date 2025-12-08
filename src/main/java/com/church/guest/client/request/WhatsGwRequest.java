package com.church.guest.client.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class WhatsGwRequest {

    private String apikey;
    private String phone_number;
    private String contact_phone_number;
    private String message_custom_id;
    private String message_type;
    private String message_body;
    private String check_status;
}
