package com.mcvteam.gs_zone.elo_score;

public class EloScoreUpdateRequest {
    private Integer winnerId;
    private Integer loserId;
    private Elo_Score winnerElo;
    private Elo_Score loserElo;

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

    public Elo_Score getLoserElo() {
        return loserElo;
    }

    public void setLoserElo(Elo_Score loserElo) {
        this.loserElo = loserElo;
    }

    public Elo_Score getWinnerElo() {
        return winnerElo;
    }

    public void setWinnerElo(Elo_Score winnerElo) {
        this.winnerElo = winnerElo;
    }

}
