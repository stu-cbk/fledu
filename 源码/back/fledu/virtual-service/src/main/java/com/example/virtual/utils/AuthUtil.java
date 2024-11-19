package com.example.virtual.utils;


import lombok.Data;

import java.security.SignatureException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

@Data
public class AuthUtil {
    private final String APISecret="xxx";
    private final String APIKey="xxx";
    private String host;
    private String ts;
    private String originSign;
    private String signature;
    private String authorization;

    public AuthUtil(){}
    public AuthUtil(String host,String requestLine) throws SignatureException
    {
        this.host = host;
        this.ts = this.generateTs();
        this.originSign = this.generateOriginSignature(requestLine);
        this.signature = this.generateSignature();
        this.authorization = this.generateAuthorization();
    }

    public String generateTs() {
        SimpleDateFormat sdf = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.US);
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
        return sdf.format(new Date());
    }

    public String generateOriginSignature(String requestLine)
    {
        return "host: " + this.host + "\ndate: " + this.ts + "\nGET " + requestLine + " HTTP/1.1";
    }

    public String generateSignature() throws SignatureException
    {
        return CryptUtil.hmacEncrypt("HmacSHA256", this.originSign, this.APISecret);
    }

    public String generateAuthorization()
    {
        String line = String.format("api_key=\"%s\", algorithm=\"hmac-sha256\", headers=\"host date request-line\", " +
                "signature=\"%s\"",this.APIKey, this.signature);
        return CryptUtil.base64Encode(line);
    }
}

