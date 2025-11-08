package avaritiaio;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;

@EventBusSubscriber(modid = AvaritiaIO.MODID, bus = EventBusSubscriber.Bus.MOD)
public class AvaritiaIOCreativeTabEvents {

    @SubscribeEvent
    public static void onBuildCreativeTab(BuildCreativeModeTabContentsEvent event) {
        if (event.getTab() == AvaritiaIO.AVARITIA_IO_TAB.get()) {
            event.accept(AvaritiaIO.INFINITE_CAPACITOR.get());
        }
    }
}
