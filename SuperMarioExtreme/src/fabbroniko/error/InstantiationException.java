package fabbroniko.error;

import fabbroniko.gameobjects.AbstractGameObject;

public class InstantiationException extends RuntimeException {

	private static final long serialVersionUID = -1L;
	private final Class<? extends AbstractGameObject> objectClass;

	public InstantiationException(final Class<? extends AbstractGameObject> objectClass){
		this.objectClass = objectClass;
	}
	
	public String toString(){
		return "Something went wrong trying to initialize a new " + objectClass.getSimpleName();
	}
}
