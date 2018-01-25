package com.fangelo.kotlinrpg.ui.dialog;

import com.fangelo.libraries.ui.Dialog;
import com.fangelo.libraries.ui.DialogResult;

public class ConfirmDialog extends Dialog {

	public ConfirmDialog(String title, String text) {
		super(title);

		text(text);

		button("Yes", DialogResult.Yes);
		button("No", DialogResult.No);
	}
}
