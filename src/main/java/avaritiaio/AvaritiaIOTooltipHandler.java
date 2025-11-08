package avaritiaio;

import com.enderio.base.common.init.EIODataComponents;
import com.enderio.base.common.lang.EIOLang;
import com.enderio.core.common.util.TooltipUtil;
import committee.nova.mods.avaritia.api.utils.lang.TextUtils;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;

import java.util.List;

@EventBusSubscriber(value = Dist.CLIENT)
public class AvaritiaIOTooltipHandler {
    @SubscribeEvent(priority = EventPriority.LOW)
    public static void addAdvancedTooltips(ItemTooltipEvent evt) {
        ItemStack forItem = evt.getItemStack();
        List<Component> components = evt.getToolTip();
        addCapacitorTooltips(evt, forItem,components);
    }

    @OnlyIn(Dist.CLIENT)
    private static void addCapacitorTooltips(ItemTooltipEvent event, ItemStack itemStack,List<Component> components) {
        if (itemStack.has(EIODataComponents.CAPACITOR_DATA) && itemStack.is(AvaritiaIO.INFINITE_CAPACITOR)) {
            for (int x = 0; x < components.size(); x++) {
                if (components.get(x).getContents() instanceof TranslatableContents tc) {
                    if (tc.getKey().equals("tooltip.enderio.capacitor.base")) {
                        event.getToolTip().set(x, TooltipUtil.styledWithArgs(EIOLang.CAPACITOR_TOOLTIP_BASE, TextUtils.makeFabulous(I18n.get("tooltip.infinity"))));
                        return;
                    }
                }
            }
        }
    }

}
