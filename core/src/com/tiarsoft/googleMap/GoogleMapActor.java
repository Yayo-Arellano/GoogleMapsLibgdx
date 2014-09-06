package com.tiarsoft.googleMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class GoogleMapActor extends Image {

	private Vector3 devicePosition;
	private Vector3 deviceSize;

	RequestHandler requestHandler;

	public GoogleMapActor(RequestHandler requestHandler) {
		this.requestHandler = requestHandler;

		devicePosition = new Vector3();
		deviceSize = new Vector3();

		setDrawable(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("badlogic.jpg")))));

	}

	@Override
	public void act(float delta) {
		getStage().getCamera().project(devicePosition.set(getX(), getY(), 0));
		getStage().getCamera().project(deviceSize.set(getWidth(), getHeight(), 0));

		if (getStage().getActors().contains(this, true)) {
			requestHandler.addMap((int) getDevicePositionX(), (int) getDevicePositionY(), (int) getDeviceWidth(), (int) getDeviceHeight());
		}
	}

	public void showMap(Stage stage) {
		stage.addActor(this);
	}

	public void hideMap() {
		this.remove();
		requestHandler.removeMap();
	}

	public int getDevicePositionX() {
		return (int) devicePosition.x;
	}

	public int getDevicePositionY() {
		return (int) devicePosition.y;
	}

	public int getDeviceWidth() {
		return (int) deviceSize.x;
	}

	public int getDeviceHeight() {
		return (int) deviceSize.y;
	}
}
