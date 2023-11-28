package game;

import io.ActionFenetre;
import io.Auditeur;
import io.Fenetre;
import io.Random;

/**
 * Programme utilisant un objet de la classe {@link #Fenetre} comme interface
 * graphique.
 */
public class SixHumans implements Auditeur {

	// Consts
	private static final String DOSSIER_IMAGES = "img\\";
	private static final int TAILLE_CASE = 48;
	private static final int OBSTACLE = -1;
	private static final int EMPTY = 0;
	private static final int PLAYER = 1;
	private static final int OBJECTIVE = 7;

	// Vars
	private static Fenetre window;
	int[][] grid = new int[10][10];
	int iPlayer, jPlayer;

	public static void main(String[] args) {
		/* Creating the window */
		window = new Fenetre("6 humans", 10 * TAILLE_CASE, 10 * TAILLE_CASE);
		SixHumans jeu = new SixHumans();

		/* Spawn objects */
		for(int i = 0; i < 5; i++) jeu.placerObjet(OBSTACLE);
		jeu.placerObjet(OBJECTIVE);
		jeu.placerObjet(PLAYER);

		/* Show the grid */
		jeu.displayGrid();

		/* Display the frame itself */
		window.setAuditeur(jeu);
		window.afficher();
	}

	/**
	 * Place an object on the grid
	 * @param elementID The ID of the object to place
	 */
	private void placerObjet(int elementID) {
		do {
			iPlayer = Random.randomize(grid.length - 1);
			jPlayer = Random.randomize(grid[iPlayer].length - 1);
		} while (grid[iPlayer][jPlayer] != EMPTY);
		grid[iPlayer][jPlayer] = elementID;
	}

	/**
	 * Display the grid
	 */
	private void displayGrid() {
		window.effacerDessins();
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[i].length; j++) {
				// If i = 1 et j = 2 -> Then the image should display in (96 ; 48)
				int x = j * TAILLE_CASE;
				int y = i * TAILLE_CASE;
				// Afficher le sol
				window.dessinerImage(DOSSIER_IMAGES + "sol.png", x, y, TAILLE_CASE, TAILLE_CASE);
				if (grid[i][j] == OBJECTIVE) {
					// Afficher l'objectif
					window.dessinerImage(DOSSIER_IMAGES + "objectif.png", x, y, TAILLE_CASE, TAILLE_CASE);
				}
				if(grid[i][j] == OBSTACLE) {
					// Afficher l'obstacle
					window.dessinerImage(DOSSIER_IMAGES + "mur.png", x, y, TAILLE_CASE, TAILLE_CASE);
				}
				if (grid[i][j] == PLAYER) {
					// Afficher le personnage
					window.dessinerImage(DOSSIER_IMAGES + "personnage1.png", x, y, TAILLE_CASE, TAILLE_CASE);
				}
			}
		}
		window.afficherDessins();
	}

	@Override
	public void executerAction(Fenetre instance, String elementName, ActionFenetre action, String value) {
		if (action == ActionFenetre.PRESSION_TOUCHE) {
			switch (value) {
			case "Haut", "Z" -> movePlayer(-1, 0);
			case "Droite", "D" -> movePlayer(0, 1);
			case "Bas", "S" -> movePlayer(1, 0);
			case "Gauche", "Q" -> movePlayer(0, -1);
			}
			displayGrid();
		}
	}

	/**
	 * Move the player
	 * @param deltaI The value on the y axis (If you want to move up, deltaI = -1 and if you want to move down, deltaI = 1)
	 * @param deltaJ The value on the x axis (If you want to move left, deltaJ = -1 and if you want to move right, deltaJ = 1)
	 */
	public void movePlayer(int deltaI, int deltaJ) {
		/* Move the player */
		grid[iPlayer][jPlayer] = 0;
		iPlayer = (iPlayer + deltaI + grid.length) % grid.length;
		jPlayer = (jPlayer + deltaJ + grid[iPlayer].length) % grid[iPlayer].length;
		grid[iPlayer][jPlayer] = 1;
	}
}