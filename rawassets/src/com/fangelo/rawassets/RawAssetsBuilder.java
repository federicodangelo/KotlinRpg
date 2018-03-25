package com.fangelo.rawassets;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.fangelo.rawassets.tasks.AssetBuilderTask;
import com.fangelo.rawassets.tasks.BuildItemsAtlas;
import com.fangelo.rawassets.tasks.BuildPlayersAtlas;
import com.fangelo.rawassets.tasks.BuildTilesAtlas;
import com.fangelo.rawassets.tasks.BuildUIAtlas;

public class RawAssetsBuilder extends ApplicationAdapter {

	public static void main(String[] args) {

		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.forceExit = false;
		new LwjglApplication(new RawAssetsBuilder(), config);
	}

	@Override
	public void create() {
		System.out.println("Building Assets!");

		AssetBuilderTask[] tasks = new AssetBuilderTask[] { //
				new BuildUIAtlas(), //
				new BuildPlayersAtlas(), //
				new BuildTilesAtlas(), //
				new BuildItemsAtlas() //
		};

		for (AssetBuilderTask task : tasks) {
			System.out.println();
			System.out.println("****************************");
			System.out.println("****************************");
			System.out.println("Executing task " + task.getClass().getSimpleName());
			System.out.println("****************************");
			System.out.println("****************************");
			System.out.println();
			task.execute();
		}

		Gdx.app.exit();
	}
}
