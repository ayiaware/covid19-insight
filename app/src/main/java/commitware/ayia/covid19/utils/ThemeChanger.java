package commitware.ayia.covid19.utils;

import androidx.appcompat.app.AppCompatDelegate;

public class ThemeChanger {

    public ThemeChanger(String theme) {

        switch (theme) {
            case "LightTheme":
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                break;
            case "DarkTheme":
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                break;
            case "FollowSystem":
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
                break;
        }
    }
}
