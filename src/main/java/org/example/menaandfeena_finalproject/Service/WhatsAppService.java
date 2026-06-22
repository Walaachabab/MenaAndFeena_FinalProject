package org.example.menaandfeena_finalproject.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;

@Service
@RequiredArgsConstructor
public class WhatsAppService {
    @Value("${ultramsg.instance.id}")
    private String instanceId;

    @Value("${ultramsg.token}")
    private String token;

    private final RestTemplate restTemplate = new RestTemplate();

    public void sendWhatsAppMessage(String to, String message) {
        String url = "https://api.ultramsg.com/" + instanceId + "/messages/chat";

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("token", token);
        body.add("to", to);
        body.add("body", message);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);

        // نرسل الرسالة مرة واحدة فقط لتجنّب تكرار الإرسال (كان هناك استدعاء مكرر سابقاً).
        ResponseEntity<String> response =
                restTemplate.postForEntity(url, request, String.class);

        System.out.println("WHATSAPP RESPONSE = " + response.getBody());

    }

    // يرسل ملف PDF (أو أي مستند) عبر واتساب باستخدام endpoint المستندات في UltraMsg.
    // الـ document يقبل رابطاً عاماً أو محتوى base64 بصيغة data URI مثل: data:application/pdf;base64,XXXX
    public void sendWhatsAppDocument(String to, String filename, String document, String caption) {
        String url = "https://api.ultramsg.com/" + instanceId + "/messages/document";

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("token", token);
        body.add("to", to);
        body.add("filename", filename);
        body.add("document", document);
        body.add("caption", caption);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);

        System.out.println("WHATSAPP DOCUMENT RESPONSE = " + response.getBody());
    }
}
