package fabbroniko.environment;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import fabbroniko.environment.EnvironmentStatics.TileType;
import fabbroniko.error.CorruptedFileError;
import fabbroniko.error.ResourceNotFoundError;
import fabbroniko.main.Drawable;
import fabbroniko.main.Game;
import fabbroniko.main.GamePanel;

/**
 * Loads and Draws the specified Map and TileSet into the graphic context.
 * @author nicola.fabbrini
 *
 */
public class TileMap implements Drawable {

	// Tiles data
	private final Dimension tileSize;			// Dimensioni di un singolo tile
	private BufferedImage tileSet;		// Immagine del set di tiles da scomporre
	private int nRows;					// Numero di righe
	private int nCols;					// Numero di colonne
		
	private final List<Tile> tiles;
	private int[][] map;
	private Dimension mapSize;
	private Position minLimits;			// Posizione del limite minimo disegnabile		
	private Position maxLimits;			// Posizione del limite massimo disegnabile
	
	// Current position of the map
	private final Position mapPosition;		// Posizione della mappa nella matrice.
	private final Position lastDrawablePosition;	// Ultima posizione disegnabile
	private int startingXIndex;		// Indice X dal quale cominciare a disegnare
	private int startingYIndex;		// Indice Y dal quale cominciare a disegnare
	
	/**
	 * Constructs a new TileMap with a TileSet and a Map.
	 * @param tileSetP TileSet's path.
	 * @param mapP Map's path.
	 */
	public TileMap(final String tileSetP, final String mapP) {
		tiles = new ArrayList<Tile>();
		tileSize = EnvironmentStatics.TILE_DIMENSION.clone();
		
		mapPosition = Game.ORIGIN.clone();
		lastDrawablePosition = Game.ORIGIN.clone();
		
		try {
			this.tileSet = ImageIO.read(getClass().getResourceAsStream(tileSetP));
		} catch (Exception e) {
			throw new ResourceNotFoundError(tileSetP);
		} 
		
		loadTiles();
		loadMap(mapP);
	}
	
	/**
	 * Splits the TileSet into the basic tiles and it stores them into and array of Tile
	 */
	private void loadTiles() {
		int i = 0;
		for (int currentY = 0; currentY < tileSet.getHeight(); currentY += tileSize.getHeight()) {
			for (int currentX = 0; currentX < tileSet.getWidth(); currentX += tileSize.getWidth()) {
				tiles.add(new Tile(tileSet.getSubimage(currentX, currentY, (int) tileSize.getWidth(), (int) tileSize.getHeight()), TileType.getTileType(i)));
			}
			i++;
		}
	}
	
	/**
	 * Loads the map stored in the specified file into a matrix composed by indexes of the tiles of the game.
	 * @param mapFile
	 */
	private void loadMap(final String mapFile) {
		final InputStream inputStream = getClass().getResourceAsStream(mapFile);
		final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
		
		try {
			nRows = Integer.valueOf(bufferedReader.readLine());
			nCols = Integer.valueOf(bufferedReader.readLine());
		} catch (IOException e) {
			throw new CorruptedFileError(mapFile);
		}
		
		String[] values;
		
		map = new int[nRows][nCols];
		mapSize = new Dimension((int) (nCols * tileSize.getWidth()), (int) (nRows * tileSize.getHeight()));
		minLimits = Game.ORIGIN.clone();
		maxLimits = new Position((int) (mapSize.getWidth() - Game.BASE_WINDOW_SIZE.getWidth()), (int) (mapSize.getHeight() - Game.BASE_WINDOW_SIZE.getHeight()));
		
		for (int i = 0; i < nRows; i++) {
			try {
				final String tmp = bufferedReader.readLine();
				if (tmp == null) {
					throw new CorruptedFileError(mapFile); 
				}
				values = tmp.split("\t");
				if (values.length < nCols) {
					throw new CorruptedFileError(mapFile);
				}
				for (int u = 0; u < nCols; u++) {
					map[i][u] = Integer.valueOf(values[u]);
				}
			} catch (IOException e) {
				throw new CorruptedFileError(mapFile);
			}
		}
	}
	
	/**
	 * Set the position of the map within the matrix.
	 * @param pos New Position
	 */
	public void setPosition(final Position pos) {
		this.mapPosition.copyPosition(pos);
		adjustCoords();
	}
	
	/**
	 * Gets the map's dimension.
	 * @return Returns a new instance of the map's dimension.
	 */
	public Dimension getDimension() {
		return this.mapSize.clone();
	}
	
	/**
	 * Sets the map's position. If it goes out of bounds, its position will be automatically adjusted.
	 * @param x X Coordinate.
	 * @param y Y Coordinate.
	 */
	public void setPosition(final int x, final int y) {
		this.mapPosition.setX(x);
		this.mapPosition.setY(y);
		adjustCoords();
	}
	
