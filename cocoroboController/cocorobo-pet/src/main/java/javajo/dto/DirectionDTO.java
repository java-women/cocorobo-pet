package javajo.dto;

/**
 * 進行方向取得用DTO
 */
public class DirectionDTO {

    // apikey
    private String apiKey;

    //進行方向
    private String direction;

    public String getApiKey(){
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }
}