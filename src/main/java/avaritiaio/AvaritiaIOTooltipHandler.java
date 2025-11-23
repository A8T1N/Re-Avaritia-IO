package avaritiaio;

import com.enderio.base.common.init.EIODataComponents;
import com.enderio.base.common.lang.EIOLang;
import com.enderio.core.common.util.TooltipUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.minecraft.util.Mth;
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

    private static final ChatFormatting[] rainbow = new ChatFormatting[]{
            ChatFormatting.RED, ChatFormatting.GOLD, ChatFormatting.YELLOW,
            ChatFormatting.GREEN, ChatFormatting.AQUA, ChatFormatting.BLUE,
            ChatFormatting.LIGHT_PURPLE
    };
    public static long lastTime = System.currentTimeMillis();

    public static String ColorTransformationFormatting(String input, ChatFormatting[] colours, double delay, int step, int posstep) {
        StringBuilder stringBuilder = new StringBuilder(input.length() * 3);

        if (delay <= 0.0D) {
            delay = 0.001D;
        }

        int offset = Mth.floor((System.currentTimeMillis() - lastTime) / delay) % colours.length;

        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);

            int colorIndex = ((i * posstep) + colours.length - offset) % colours.length;

            stringBuilder.append(colours[colorIndex].toString());
            stringBuilder.append(c);
        }

        return stringBuilder.toString();
    }

    public static Component makeRainbow(String text) {
        return Component.literal(ColorTransformationFormatting(text, rainbow, 80.0D, 1, 1));
    }


    @SubscribeEvent(priority = EventPriority.LOW)
    public static void addAdvancedTooltips(ItemTooltipEvent evt) {
        ItemStack forItem = evt.getItemStack();
        List<Component> components = evt.getToolTip();
        addCapacitorTooltips(evt, forItem, components);
    }

    @OnlyIn(Dist.CLIENT)
    private static void addCapacitorTooltips(ItemTooltipEvent event, ItemStack itemStack, List<Component> components) {
        if (itemStack.has(EIODataComponents.CAPACITOR_DATA) && itemStack.is(AvaritiaIO.INFINITE_CAPACITOR)) {
            for (int i = 0; i < components.size(); i++) {
                if (components.get(i).getContents() instanceof TranslatableContents tc) {
                    if (tc.getKey().equals("tooltip.enderio.capacitor.base")) {
                        event.getToolTip().set(i, TooltipUtil.styledWithArgs(EIOLang.CAPACITOR_TOOLTIP_BASE, makeRainbow(I18n.get("tooltip.infinity"))));
                    }
                }
            }
        }
    }

}
