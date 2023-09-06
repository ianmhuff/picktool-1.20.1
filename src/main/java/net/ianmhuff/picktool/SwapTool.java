package net.ianmhuff.picktool;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;

import static net.ianmhuff.picktool.PickToolClient.*;
import static net.minecraft.registry.tag.ItemTags.*;
import static net.minecraft.util.hit.HitResult.Type.BLOCK;



public class SwapTool implements ClientModInitializer {

    int firstSlot = 0, lastSlot = 35; //the first & last slots to check when scanning for tools

    @Override
    public void onInitializeClient() {

        MinecraftClient client = MinecraftClient.getInstance();

        //pick tool key binding - defaults to G key
        KeyBinding keyBinding = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.picktool.swaptool", // The translation key of the keybinding's name
                InputUtil.Type.KEYSYM, // The type of the keybinding, KEYSYM for keyboard, MOUSE for mouse.
                GLFW.GLFW_KEY_G, // The keycode of the key
                "category.picktool.keybindings" // The translation key of the keybinding's category.
        ));

        //runs whenever key binding is pressed
        ClientTickEvents.END_CLIENT_TICK.register(clientTick -> {
            while (keyBinding.wasPressed()) {

                if(checkHotbar) { firstSlot = 0; }
                else { firstSlot = 9; }

                //create arraylist to contain slot numbers of all slots containing tools
                ArrayList<Integer> slots = new ArrayList<>();
                slots.add(toolSlot);

                //find all non-hotbar slots containing the correct tool
                for(int i = firstSlot; i <= lastSlot; i++) {
                    if(i == toolSlot) { continue; }

                    ItemStack itemStack = client.player.getInventory().getStack(i);

                    if(itemStack.isIn(PICKAXES)
                            || itemStack.isIn(SHOVELS)
                            || itemStack.isIn(AXES)
                            || itemStack.isIn(HOES)) {
                        slots.add(i);
                    }
                }

                //if a tool was found in a slot != toolSlot
                if(slots.size() > 1) {

                    for(int i = 0; i < slots.size() - 1; i++) {

                        int slot1 = slots.get(i);
                        int slot2 = slots.get(i + 1);

                        if(slot1 < 9) slot1 += 36;

                        client.interactionManager.clickSlot(
                                client.player.playerScreenHandler.syncId,
                                slot1,
                                slot2,
                                SlotActionType.SWAP,
                                client.player
                        );
                    }

                    //change player's selected slot to the tool slot
                    if(selectSlot) {
                        client.player.getInventory().selectedSlot = toolSlot;
                    }
                }
            }
        });
    }
}