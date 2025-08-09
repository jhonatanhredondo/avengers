package model;

import enums.Status;

public class HeroJavaBeans {
	private int heroId;
	private String heroName;
	private Status heroStatus;
	private int powerId;
	private String powerName;

	public HeroJavaBeans() {
	}

	public HeroJavaBeans(int heroId, String heroName, String status, int powerId, String powerName) {
		super();
		this.heroId = heroId;
		this.heroName = heroName;
		this.heroStatus = Status.valueOf(status);
		this.powerId = powerId;
		this.powerName = powerName;
	}

	public int getHeroId() {
		return heroId;
	}

	public String getHeroName() {
		return heroName;
	}

	public String getPowerName() {
		return powerName;
	}

	public int getPowerId() {
		return powerId;
	}

	public Status getHeroStatus() {
		return heroStatus;
	}

	public void setHeroId(int heroId) {
		this.heroId = heroId;
	}

	public void setHeroName(String heroName) {
		this.heroName = heroName;
	}

	public void setHeroStatus(Status heroStatus) {
		this.heroStatus = heroStatus;
	}

	public void setPowerId(int powerId) {
		this.powerId = powerId;
	}

	public void setPowerName(String powerName) {
		this.powerName = powerName;
	}
}
