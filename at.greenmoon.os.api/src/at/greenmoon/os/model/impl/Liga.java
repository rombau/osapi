package at.greenmoon.os.model.impl;

import java.util.List;

public class Liga {

	private String Land;
	private Integer ebene;
	private String gruppe;
	private List<Team> teams;
	
	public String getLand() {
		return Land;
	}
	
	public void setLand(String land) {
		Land = land;
	}
	
	public Integer getEbene() {
		return ebene;
	}
	
	public void setEbene(Integer ebene) {
		this.ebene = ebene;
	}
	
	public String getGruppe() {
		return gruppe;
	}
	
	public void setGruppe(String gruppe) {
		this.gruppe = gruppe;
	}
	
	public List<Team> getTeams() {
		return teams;
	}
	
	public void setTeams(List<Team> teams) {
		this.teams = teams;
	}
}
