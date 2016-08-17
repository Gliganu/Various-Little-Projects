package model;

/**
 * 
 * Enum representing different typpes of pieces for the map
 *
 */
public enum PieceType {
	FreeBlock(0), FullBock(1), CrucialBlock(2), Player(3);
    private int value;
    
  
    private PieceType(int value) {
            this.value = value;
    }
    
    /**
     * Get the value associated with that certain piece
     * @return
     */
    public int getValue(){
    	return value;
    }
};  


