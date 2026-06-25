package com.pearlbind.addon;

import com.mojang.logging.LogUtils;
import com.pearlbind.addon.modules.PearlBind;
import meteordevelopment.meteorclient.addons.GithubRepo;
import meteordevelopment.meteorclient.addons.MeteorAddon;
import meteordevelopment.meteorclient.systems.modules.Category;
import meteordevelopment.meteorclient.systems.modules.Modules;
import org.slf4j.Logger;
import meteordevelopment.meteorclient.systems.modules.Categories;

public class PearlBindAddon extends MeteorAddon {
    public static final Logger LOG = LogUtils.getLogger();
    /*public static final Category CATEGORY = new Category("Extras");*/

    @Override
    public void onInitialize() {
        LOG.info("Initializing Pearl Bind Addon");
        Modules.get().add(new PearlBind());
    }

    /*
    @Override
    public void onRegisterCategories() {
        Modules.registerCategory(CATEGORY);
    }
    */

    @Override
    public String getPackage() {
        return "com.pearlbind.addon";
    }

    @Override
    public GithubRepo getRepo() {
        return new GithubRepo("yourusername", "meteor-pearlbind-addon");
    }
}