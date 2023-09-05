package net.ianmhuff.picktool;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
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
import net.minecraft.text.Text;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;

import static net.minecraft.registry.tag.ItemTags.*;
import static net.minecraft.util.hit.HitResult.Type.BLOCK;

//TODO make the default key not conflict with any of my other mods
//TODO find a way to go Silk->OtherTool->Silk instead of Silk->Other->Fortune->Silk
//TODO add a toggle that allows the mod the check the hotbar (and offhand?) as well
//TODO add a config to enable/disable the automatic slot selection

//TODO add a second keybinding that rotates through all tools in the inventory
//  maybe have a separate class for this
//  add a config option to set the default tool for this
//    as in the first tool it always picks? in hindsight this seems strange
//  also add a separate selected slot toggle for this input
//    this also seems strange in hindsight hmmmm

//TODO translation keys?

public class PickToolClient implements ClientModInitializer {

    public static int toolSlot = 38; //the hotbar slot that tools will be placed into, default=38=third hotbar slot
    int firstSlot = 9; //the first slot to check when scanning for tools
    int lastSlot = 35; //the last slot
    boolean selectSlot = true; //whether the tool slot should also be selected when a tool is picked
    //boolean checkHotbar = false; //whether the mod should check the hotbar for tools when scanning inventory
    //boolean checkOffhand = false; //whether the mod should check the offhand for tools when scanning inventory

    public void getNextTool(TagKey<Item> toolType, MinecraftClient client) {

        //create arraylist to contain slot numbers of all slots containing correct tools
        ArrayList<Integer> slots = new ArrayList<>();
        slots.add(toolSlot);

        //find all non-hotbar slots containing the correct tool
        for(int i = firstSlot; i <= lastSlot; i++) {
            if(client.player.getInventory().getStack(i).isIn(toolType) /* && i != toolSlot */) {
                slots.add(i);
            }
        }

        //if a correct tool was found
        if(slots.size() > 1) {

            for(int i = 0; i < slots.size() - 1; i++) {

                int slot1 = slots.get(i);
                int slot2 = slots.get(i + 1);

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
                client.player.getInventory().selectedSlot = toolSlot - 36;
            }

        } /*else {
            client.player.sendMessage(Text.of("Tool not found"), false);
        }*/
    }

    @Override
    public void onInitializeClient() {

        MinecraftClient client = MinecraftClient.getInstance();

        //pick tool key binding - defaults to R key
        KeyBinding keyBinding = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.picktool.picktool", // The translation key of the keybinding's name
                InputUtil.Type.KEYSYM, // The type of the keybinding, KEYSYM for keyboard, MOUSE for mouse.
                GLFW.GLFW_KEY_R, // The keycode of the key
                "category.picktool.picktool" // The translation key of the keybinding's category.
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
                    } /*else {
                        client.player.sendMessage(Text.of("no tool"), false);
                    }*/
                } /*else {
                    client.player.sendMessage(Text.of("no block"), false);
                }*/
            }
        });
    }
}