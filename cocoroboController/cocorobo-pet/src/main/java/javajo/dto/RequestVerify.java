package javajo.dto;


import org.springframework.web.multipart.MultipartFile;

public class RequestVerify {
    private String faceId1;
    private MultipartFile photo;

    public void setFaceId1(String faceId1) {
        this.faceId1 = faceId1;
    }

    public void setPhoto(MultipartFile photo) {
        this.photo = photo;
    }

}
