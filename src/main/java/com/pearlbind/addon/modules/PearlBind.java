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
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.Items;
import meteordevelopment.meteorclient.systems.modules.Categories;

public class PearlBind extends Module {

    private final SettingGroup sgGeneral = settings.getDefaultGroup();

    public final Setting<Keybind> pearlKey = sgGeneral.add(new KeybindSetting.Builder()
        .name("pearl-key")
        .description("Otomatik Ender Pearl fırlatmak için tuş atayın.")
        .defaultValue(Keybind.fromKey(67)) // C tuşu
        .build()
    );

    private boolean wasPressed = false;

    public PearlBind() {
        /*super(PearlBindAddon.CATEGORY, "pearl-bind", "Tuşa basıldığında hotbardaki Ender Pearl'ü otomatik fırlatır.");*/
        super(Categories.Player, "pearl-bind", "Tuşa basıldığında hotbardaki Ender Pearl'ü otomatik fırlatır.");
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
        if (mc.player == null || mc.gameMode == null) return;

        FindItemResult pearl = InvUtils.findInHotbar(Items.ENDER_PEARL);

        if (!pearl.found()) {
            warning("Hotbarda Ender Pearl bulunamadı.");
            return;
        }

        // pearl.slot()'a geçiş yap ve geri dönüleceğini (true) belirterek mevcut slotu hafızaya al
        InvUtils.swap(pearl.slot(), true);
        
        // Eşyayı kullan (Fırlat)
        mc.gameMode.useItem(mc.player, InteractionHand.MAIN_HAND);
        
        // İşlem bittiğinde hafızadaki orijinal slota otomatik olarak geri dön
        InvUtils.swapBack();
    }
}