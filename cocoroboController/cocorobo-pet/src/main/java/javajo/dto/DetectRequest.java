package javajo.dto;

public class DetectRequest{
    private RequestBody requestBody;
    private HttpHeaders httpHeaders;

    DetectRequest(RequestBody requestBody, HttpHeaders httpHeaders){
        this.requestBody = requestBody;
        this.httpHeaders = httpHeaders;
    }



}