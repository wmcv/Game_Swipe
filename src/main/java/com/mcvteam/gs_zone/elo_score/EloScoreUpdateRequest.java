package com.mcvteam.gs_zone.elo_score;

public class EloScoreUpdateRequest {
    private int winnerId;
    private int loserId;

    // Constructors
    public EloScoreUpdateRequest() {}

    public EloScoreUpdateRequest(int winnerId, int loserId) {
        this.winnerId = winnerId;
        this.loserId = loserId;
    }

    // Getters and Setters
    public int getWinnerId() {
        return winnerId;
    }

    public void setWinnerId(int winnerId) {
        this.winnerId = winnerId;
    }

    public int getLoserId() {
        return loserId;
    }

    public void setLoserId(int loserId) {
        this.loserId = loserId;
    }
}
