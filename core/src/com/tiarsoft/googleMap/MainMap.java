package com.tiarsoft.googleMap;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.utils.viewport.StretchViewport;

public class MainMap extends ApplicationAdapter {
	final int WIDTH = 480;
	final int HEIGHT = 800;

	public RequestHandler requestHandler;

	public MainMap(RequestHandler mapHandler) {
		this.requestHandler = mapHandler;

	}

	TextButtonStyle txButtonStyle;
	LabelStyle lbStyle;
	BitmapFont font;

	Stage stage;
	GoogleMapActor mapActor;

	TextButton btShowMap;
	TextButton btHideMap;

	Label lbPosition;

	@Override
	public void create() {
		stage = new Stage(new StretchViewport(WIDTH, HEIGHT));

		font = new BitmapFont();

		TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("ui.txt"));
		txButtonStyle = new TextButtonStyle(new NinePatchDrawable(atlas.createPatch("bt")), new NinePatchDrawable(atlas.createPatch("btDown")), null,
				font);
		lbStyle = new LabelStyle(font, Color.WHITE);

		mapActor = new GoogleMapActor(requestHandler);
		mapActor.setSize(450, 450);
		mapActor.setPosition(20, 300);

		btShowMap = new TextButton("Show Map", txButtonStyle);
		btShowMap.setPosition(10, 50);
		btShowMap.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				mapActor.showMap(stage);
			}
		});

		btHideMap = new TextButton("Hide Map", txButtonStyle);
		btHideMap.setPosition(110, 50);
		btHideMap.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				mapActor.hideMap();
			}
		});

		lbPosition = new Label("", lbStyle);
		lbPosition.setPosition(50, 150);

		stage.addActor(btShowMap);
		stage.addActor(btHideMap);
		stage.addActor(lbPosition);

		Gdx.input.setInputProcessor(stage);
	}

	public void setPosition(float lat, float lng) {
		lbPosition.setText("Latitude: " + lat + ", Longitude: " + lng);
	}

	@Override
	public void render() {
		stage.act();

		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.draw();
	}

	@Override
	public void resize(int width, int height) {
		stage.getViewport().update(width, height, true);
	}

}
