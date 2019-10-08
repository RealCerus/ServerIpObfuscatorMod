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

import it.unimi.dsi.fastutil.booleans.BooleanConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.ServerListScreen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.gui.widget.button.CheckboxButton;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.resources.I18n;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class CustomServerListGui extends ServerListScreen {

    private NewGuiPasswordField widget;
    private BetterTextFieldWidget textFieldWidget;

    public CustomServerListGui(BooleanConsumer p_i51117_1_, ServerData p_i51117_2_) {
        super(p_i51117_1_, p_i51117_2_);
    }

    @Override
    protected void init() {
        try {
            Minecraft.getInstance().keyboardListener.enableRepeatEvents(true);

            Field field = getClass().getSuperclass().getDeclaredField("field_195170_a"); //connect button field
            field.setAccessible(true);
            field.set(this, (Button) this.addButton(new Button(this.width / 2 - 100, this.height
                    / 4 + 96 + 12, 200, 20, I18n.format("selectServer.select"),
                    (p_213026_1_) -> this.connectToServer())));

            this.addButton(new Button(this.width / 2 - 100, this.height / 4 + 120 + 12, 200,
                    20, I18n.format("gui.cancel"), (p_213025_1_) -> {
                try {
                    Field f = getClass().getSuperclass().getDeclaredField("field_213027_d"); // cancel button consumer
                    f.setAccessible(true);
                    Method m = f.get(this).getClass().getDeclaredMethod("accept", boolean.class);
                    m.invoke(f.get(this), false);
                } catch (InvocationTargetException | NoSuchMethodException | IllegalAccessException | NoSuchFieldException e) {
                    e.printStackTrace();
                }
            }));

            field = getClass().getSuperclass().getDeclaredField("field_146302_g"); // ip edit field
            field.setAccessible(true);

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
                try {
                    Method method = getClass().getSuperclass().getDeclaredMethod("func_195168_i"); // something active method
                    method.setAccessible(true);
                    method.invoke(this);
                } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            });

            field.set(this, widget);
            this.children.add(widget);
            this.func_212928_a(widget);

            CheckboxButton button = new BetterCheckboxButton((widget.x + widget.getWidth() + 10), (widget.y),
                    20, 20, I18n.format("serveripobfuscator.gui.showip"), false, show -> {
                widget.show(show);
                if (show) {
                    try {
                        Field f = widget.getClass().getSuperclass().getDeclaredField("field_146216_j");
                        f.setAccessible(true);
                        f.set(widget, textFieldWidget.getText());
                    } catch (IllegalAccessException | NoSuchFieldException e) {
                        e.printStackTrace();
                    }
                } else {
                    StringBuilder builder = new StringBuilder();
                    for (int l = 0; l < widget.getText().length(); l++)
                        builder.append("*");
                    widget.setText(builder.toString());
                }
            });
            this.addButton(button);

            Method method = getClass().getSuperclass().getDeclaredMethod("func_195168_i");
            method.setAccessible(true);
            method.invoke(this);
        } catch (IllegalAccessException | NoSuchFieldException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    private void connectToServer() {
        try {
            Field field = getClass().getSuperclass().getDeclaredField("field_146301_f");
            field.setAccessible(true);
            Object o = field.get(this);

            Debug.printFields(o.getClass());

            field = o.getClass().getDeclaredField("field_78845_b");
            field.setAccessible(true);

            field.set(o, widget.getRealText());

            field = getClass().getSuperclass().getDeclaredField("field_213027_d");
            field.setAccessible(true);
            o = field.get(this);
            BooleanConsumer consumer = (BooleanConsumer) o;
            consumer.accept(true);
        } catch (IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean keyPressed(int p_keyPressed_1_, int p_keyPressed_2_, int p_keyPressed_3_) {
        try {
            Field field = getClass().getSuperclass().getDeclaredField("field_146302_g");
            field.setAccessible(true);
            if (this.getFocused() != field.get(this) || p_keyPressed_1_ != 257 && p_keyPressed_1_ != 335) {
                return super.keyPressed(p_keyPressed_1_, p_keyPressed_2_, p_keyPressed_3_);
            } else {
                this.connectToServer();
                return true;
            }
        } catch (IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
        }
        return true;
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
