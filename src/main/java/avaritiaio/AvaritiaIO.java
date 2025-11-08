package avaritiaio;

import com.enderio.base.api.capacitor.CapacitorData;
import com.enderio.base.common.init.EIOCreativeTabs;
import com.enderio.base.common.init.EIODataComponents;
import com.enderio.base.common.item.capacitors.CapacitorItem;
import com.enderio.regilite.Regilite;
import com.enderio.regilite.holder.RegiliteItem;
import com.enderio.regilite.registry.ItemRegistry;
import com.mojang.logging.LogUtils;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import org.slf4j.Logger;

@Mod(AvaritiaIO.MODID)
public class AvaritiaIO {
    public static final String MODID = "avaritiaio";
    private static final Logger LOGGER = LogUtils.getLogger();

    private static final Regilite REGILITE = new Regilite(MODID);
    private static final ItemRegistry ITEM_REGISTRY = REGILITE.itemRegistry();

    public static final RegiliteItem<CapacitorItem> INFINITE_CAPACITOR = ITEM_REGISTRY
            .registerItem("infinite_capacitor",
                    props -> new CapacitorItem(
                            props.component(EIODataComponents.CAPACITOR_DATA, CapacitorData.simple(999.9f))))
            .setTab(EIOCreativeTabs.MAIN);


    public AvaritiaIO(IEventBus modEventBus, ModContainer modContainer) {

        REGILITE.register(modEventBus);
        ITEM_REGISTRY.register(modEventBus);

//        NeoForge.EVENT_BUS.register(this);

        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

}
