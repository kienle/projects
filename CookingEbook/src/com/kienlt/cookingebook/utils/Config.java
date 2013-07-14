package com.kienlt.cookingebook.utils;

import android.os.Environment;

public class Config {
	public static final String APP_FOLDER = Environment.getExternalStorageDirectory().getPath() + "/Cooking";
    public static final String FOLDER_DATABASE = APP_FOLDER + "/databases";
	public static final String PDF_EXT = ".pdf";
}
