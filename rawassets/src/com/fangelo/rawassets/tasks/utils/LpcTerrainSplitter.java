package com.fangelo.rawassets.tasks.utils;

import java.util.ArrayList;

public class LpcTerrainSplitter extends LpcTilesSplitter {

	public LpcTerrainSplitter() {
		super(new ArrayList<LpcTile>() {
			{
				add(new LpcTile("big-spot", 0, 0));
				add(new LpcTile("small-spot", 0, 1));
				add(new LpcTile("center-0", 1, 3));
				add(new LpcTile("center-1", 0, 5));
				add(new LpcTile("center-2", 1, 5));
				add(new LpcTile("center-3", 2, 5));

				add(new LpcTile("inner-north-west", 1, 0));
				add(new LpcTile("inner-north-east", 2, 0));
				add(new LpcTile("inner-south-west", 1, 1));
				add(new LpcTile("inner-south-east", 2, 1));

				add(new LpcTile("outer-north-west", 0, 2));
				add(new LpcTile("outer-north", 1, 2));
				add(new LpcTile("outer-north-east", 2, 2));

				add(new LpcTile("outer-west", 0, 3));
				add(new LpcTile("outer-east", 2, 3));

				add(new LpcTile("outer-south-west", 0, 4));
				add(new LpcTile("outer-south", 1, 4));
				add(new LpcTile("outer-south-east", 2, 4));
			}
		});
	}
}
