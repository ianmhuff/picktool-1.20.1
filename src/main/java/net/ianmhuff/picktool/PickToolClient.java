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

import static net.minecraft.registry.tag.ItemTags.*;
import static net.minecraft.util.hit.HitResult.Type.BLOCK;

//TODO make the default key not conflict with any of my other mods
//TODO find a way to go Silk->OtherTool->Silk instead of Silk->Other->Fortune->Silk

//TODO add a second keybinding that rotates through all tools in the inventory
//  maybe have a separate class for this
//  add a config option to set the default tool for this
//    as in the first tool it always picks? in hindsight this seems strange
//  also add a separate selected slot toggle for this input
//    this also seems strange in hindsight hmmmm

//TODO translation keys?

public class PickToolClient {

    public static int toolSlot = 2; //the hotbar slot that tools will be placed into, default=38=third hotbar slot
    public static boolean selectSlot = true; //whether the tool slot should also be selected when a tool is picked
    public static boolean checkHotbar = true; //whether to check the hotbar for tools when scanning inventory
    //public static boolean checkOffhand = false; //whether the mod should check the offhand for tools when scanning inventory


}