package net.ianmhuff.picktool;

import com.terraformersmc.modmenu.api.ModMenuApi;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.SliderWidget;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import static net.ianmhuff.picktool.PickToolClient.toolSlot;
import static net.ianmhuff.picktool.PickToolClient.selectSlot;
import static net.ianmhuff.picktool.PickToolClient.checkHotbar;

//TODO center the slider bar for slot selection


@Environment(EnvType.CLIENT)
public class ModConfigScreen extends Screen implements ModMenuApi {

    private final Screen parent;

    private static final Identifier BACKGROUND_TEXTURE = new Identifier("minecraft", "textures/gui/options_background.png");

    protected ModConfigScreen(Screen parent) {
        super(Text.literal("Pick Tool Config Screen"));
        this.parent = parent;
    }

    @Override
    public void close() {
        client.setScreen(parent);
    }

    protected ModConfigScreen() {
        // The parameter is the title of the screen,
        // which will be narrated when you enter the screen.
        super(Text.literal("Pick Tool Config Screen"));
        parent = null;
    }

    @Override
    protected void init() {

        //slider for tool slot
        SliderWidget toolSlotSlider = new SliderWidget(
                this.width / 2 - 205,
                20,
                200,
                20,
                Text.translatable("config.picktool.toolslot.button").append(String.valueOf(toolSlot + 1)),
                (double) (toolSlot) / 8.0
        ) {
            @Override
            protected void updateMessage() {
                // Update the label that shows the selected value
                setMessage(Text.translatable("config.picktool.toolslot.button").append(String.valueOf((int) (this.value * 8.0) + 1)));
            }

            @Override
            protected void applyValue() {
                toolSlot = ((int) (this.value * 8.0));
            }

        };
        //add tooltip
        toolSlotSlider.setTooltip(Tooltip.of(Text.translatable("config.picktool.toolslot.tooltip")));
        // add the slider to the screen
        this.addDrawableChild(toolSlotSlider);


        final ButtonWidget selectSlotButton = ButtonWidget.builder(
            Text.translatable("config.picktool.selectslot.button").append(String.valueOf(selectSlot)),
            button -> {
                selectSlot = !selectSlot;
                button.setMessage(Text.translatable("config.picktool.selectslot.button").append(String.valueOf(selectSlot)));
            } )
            .dimensions(width / 2 + 5, 20, 200, 20)
            .tooltip(Tooltip.of(Text.translatable("config.picktool.selectslot.tooltip")))
            .build();
        this.addDrawableChild(selectSlotButton);


        final ButtonWidget checkHotbarButton = ButtonWidget.builder(
            Text.translatable("config.picktool.checkhotbar.button").append(String.valueOf(checkHotbar)),
            button -> {
                checkHotbar = !checkHotbar;
                button.setMessage(Text.translatable("config.picktool.checkhotbar.button").append(String.valueOf(checkHotbar)));
            } )
            .dimensions(width / 2 - 205, 50, 200, 20)
            .tooltip(Tooltip.of(Text.translatable("config.picktool.checkhotbar.tooltip")))
            .build();
        this.addDrawableChild(checkHotbarButton);


        final ButtonWidget doneButton = ButtonWidget.builder(Text.translatable("Done"),
                        button -> client.setScreen(this.parent))
                .dimensions(this.width / 2 - 130, this.height - 50, 260, 20)
                .build();
        this.addDrawableChild(doneButton);
    }

    @Override
    public void render(final DrawContext matrices, final int mouseX, final int mouseY, final float delta) {
        this.renderBackground(matrices);
        super.render(matrices, mouseX, mouseY, delta);
    }

}
