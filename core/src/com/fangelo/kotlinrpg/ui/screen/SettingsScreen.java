package com.fangelo.kotlinrpg.ui.screen;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.fangelo.libraries.ui.Screen;

public class SettingsScreen extends Screen {

	static public boolean showFps;
	static public boolean showToolLabels;
	static public boolean showScreenshotButton;
	static public boolean showTools;

	private Table settingsContainer;

	private Button showFpsButton;
	private Button showToolLabelsButton;
	private Button showToolsButton;
	private Button showScreenshotButtonButton;

	private Button closeButton;

	public SettingsScreen() {

		settingsContainer = new Table(skin).padLeft(30).padRight(30).padBottom(5).padTop(5);
		settingsContainer.setBackground("panel-brown");

		add(settingsContainer).center();

		settingsContainer.add("Settings").top().padBottom(20);
		settingsContainer.row();

		showFpsButton = new CheckBox("Show FPS / Stats", skin);
		showFpsButton.setChecked(showFps);
		showFpsButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				showFps = showFpsButton.isChecked();
			}
		});
		settingsContainer.add(showFpsButton).pad(5);

		settingsContainer.row();

		HorizontalGroup toolsGroup = new HorizontalGroup();
		toolsGroup.space(5);

		showToolsButton = new CheckBox("Show Tools", skin);
		showToolsButton.setChecked(showTools);
		showToolsButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				showTools = showToolsButton.isChecked();
			}
		});
		toolsGroup.addActor(showToolsButton);

		showToolLabelsButton = new CheckBox("Labels", skin);
		showToolLabelsButton.setChecked(showToolLabels);
		showToolLabelsButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				showToolLabels = showToolLabelsButton.isChecked();
			}
		});
		toolsGroup.addActor(showToolLabelsButton);

		settingsContainer.add(toolsGroup).pad(5);

		settingsContainer.row();

		showScreenshotButtonButton = new CheckBox("Show Screenshot Button", skin);
		showScreenshotButtonButton.setChecked(showScreenshotButton);
		showScreenshotButtonButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				showScreenshotButton = showScreenshotButtonButton.isChecked();
			}
		});
		settingsContainer.add(showScreenshotButtonButton).pad(5);

		settingsContainer.row();

		settingsContainer.row();

		closeButton = new TextButton("Close", skin);

		closeButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				onBackButtonPressed();
			}
		});

		settingsContainer.add(closeButton).padTop(20);
	}
}
