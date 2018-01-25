package com.fangelo.kotlinrpg.ui.screen;

public class Screens {

	static public SettingsScreen settingsScreen;
	static public MainMenuScreen mainMenuScreen;
	static public AboutScreen aboutScreen;

	static public void init(/*World world, WorldInputHandler worldInputHandler*/) {
		settingsScreen = new SettingsScreen();
		mainMenuScreen = new MainMenuScreen();
		aboutScreen = new AboutScreen();
	}

	static public void dispose() {
		settingsScreen = null;
		mainMenuScreen = null;
		aboutScreen = null;
	}
}
