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
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.util.SharedConstants;

import javax.annotation.Nullable;
import java.util.function.Predicate;

public class NewGuiPasswordField extends TextFieldWidget {

    private TextFieldWidget textField;
    private boolean show;

    public NewGuiPasswordField(FontRenderer p_i51137_1_, int p_i51137_2_, int p_i51137_3_, int p_i51137_4_, int p_i51137_5_, String p_i51137_6_, TextFieldWidget textField) {
        super(p_i51137_1_, p_i51137_2_, p_i51137_3_, p_i51137_4_, p_i51137_5_, p_i51137_6_);
        this.textField = textField;
    }

    public NewGuiPasswordField(FontRenderer p_i51138_1_, int p_i51138_2_, int p_i51138_3_, int p_i51138_4_, int p_i51138_5_, @Nullable TextFieldWidget p_i51138_6_, String p_i51138_7_, TextFieldWidget textField) {
        super(p_i51138_1_, p_i51138_2_, p_i51138_3_, p_i51138_4_, p_i51138_5_, p_i51138_6_, p_i51138_7_);
        this.textField = textField;
    }

    @Override
    public void setText(String p_146180_1_) {

        int maxStringLength = (int) ReflectionHelper.getSilent(this, "field_146217_k", true);
        Predicate<String> validator = (Predicate<String>) ReflectionHelper.getSilent(this, "field_175209_y", true);
        String text;

        if (validator.test(p_146180_1_)) {
            if (!show) {
                StringBuilder builder = new StringBuilder();
                for (int l = 0; l < p_146180_1_.length(); l++)
                    builder.append("*");
                p_146180_1_ = builder.toString();
            }


            if (p_146180_1_.length() > maxStringLength) {
                text = p_146180_1_.substring(0, maxStringLength);
            } else {
                text = p_146180_1_;
            }
            ReflectionHelper.setSilent(this, "field_146216_j", text, true);

            this.setCursorPositionEnd();
            this.setSelectionPos(this.getCursorPosition());
        }
    }

    @Override
    public void writeText(String p_146191_1_) {
        int selectionEnd = (int) ReflectionHelper.getSilent(this, "field_146223_s", true);
        int maxStringLength = (int) ReflectionHelper.getSilent(this, "field_146217_k", true);
        Predicate<String> validator = (Predicate<String>) ReflectionHelper.getSilent(this, "field_175209_y", true);

        String lvt_2_1_ = "";
        String lvt_3_1_ = SharedConstants.filterAllowedCharacters(p_146191_1_);
        int lvt_4_1_ = Math.min(this.getCursorPosition(), selectionEnd);
        int lvt_5_1_ = Math.max(this.getCursorPosition(), selectionEnd);
        int lvt_6_1_ = maxStringLength - this.getText().length() - (lvt_4_1_ - lvt_5_1_);

        if (!this.getText().isEmpty()) {
            lvt_2_1_ = lvt_2_1_ + this.getText().substring(0, lvt_4_1_);
        }

        int lvt_7_2_;
        if (lvt_6_1_ < lvt_3_1_.length()) {
            lvt_2_1_ = lvt_2_1_ + lvt_3_1_.substring(0, lvt_6_1_);
            lvt_7_2_ = lvt_6_1_;
        } else {
            lvt_2_1_ = lvt_2_1_ + lvt_3_1_;
            lvt_7_2_ = lvt_3_1_.length();
        }

        if (!this.getText().isEmpty() && lvt_5_1_ < this.getText().length()) {
            lvt_2_1_ = lvt_2_1_ + this.getText().substring(lvt_5_1_);
        }

        if (validator.test(lvt_2_1_)) {
            this.setText(lvt_2_1_);
            this.func_212422_f(lvt_4_1_ + lvt_7_2_);
            this.setSelectionPos(this.getCursorPosition());
        }
    }

    @Override
    public boolean keyPressed(int p_keyPressed_1_, int p_keyPressed_2_, int p_keyPressed_3_) {
        textField.keyPressed(p_keyPressed_1_, p_keyPressed_2_, p_keyPressed_3_);
        if (!this.func_212955_f()) {
            return false;
        } else {
            ReflectionHelper.setSilent(this, "field_212956_h", Screen.hasShiftDown(), true);

            if (Screen.isSelectAll(p_keyPressed_1_)) {
                this.setCursorPositionEnd();
                this.setSelectionPos(0);
                return true;
            } else if (Screen.isCopy(p_keyPressed_1_)) {
                Minecraft.getInstance().keyboardListener.setClipboardString(this.getSelectedText());
                return true;
            } else if (Screen.isPaste(p_keyPressed_1_)) {
                if ((boolean) ReflectionHelper.getSilent(this, "field_146226_p", true)) {
                    this.writeText(Minecraft.getInstance().keyboardListener.getClipboardString());
                    this.textField.writeText(Minecraft.getInstance().keyboardListener.getClipboardString());
                }

                return true;
            } else if (Screen.isCut(p_keyPressed_1_)) {
                Minecraft.getInstance().keyboardListener.setClipboardString(this.getSelectedText());

                if ((boolean) ReflectionHelper.getSilent(this, "field_146226_p", true)) {
                    this.writeText("");
                }

                return true;
            } else {
                switch (p_keyPressed_1_) {
                    case 259:
                    case 261:
                        if ((boolean) ReflectionHelper.getSilent(this, "field_146226_p", true)) {
                            ReflectionHelper.setSilent(this, "field_212956_h", false, true);
                            this.delete(-1);
                            ReflectionHelper.setSilent(this, "field_212956_h", Screen.hasShiftDown(), true);
                        }
                        return true;
                    case 260:
                    case 264:
                    case 265:
                    case 266:
                    case 267:
                    default:
                        return false;
                    case 262:
                        if (Screen.hasControlDown()) {
                            this.setCursorPosition(this.getNthWordFromCursor(1));
                        } else {
                            this.moveCursorBy(1);
                        }
                        return true;
                    case 263:
                        if (Screen.hasControlDown()) {
                            this.setCursorPosition(this.getNthWordFromCursor(-1));
                        } else {
                            this.moveCursorBy(-1);
                        }
                        return true;
                    case 268:
                        this.setCursorPositionZero();
                        return true;
                    case 269:
                        this.setCursorPositionEnd();
                        return true;
                }
            }
        }
    }

    @Override
    public boolean charTyped(char p_charTyped_1_, int p_charTyped_2_) {
        textField.charTyped(p_charTyped_1_, p_charTyped_2_);

        if (!this.func_212955_f()) {
            return false;
        }

        if (SharedConstants.isAllowedCharacter(p_charTyped_1_)) {
            if ((boolean) ReflectionHelper.getSilent(this, "field_146226_p", true)) {
                this.writeText(Character.toString(p_charTyped_1_));
            }
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void deleteFromCursor(int num) {
        super.deleteFromCursor(num);
        textField.deleteFromCursor(num);
    }

    @Override
    public void deleteWords(int num) {
        super.deleteWords(num);
        textField.deleteWords(num);
    }

    private void delete(int p_212950_1_) {
        if (Screen.hasControlDown()) {
            this.deleteWords(p_212950_1_);
        } else {
            this.deleteFromCursor(p_212950_1_);
        }

    }

    public void show(boolean b) {
        this.show = b;
    }

    public String getRealText() {
        return textField.getText();
    }
}
