package avaritiaio;

import com.enderio.base.api.capacitor.CapacitorData;
import com.enderio.base.api.grindingball.GrindingBallData;
import com.enderio.base.common.init.EIODataComponents;
import com.enderio.base.common.item.capacitors.CapacitorItem;
import com.enderio.base.common.item.misc.MaterialItem;
import com.enderio.base.common.tag.EIOTags;
import com.enderio.regilite.Regilite;
import com.enderio.regilite.holder.RegiliteItem;
import com.enderio.regilite.registry.ItemRegistry;
import committee.nova.mods.avaritia.init.registry.ModRarities;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

@Mod(AvaritiaIO.MODID)
public class AvaritiaIO {
    public static final String MODID = "avaritiaio";
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);
    private static final Regilite REGILITE = new Regilite(MODID);
    private static final ItemRegistry ITEM_REGISTRY = REGILITE.itemRegistry();

    // Capacitors
    public static final RegiliteItem<CapacitorItem> INFINITE_CAPACITOR = ITEM_REGISTRY
            .registerItem("infinite_capacitor",
                    props -> new CapacitorItem(
                            props.component(EIODataComponents.CAPACITOR_DATA, CapacitorData.simple(999.9F))
                                    .rarity(ModRarities.COSMIC.getValue())));

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> AVARITIA_IO_TAB =
            CREATIVE_MODE_TABS.register("avaritia_io", () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.avaritiaio"))
                    .icon(() -> new ItemStack(INFINITE_CAPACITOR.get()))
                    .build());

    // Grinding Balls

    public static final RegiliteItem<MaterialItem> INFINITY_BALL = grindingBall("infinity_grinding_ball",
            new GrindingBallData(5.0F, 5.0F, 0.01F, 2000000000));

    public static final RegiliteItem<MaterialItem> NEUTRONIUM_BALL = grindingBall("neutronium_grinding_ball",
            new GrindingBallData(1.0F, 5.0F, 0.1F, 2000000));

    private static RegiliteItem<MaterialItem> grindingBall(String name, GrindingBallData grindingBallData) {
        return ITEM_REGISTRY
                .registerItem(
                        name,
                        props -> new MaterialItem(
                                props.component(EIODataComponents.GRINDING_BALL, grindingBallData)
                                        .rarity(name.equals("infinity_grinding_ball")
                                                ? ModRarities.COSMIC.getValue()
                                                : ModRarities.EPIC),
                                false
                        ))
                .addItemTags(EIOTags.Items.GRINDING_BALLS);
    }

    public AvaritiaIO(IEventBus modEventBus, ModContainer modContainer) {

        REGILITE.register(modEventBus);
        ITEM_REGISTRY.register(modEventBus);
        CREATIVE_MODE_TABS.register(modEventBus);

        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    @SubscribeEvent
    public static void onBuildCreativeTab(BuildCreativeModeTabContentsEvent event) {
        if (event.getTab() == AVARITIA_IO_TAB.get()) {
            event.accept(INFINITE_CAPACITOR.get());
        }
    }


}
