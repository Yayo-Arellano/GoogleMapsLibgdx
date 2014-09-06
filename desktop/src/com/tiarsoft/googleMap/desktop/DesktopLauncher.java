package com.tiarsoft.googleMap.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.tiarsoft.googleMap.MainMap;
import com.tiarsoft.googleMap.RequestHandler;

public class DesktopLauncher {
	public static void main(String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		new LwjglApplication(new MainMap(handler), config);
	}

	static RequestHandler handler = new RequestHandler() {

		@Override
		public void removeMap() {
			// TODO Auto-generated method stub

		}

		@Override
		public void addMap(int x, int y, int width, int height) {
			// TODO Auto-generated method stub

		}
	};
}
