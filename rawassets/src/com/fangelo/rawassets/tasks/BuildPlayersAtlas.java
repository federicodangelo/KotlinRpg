package com.fangelo.rawassets.tasks;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.tools.texturepacker.TexturePacker;
import com.fangelo.rawassets.tasks.utils.LpcPlayerSplitter;

public class BuildPlayersAtlas extends AssetBuilderTask {

	@Override
	public void onExecute() {
		unpackLpcPlayers();

		buildAtlas();
	}

	private static void buildAtlas() {

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

		String fullPath = "lpc/players/" + playerFileName + ".png";

		FileHandle lpcFileHandle = Gdx.files.internal(fullPath);

		LpcPlayerSplitter processor = new LpcPlayerSplitter(lpcFileHandle);

		processor.exportImagesTo("players/", "player");
	}
}
