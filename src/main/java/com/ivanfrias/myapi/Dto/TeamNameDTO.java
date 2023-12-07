package com.ivanfrias.myapi.Dto;

public class TeamNameDTO {
    private String teamName;

    public TeamNameDTO(String teamName) {
        this.teamName = teamName;
    }

    public TeamNameDTO() {
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }
}
