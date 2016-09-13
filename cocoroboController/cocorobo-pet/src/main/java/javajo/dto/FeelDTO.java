package javajo.dto;

/**
 * 感情取得用DTO.
 */
public class FeelDTO {

    // 実行結果
    private boolean result;

    // 感情(目玉の種類)
    private String feel;

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public String getFeel() {
        return feel;
    }

    public void setFeel(String feel) {
        this.feel = feel;
    }
}
