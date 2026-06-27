package com.pearlbind.addon.modules;

import com.pearlbind.addon.PearlBindAddon;
import meteordevelopment.meteorclient.events.world.TickEvent;
import meteordevelopment.meteorclient.settings.KeybindSetting;
import meteordevelopment.meteorclient.settings.Setting;
import meteordevelopment.meteorclient.settings.SettingGroup;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.meteorclient.utils.misc.Keybind;
import meteordevelopment.meteorclient.utils.player.FindItemResult;
import meteordevelopment.meteorclient.utils.player.InvUtils;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.util.Hand;
import net.minecraft.item.Items;
import meteordevelopment.meteorclient.systems.modules.Categories;

public class PearlBind extends Module {

    private final SettingGroup sgGeneral = settings.getDefaultGroup();

    public final Setting<Keybind> pearlKey = sgGeneral.add(new KeybindSetting.Builder()
        .name("pearl-key")
        .description("Set the key to automatically throw Ender Pearls.")
        .defaultValue(Keybind.fromKey(67)) // C key
        .build()
    );

    private boolean wasPressed = false;

    public PearlBind() {
        /*super(PearlBindAddon.CATEGORY, "pearl-bind", "Tuşa basıldığında hotbardaki Ender Pearl'ü otomatik fırlatır.");*/
        super(Categories.Player, "pearl-bind", "When the button is pressed, it automatically throws the Ender Pearl from the hotbar.");
    }

    @EventHandler
    private void onTick(TickEvent.Post event) {
        boolean isPressed = pearlKey.get().isPressed();

        if (isPressed && !wasPressed) {
            throwPearl();
        }

        wasPressed = isPressed;
    }

    private void throwPearl() {
        if (mc.player == null || mc.interactionManager == null) return;

        FindItemResult pearl = InvUtils.findInHotbar(Items.ENDER_PEARL);

        if (!pearl.found()) {
            warning("Ender Pearl not found in the hotbar.");
            return;
        }

        // pearl.slot()'a geçiş yap ve geri dönüleceğini (true) belirterek mevcut slotu hafızaya al
        InvUtils.swap(pearl.slot(), true);
        
        // Eşyayı kullan (Fırlat)
        mc.interactionManager.interactItem(mc.player, Hand.MAIN_HAND);
        
        // İşlem bittiğinde hafızadaki orijinal slota otomatik olarak geri dön
        InvUtils.swapBack();
    }
}