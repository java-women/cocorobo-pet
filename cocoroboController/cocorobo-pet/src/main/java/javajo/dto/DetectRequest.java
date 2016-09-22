package javajo.dto;

import org.springframework.http.HttpHeaders;

public class DetectRequest{
    private  RequestBody requestBody;
    private HttpHeaders httpHeaders;

    public DetectRequest(RequestBody requestBody, HttpHeaders httpHeaders){
        this.requestBody = requestBody;
        this.httpHeaders = httpHeaders;
    }



}