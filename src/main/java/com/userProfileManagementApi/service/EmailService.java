package com.userProfileManagementApi.service;

import org.springframework.stereotype.Service;
import javax.mail.MessagingException;
import java.io.IOException;
import okhttp3.*;

@Service
public class EmailService {

    private static final String BASE_URL = "https://wpy6jq.api.infobip.com";
    private static final String API_KEY = "App 1dc3da94aa305643627de872e54ddb9a-b7caacdf-1d63-466e-acc5-27afd09df7f1";
    private static final String SENDER_EMAIL_ADDRESS = "Rugwiro@selfserviceib.com";
    private static final String EMAIL_SUBJECT = "Soft Drink Company - New Password";

    public EmailService() throws IOException {
    }

    	private static Request prepareHttpRequest(RequestBody body) {
		return new Request.Builder()
				.url(String.format("%s/email/2/send", BASE_URL))
				.method("POST", body)
				.addHeader("Authorization", API_KEY)
				.addHeader("Content-Type", "text/plain")
				.addHeader("Accept", "application/json")
				.build();
	}

    public void createEmail(String firstName, String password, String email) throws MessagingException, IOException {

        OkHttpClient client = new OkHttpClient().newBuilder().build();

        RequestBody body = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("from", SENDER_EMAIL_ADDRESS)
                .addFormDataPart("to", email)
                .addFormDataPart("subject", EMAIL_SUBJECT)
                .addFormDataPart("text", "Hello " + firstName + ", \n \n Your new account password is: " + password + "\n \n The Support Team").build();

        Request request = prepareHttpRequest(body);
        Response response = client.newCall(request).execute();

    }



}
