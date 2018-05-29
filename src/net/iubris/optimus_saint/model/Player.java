package net.iubris.optimus_saint.model;

public class Player {

	private String name;
	private final Level level;
	private final PremiumRank premiumRank;

	public Player() {
		this.level = new Level();
		this.premiumRank = new PremiumRank();
	}
	
	public Level getLevel() {
		return level;
	}
	
	public PremiumRank getPremiumRank() {
		return premiumRank;
	}
	
	public String getName() {
		return name;
	}
	
	
	public static class Level extends QuantitableLevel {}
	public static class PremiumRank extends QuantitableLevel {}
	
	
	public static abstract class QuantitableLevel {
		private int quantity;
		private int neededToIncreaseQuantity;
		private int actualToIncreaseQuantity;
		
		public void setActualSubQuantity(int actualNewSubQuantity) {
			this.actualToIncreaseQuantity = actualNewSubQuantity;
		}
		
		public void increase(int actualToIncreaseNewQuantity, int neededToIncreaseNewQuantity) {
			quantity+=1;
			resetOnIncrease(actualToIncreaseNewQuantity, neededToIncreaseNewQuantity);
		}
		
		public void resetOnIncrease(int actualToIncreaseQuantity, int neededToIncreaseQuantity) {
			this.actualToIncreaseQuantity = actualToIncreaseQuantity;
			this.neededToIncreaseQuantity = neededToIncreaseQuantity;
		}
		
		public int getQuantity() {
			return quantity;
		}
		public int getNeededToIncreaseQuantity() {
			return neededToIncreaseQuantity;
		}
		public int getActualToIncreaseQuantity() {
			return actualToIncreaseQuantity;
		}
	}
}
