package fabbroniko.gameobjects;

import java.lang.reflect.InvocationTargetException;

import fabbroniko.environment.Position;
import fabbroniko.environment.TileMap;
import fabbroniko.gamestatemanager.GenericLevel;

public class GameObjectBuilder{

	private AbstractGameObject currentObject;
	private final TileMap tileMap;
	private final GenericLevel genericLevel;
	
	public GameObjectBuilder(final TileMap tileMap, final GenericLevel genericLevel){
		this.tileMap = tileMap;
		this.genericLevel = genericLevel;
	}
	
	public GameObjectBuilder newInstance(final Class<? extends AbstractGameObject> objectClass){
		try {
			currentObject = objectClass.getConstructor(TileMap.class, GenericLevel.class).newInstance(tileMap, genericLevel);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
			throw new fabbroniko.error.InstantiationException(objectClass);
		}
		return this;
	}
	
	public GameObjectBuilder setPosition(final Position position){
		currentObject.setObjectPosition(position);
		return this;
	}
	
	public AbstractGameObject getInstance(){
		return this.currentObject;
	}
}
