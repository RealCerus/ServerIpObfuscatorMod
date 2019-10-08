/*
 *  Copyright (c) 2018 Cerus
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Contributors:
 * Cerus
 *
 */

package de.cerus.serveripobfuscator;

import de.cerus.serveripobfuscator.reflections.ReflectionHelper;
import it.unimi.dsi.fastutil.booleans.BooleanConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.ServerListScreen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.gui.widget.button.CheckboxButton;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.resources.I18n;

public class CustomServerListGui extends ServerListScreen {

    private NewGuiPasswordField widget;
    private BetterTextFieldWidget textFieldWidget;

    public CustomServerListGui(BooleanConsumer p_i51117_1_, ServerData p_i51117_2_) {
        super(p_i51117_1_, p_i51117_2_);
    }

    @Override
    protected void init() {
        Minecraft.getInstance().keyboardListener.enableRepeatEvents(true);

        ReflectionHelper.setSilent(this, "field_195170_a", (Button) this.addButton(
                new Button(this.width / 2 - 100, this.height / 4 + 96 + 12, 200,
                        20, I18n.format("selectServer.select"), (p_213026_1_) ->
                        this.connectToServer())), true);

        this.addButton(new Button(this.width / 2 - 100, this.height / 4 + 120 + 12, 200,
                20, I18n.format("gui.cancel"), (p_213025_1_) -> {
            Object o = ReflectionHelper.getSilent(this, "field_213027_d", true);
            ReflectionHelper.invokeSilent(o, "accept", new Object[]{false});
        }));

        textFieldWidget = new BetterTextFieldWidget(this.font, this.width / 2 - 100, 116,
                200, 20, I18n.format("addServer.enterIp"));
        textFieldWidget.setVisible(false);
        textFieldWidget.setMaxStringLength(128);

        widget = new NewGuiPasswordField(this.font, this.width / 2 - 100, 116,
                200, 20, I18n.format("addServer.enterIp", new Object[0]),
                textFieldWidget);
        textFieldWidget.setField(widget);
        widget.setMaxStringLength(128);
        widget.setFocused2(true);
        widget.setText(this.minecraft.gameSettings.lastServer);
        textFieldWidget.setText(this.minecraft.gameSettings.lastServer);

        widget.func_212954_a((p_213024_1_) -> {
            ReflectionHelper.invokeSilent(this, "func_195168_i", new Object[]{true});
        });

        ReflectionHelper.setSilent(this, "field_146302_g", widget, true);

        this.children.add(widget);
        this.func_212928_a(widget);

        CheckboxButton button = new BetterCheckboxButton((widget.x + widget.getWidth() + 10), (widget.y),
                20, 20, I18n.format("serveripobfuscator.gui.showip"), false, show -> {
            widget.show(show);
            if (show) {
                ReflectionHelper.setSilent(widget, "field_146216_j", textFieldWidget.getText(), true);
            } else {
                StringBuilder builder = new StringBuilder();
                for (int l = 0; l < widget.getText().length(); l++)
                    builder.append("*");
                widget.setText(builder.toString());
            }
        });
        this.addButton(button);

        ReflectionHelper.invokeSilent(this, "func_195168_i", new Object[]{true});
    }

    private void connectToServer() {
        Object o = ReflectionHelper.getSilent(this, "field_146301_f", true);
        ReflectionHelper.setSilent(o, "field_78845_b", widget.getRealText());
        o = ReflectionHelper.getSilent(this, "field_213027_d", true);
        BooleanConsumer consumer = (BooleanConsumer) o;
        consumer.accept(true);
    }

    @Override
    public boolean keyPressed(int p_keyPressed_1_, int p_keyPressed_2_, int p_keyPressed_3_) {
        if (this.getFocused() != ReflectionHelper.getSilent(this, "field_146302_g", true)
                || p_keyPressed_1_ != 257 && p_keyPressed_1_ != 335) {
            return super.keyPressed(p_keyPressed_1_, p_keyPressed_2_, p_keyPressed_3_);
        } else {
            this.connectToServer();
            return true;
        }
    }

    @Override
    public void onClose() {
        //super.onClose();
        save();
    }

    private void save() {
        Minecraft.getInstance().gameSettings.lastServer = textFieldWidget.getText();
        Minecraft.getInstance().gameSettings.saveOptions();
    }

    @Override
    protected void finalize() throws Throwable {
        save();
    }
}
