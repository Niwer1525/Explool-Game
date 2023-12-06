package game;

import io.ActionFenetre;
import io.Auditeur;
import io.Fenetre;
import io.Random;

/**
 * A small and funny game where you have to move a character to reach an objective and dodging walls.
 */
public class SixHumans implements Auditeur {

	// Consts
	private static final String IMAGE_FOLDER = "/img/";
	private static final int CELL_SIZE = 48;
	private static final int WALL = -1;
	private static final int EMPTY = 0;
	private static final int PLAYER = 1;
	private static final int OBJECTIVE = 7;
	
	// Vars
	private static Fenetre window;
	int[][] grid = new int[10][10];
	int[][] positions = new int[10][2];

	public static void main(String[] args) {
		/* Creating the window */
		window = new Fenetre("6 humans", 10 * CELL_SIZE, 10 * CELL_SIZE);
		SixHumans game = new SixHumans();

		/* Initialize the game */
		game.init();

		/* Display the frame itself */
		window.setAuditeur(game, 1000);
		window.afficher();
	}

	/**
	 * Initialize the game
	 */
	public void init() {
		/* Spawn objects */
		placeObject(OBJECTIVE);
		placeObject(WALL, 10);
		int[] playerCoords = placeObject(PLAYER);
		positions[PLAYER][0] = playerCoords[0];
		positions[PLAYER][1] = playerCoords[1];

		for(int i = 2; i < OBJECTIVE; i++) placeObject(i);

		/* Show the grid */
		displayGrid();
	}

	/**
	 * Place multiple objects on the grid randomly.
	 * You can not get the coords of each object cause we're limited by the non-oriented object.
	 * If we were in OOP, we could have created an list (ArrayLit) and save the data into.
	 * But in our case it's useless and too complicated for students.
	 * 
	 * @param elementID The ID of the object to place
	 * @param count The number of objects to place
	 */
	public void placeObject(int elementID, int count) {
		for(int i = 0; i < count; i++) placeObject(elementID);
	}

	/**
	 * Place an object on the grid
	 * @param elementID The ID of the object to place
	 */
	private int[] placeObject(int elementID) {
		int i, j;
		do {
			i = Random.randomize(grid.length - 1);
			j = Random.randomize(grid[i].length - 1);
		} while (grid[i][j] != EMPTY);
		grid[i][j] = elementID;
		return new int[] {i, j};
	}

	/**
	 * Display the grid
	 */
	private void displayGrid() {
		window.effacerDessins();
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[i].length; j++) {
				// If i = 1 & j = 2 -> Then the image should display in (96 ; 48)
				int x = j * CELL_SIZE;
				int y = i * CELL_SIZE;
				// Display the ground
				window.dessinerImage(IMAGE_FOLDER + "ground.png", x, y, CELL_SIZE, CELL_SIZE);
				if (grid[i][j] == OBJECTIVE) {
					// Display the objective
					window.dessinerImage(IMAGE_FOLDER + "objective.png", x, y, CELL_SIZE, CELL_SIZE);
				} else if(grid[i][j] == WALL) {
					// Display walls
					window.dessinerImage(IMAGE_FOLDER + "wall.png", x, y, CELL_SIZE, CELL_SIZE);
				} else if (grid[i][j] == PLAYER) {
					// Display the player
					window.dessinerImage(IMAGE_FOLDER + "player.png", x, y, CELL_SIZE, CELL_SIZE);
				} else if(grid[i][j] != EMPTY) {
					// Display the humans
					window.dessinerImage(IMAGE_FOLDER + "entity" + (grid[i][j] - 1) + ".png", x, y, CELL_SIZE, CELL_SIZE);
				}
			}
		}
		window.afficherDessins();
	}

	@Override
	public void executerAction(Fenetre instance, String elementName, ActionFenetre action, String value) {
		if(action == ActionFenetre.MINUTEUR) {
			/* Move the humans */
			for(int i = 2; i < OBJECTIVE; i++) {
				int deltaI = Random.randomize(2) - 1;
				int deltaJ = Random.randomize(2) - 1;
				moveEntity(deltaI, deltaJ, i);
			}
		} else if (action == ActionFenetre.PRESSION_TOUCHE) {
			switch (value) {
			case "Z", "Up" -> moveEntity(-1, 0, PLAYER);
			case "D", "Right" -> moveEntity(0, 1, PLAYER);
			case "S", "Down" -> moveEntity(1, 0, PLAYER);
			case "Q", "Left" -> moveEntity(0, -1, PLAYER);
			}
		}
		displayGrid();
	}

	/**
	 * Move entity on the grid
	 * @param deltaI The value on the y axis (If you want to move up, deltaI = -1 and if you want to move down, deltaI = 1)
	 * @param deltaJ The value on the x axis (If you want to move left, deltaJ = -1 and if you want to move right, deltaJ = 1)
	 */
	public void moveEntity(int deltaI, int deltaJ, int OBJECT_ID) {
		/* Get the next cell */
		int i = (positions[OBJECT_ID][0] + deltaI + grid.length) % grid.length;
		int j = (positions[OBJECT_ID][1] + deltaJ + grid[positions[OBJECT_ID][0]].length) % grid[positions[OBJECT_ID][0]].length;

		/* If it's a wall, then prevent the movement */
		if(grid[i][j] == EMPTY || grid[i][j] == OBJECTIVE) {
			/* Move the player */
			grid[ positions[OBJECT_ID][0] ][ positions[OBJECT_ID][1] ] = EMPTY;
			positions[OBJECT_ID][0] = i;
			positions[OBJECT_ID][1] = j;
			grid[ positions[OBJECT_ID][0] ][ positions[OBJECT_ID][1] ] = OBJECT_ID;
		}
	}
}