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

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.TextFieldWidget;

import javax.annotation.Nullable;

public class BetterTextFieldWidget extends TextFieldWidget {

    private NewGuiPasswordField field;

    public BetterTextFieldWidget(FontRenderer fontIn, int p_i51137_2_, int p_i51137_3_, int p_i51137_4_, int p_i51137_5_, String msg) {
        super(fontIn, p_i51137_2_, p_i51137_3_, p_i51137_4_, p_i51137_5_, msg);
    }

    public BetterTextFieldWidget(FontRenderer fontIn, int xIn, int yIn, int widthIn, int heightIn, @Nullable TextFieldWidget p_i51138_6_, String msg) {
        super(fontIn, xIn, yIn, widthIn, heightIn, p_i51138_6_, msg);
    }

    @Override
    public boolean keyPressed(int p_keyPressed_1_, int p_keyPressed_2_, int p_keyPressed_3_) {
        if (Screen.isPaste(p_keyPressed_1_)) {
            return true;
        }
        return super.keyPressed(p_keyPressed_1_, p_keyPressed_2_, p_keyPressed_3_);
    }

    @Override
    public boolean func_212955_f() {
        return field.func_212955_f();
    }

    public void setField(NewGuiPasswordField field) {
        this.field = field;
    }
}
