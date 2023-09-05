package net.ianmhuff.picktool;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;

/**
 * Sets up Mod Menu.
 */
public class ModMenuIntegration implements ModMenuApi {

    // Method which sets up our Mod Menu entry
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return ModConfigScreen::new;
    }
}