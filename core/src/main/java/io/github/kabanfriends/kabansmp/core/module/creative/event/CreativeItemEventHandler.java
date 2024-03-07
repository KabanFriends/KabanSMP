package io.github.kabanfriends.kabansmp.core.module.creative.event;

import io.github.kabanfriends.kabansmp.core.util.api.ItemAPI;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCreativeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class CreativeItemEventHandler implements Listener {

    @EventHandler
    public void onCreativeItemClick(InventoryCreativeEvent event) {
        ItemStack item = event.getCursor();
        ItemMeta itemMeta = item.getItemMeta();

        // Remove sus entity data tags
        if (item.getType() == Material.PAINTING) {
            String variant = ItemAPI.getPaintingItemVariant(itemMeta);
            ItemAPI.clearEntityMeta(itemMeta);
            if (variant != null) {
                ItemAPI.setPaintingItemVariant(itemMeta, variant);
            }
        } else {
            ItemAPI.clearEntityMeta(itemMeta);
        }

        item.setItemMeta(itemMeta);
    }
}
