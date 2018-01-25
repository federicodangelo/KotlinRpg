package com.fangelo.kotlinrpg

import android.content.pm.PackageManager

class AndroidPlatformAdapter(launcher: AndroidLauncher) : PlatformAdapter() {
    init {
        try {
            this.version = launcher.packageManager.getPackageInfo(launcher.getPackageName(), 0).versionName + " (code " +
                    launcher.packageManager.getPackageInfo(launcher.getPackageName(), 0).versionCode + ")"
        } catch (ex: PackageManager.NameNotFoundException) {
            this.version = ""
        }


    }
}