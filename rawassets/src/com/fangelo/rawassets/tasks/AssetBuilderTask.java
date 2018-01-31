package com.fangelo.rawassets.tasks;

public abstract class AssetBuilderTask {

	static protected final String REAL_ASSETS_PATH = "../../android/assets/";

	public void execute() {
		onExecute();
	}

	protected abstract void onExecute();

}
