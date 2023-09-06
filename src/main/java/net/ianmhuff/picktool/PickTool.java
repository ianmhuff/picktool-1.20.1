package net.ianmhuff.picktool;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.item.Item;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;

import static net.ianmhuff.picktool.PickToolClient.toolSlot;
import static net.ianmhuff.picktool.PickToolClient.selectSlot;
import static net.ianmhuff.picktool.PickToolClient.checkHotbar;
import static net.minecraft.registry.tag.ItemTags.*;
import static net.minecraft.util.hit.HitResult.Type.BLOCK;

//TODO make the default key not conflict with any of my other mods
//TODO find a way to go Silk->OtherTool->Silk instead of Silk->Other->Fortune->Silk

//TODO translation keys?

public class PickTool implements ClientModInitializer {

    int firstSlot = 0, lastSlot = 35; //the first & last slots to check when scanning for tools

    public void getNextTool(TagKey<Item> toolType, MinecraftClient client) {

        if(checkHotbar) { firstSlot = 0; }
        else { firstSlot = 9; }

        //create arraylist to contain slot numbers of all slots containing correct tools
        ArrayList<Integer> slots = new ArrayList<>();
        slots.add(toolSlot);

        //find all non-hotbar slots containing the correct tool
        for(int i = firstSlot; i <= lastSlot; i++) {
            if(i == toolSlot) { continue; }
            if(client.player.getInventory().getStack(i).isIn(toolType)) {
                slots.add(i);
            }
        }

        //if a correct tool was found
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

    @Override
    public void onInitializeClient() {

        MinecraftClient client = MinecraftClient.getInstance();

        //pick tool key binding - defaults to T key
        KeyBinding keyBinding = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.picktool.picktool", // The translation key of the keybinding's name
                InputUtil.Type.KEYSYM, // The type of the keybinding, KEYSYM for keyboard, MOUSE for mouse.
                GLFW.GLFW_KEY_T, // The keycode of the key
                "category.picktool.keybindings" // The translation key of the keybinding's category.
        ));

        //runs whenever key binding is pressed
        ClientTickEvents.END_CLIENT_TICK.register(clientTick -> {
            while (keyBinding.wasPressed()) {

                //check what the player is targeting
                HitResult hit = client.crosshairTarget;

                //if the player is targeting a block
                if(hit.getType() == BLOCK) {
                    BlockHitResult blockHit = (BlockHitResult) hit;
                    BlockPos blockPos = blockHit.getBlockPos();
                    BlockState blockState = client.world.getBlockState(blockPos);

                    //check if the block is in a mineable tag
                    if(blockState.isIn(BlockTags.PICKAXE_MINEABLE)) {
                        getNextTool(PICKAXES, client);
                    } else if(blockState.isIn(BlockTags.SHOVEL_MINEABLE)) {
                        getNextTool(SHOVELS, client);
                    } else if(blockState.isIn(BlockTags.AXE_MINEABLE)) {
                        getNextTool(AXES, client);
                    } else if(blockState.isIn(BlockTags.HOE_MINEABLE)) {
                        getNextTool(HOES, client);
                    }
                }
            }
        });
    }
}