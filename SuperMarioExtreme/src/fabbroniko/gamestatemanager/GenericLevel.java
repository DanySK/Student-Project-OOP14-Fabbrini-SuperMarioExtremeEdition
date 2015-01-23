package fabbroniko.gamestatemanager;

import java.util.List;

import fabbroniko.gameobjects.AbstractGameObject;

public interface GenericLevel {
	void addNewObject(AbstractGameObject obj);
	List<AbstractGameObject> getGameObjects();
}
