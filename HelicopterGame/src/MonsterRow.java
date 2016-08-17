import java.util.LinkedList;

public class MonsterRow {

	private LinkedList<Monster> monsterList;
	private int topY;
	private int lowerY;
	private int topX;
	private boolean allDead;
	
	public MonsterRow() {
		monsterList = new LinkedList<>();

	}

	public void updateKillStatus(){
	
		boolean tempAllDead=true;
		for(Monster monster:monsterList){
			if(!monster.isKilled())
				tempAllDead=false;
		}
		
	allDead=tempAllDead;
		
	}
	
	public boolean areAllDead(){
		return allDead;
	}
	
	public LinkedList<Monster> getList() {
		return monsterList;

	}

	public void getNewMonsters(int topX, int upperBound, int lowerBound) {
		this.topY = upperBound;
		this.lowerY = lowerBound;
		this.topX = topX;
		initializeRow();
	}

	private void initializeRow() {
		int availableSpots = (lowerY - topY) / 50;
		allDead=false;
		monsterList.clear();

		if(availableSpots==6)
			availableSpots--;
		
		for (int i = 1; i <= availableSpots - 1; i++) {
			monsterList.add(new Monster(topX, topY + i * 50));
		}

	}

}
