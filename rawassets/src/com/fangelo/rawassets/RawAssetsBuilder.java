package com.fangelo.rawassets;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.tools.texturepacker.TexturePacker;

public class RawAssetsBuilder extends ApplicationAdapter {

	static private final String REAL_ASSETS_PATH = "../../android/assets/";

	public static void main(String[] args) {

		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		new LwjglApplication(new RawAssetsBuilder(), config);
	}

	@Override
	public void create() {
		System.out.println("packaging assets!");

		packUITextures();

		packDefaultTextures();

		unpackLpcPlayers();

		packPlayers();

		Gdx.app.exit();
	}

	private static void packPlayers() {

		TexturePacker.Settings settings = new TexturePacker.Settings();

		settings.fast = false;
		settings.grid = false;
		settings.paddingX = 2;
		settings.paddingY = 2;
		settings.useIndexes = false;
		settings.maxWidth = settings.maxHeight = 1024;
		//settings.filterMag = Texture.TextureFilter.Linear;
		//settings.filterMin = Texture.TextureFilter.Linear;
		settings.stripWhitespaceX = true;
		settings.stripWhitespaceY = true;

		TexturePacker.process(settings, "players", REAL_ASSETS_PATH + "players", "players");
	}

	private static void unpackLpcPlayers() {

		//Empty players directory before processing
		Gdx.files.local("players").emptyDirectory();

		unpackLpcPlayer("player");
	}

	private static void unpackLpcPlayer(String playerFileName) {

		String fullPath = "lpc-players/" + playerFileName + ".png";

		FileHandle lpcFileHandle = Gdx.files.internal(fullPath);

		LpcPlayerSplitter processor = new LpcPlayerSplitter(lpcFileHandle);

		processor.exportImagesTo("players/", "player");
	}

	private static void packUITextures() {

		TexturePacker.Settings settings = new TexturePacker.Settings();

		//settings.fast = true;
		//settings.grid = true;
		settings.paddingX = 2;
		settings.paddingY = 2;
		//settings.useIndexes = false;
		//settings.maxWidth = settings.maxHeight = 1024;
		settings.duplicatePadding = true;
		settings.filterMag = Texture.TextureFilter.Linear;
		settings.filterMin = Texture.TextureFilter.Linear;

		TexturePacker.process(settings, "ui", REAL_ASSETS_PATH + "ui", "ui");
	}

	private static void packDefaultTextures() {

		TexturePacker.Settings settings = new TexturePacker.Settings();

		settings.fast = true;
		settings.grid = true;
		settings.paddingX = 2;
		settings.paddingY = 2;
		settings.useIndexes = false;
		settings.maxWidth = settings.maxHeight = 2048;
		settings.duplicatePadding = true;
		settings.filterMag = Texture.TextureFilter.Linear;
		settings.filterMin = Texture.TextureFilter.Linear;

		TexturePacker.process(settings, "images", REAL_ASSETS_PATH + "images", "images");
	}
}
