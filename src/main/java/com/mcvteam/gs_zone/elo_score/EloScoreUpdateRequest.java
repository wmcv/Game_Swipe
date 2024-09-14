package com.mcvteam.gs_zone.elo_score;

public class EloScoreUpdateRequest {
    private Integer winnerId;
    private Integer loserId;

    // Constructors
    public EloScoreUpdateRequest() {}

    public EloScoreUpdateRequest(Integer winnerId, Integer loserId) {
        this.winnerId = winnerId;
        this.loserId = loserId;
    }

    // Getters and Setters
    public Integer getWinnerId() {
        return winnerId;
    }

    public void setWinnerId(Integer winnerId) {
        this.winnerId = winnerId;
    }

    public Integer getLoserId() {
        return loserId;
    }

    public void setLoserId(Integer loserId) {
        this.loserId = loserId;
    }


}