	/**
	 * Gets the current map's position.
	 * @return Return's a new instance of the map's location.
	 */
	public Position getPosition() {
		return this.mapPosition.clone();
	}
	
	/**
	 * Gets the type of the tile in the specified {@link Position Position}.
	 * @param pos Position containing the coordinates of a point in the map.
	 * @return Returns Returns the {@link TileType TileType} of the tile that contains the specified Position.
	 */
	public TileType getTileType(final Position pos) {
		return tiles.get(map[(int) (pos.getY() / tileSize.getHeight())][(int) (pos.getX() / tileSize.getWidth())]).getType();
	}
	
	/**
	 * Gets the type of the tile in the specified {@link Point Point}.
	 * @param point Point containing the coordinates of a point in the map. 
	 * @return Returns the {@link TileType TileType} of the tile that contains the specified point.
	 */
	public TileType getTileType(final Point point) {
		return tiles.get(map[(int) (point.getY() / tileSize.getHeight())][(int) (point.getX() / tileSize.getWidth())]).getType();
	}
	
	/**
	 * Gets the tile's dimension.
	 * @return Tile's Dimension.
	 */
	public Dimension getTileSize() {
		return this.tileSize;
	}
	
	/**
	 * Checks if the position is valid. If an invalid position has been set, it will be adjusted.
	 */
	private void adjustCoords() {
		if (mapPosition.getX() < minLimits.getX()) {
			mapPosition.setX(minLimits.getX());
		}
		if (mapPosition.getY() < minLimits.getY()) {
			mapPosition.setY(minLimits.getY());
		}
		if (mapPosition.getX() > maxLimits.getX()) {
			mapPosition.setX(maxLimits.getX());
		}
		if (mapPosition.getY() > maxLimits.getY()) {
			mapPosition.setY(maxLimits.getY());
		}
	}
	
	/**
	 * Determines the values needed for drawing the TileMap
	 */
	private void setDrawValues() {
		startingXIndex = (int) (mapPosition.getX() / tileSize.getWidth());
		startingYIndex = (int) (mapPosition.getY() / tileSize.getHeight());
		
		lastDrawablePosition.setX((int) (mapPosition.getX() + Game.BASE_WINDOW_SIZE.getWidth()));
		lastDrawablePosition.setY((int) (mapPosition.getY() + Game.BASE_WINDOW_SIZE.getHeight()));
	}
	
	/**
	 * @see fabbroniko.main.Drawable#update()
	 */
	@Override
	public void update() {
		if (!GamePanel.getInstance().isRunning()) {
			return;
		}
	}

	/**
	 * @see fabbroniko.main.Drawable#draw(Graphics2D)
	 */
	@Override
	public void draw(final Graphics2D g) {
		setDrawValues();
		
		// settaggio posizione di base da cui iniziare a disegnare.
		final Position basePosToDraw = Game.ORIGIN.clone();
		basePosToDraw.setX(basePosToDraw.getX() - (mapPosition.getX() % tileSize.getWidth()));
		basePosToDraw.setY(basePosToDraw.getY() - (mapPosition.getY() % tileSize.getHeight()));
		
		// Settaggio posizione corrente di disegno
		final Position currentPosToDraw = new Position(basePosToDraw.getX(), basePosToDraw.getY());
		int currentXIndexToDraw = startingXIndex;
		int currentYIndexToDraw = startingYIndex;
		
		while (currentPosToDraw.getY() < Game.BASE_WINDOW_SIZE.getHeight()) {
			currentXIndexToDraw = startingXIndex;
			currentPosToDraw.setX(basePosToDraw.getX());
			while (currentPosToDraw.getX() < Game.BASE_WINDOW_SIZE.getWidth()) {
				if (map[currentYIndexToDraw][currentXIndexToDraw] != 0) {
					g.drawImage(tiles.get(map[currentYIndexToDraw][currentXIndexToDraw]).getImage(), currentPosToDraw.getX(), currentPosToDraw.getY(), null);
				}
				currentPosToDraw.setX((int) (currentPosToDraw.getX() + tileSize.getWidth()));
				currentXIndexToDraw++;
			}
			currentPosToDraw.setY((int) (currentPosToDraw.getY() + tileSize.getHeight()));
			currentYIndexToDraw++;
		}
	}
	
	@Override
	public String toString() {
		final StringBuilder stringBuilder = new StringBuilder();
		
		stringBuilder.append("Map \n");
		for (int i = 0; i < nRows; i++) {
			for (int u = 0; u < nCols; u++) {
				stringBuilder.append("[" + map[i][u] + "-" + tiles.get(map[i][u]).getType() + "]\t");
			}
			stringBuilder.append('\n');
		}
				
		return stringBuilder.toString();
	}


}
