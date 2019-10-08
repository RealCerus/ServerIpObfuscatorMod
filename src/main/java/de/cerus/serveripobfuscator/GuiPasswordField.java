package de.cerus.serveripobfuscator;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.util.SharedConstants;

import javax.annotation.Nullable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.function.Predicate;

@Deprecated
public class GuiPasswordField extends TextFieldWidget {

    private String realText = "";

    public GuiPasswordField(FontRenderer p_i51137_1_, int p_i51137_2_, int p_i51137_3_, int p_i51137_4_, int p_i51137_5_, String p_i51137_6_) {
        super(p_i51137_1_, p_i51137_2_, p_i51137_3_, p_i51137_4_, p_i51137_5_, p_i51137_6_);
    }

    public GuiPasswordField(FontRenderer p_i51138_1_, int p_i51138_2_, int p_i51138_3_, int p_i51138_4_, int p_i51138_5_, @Nullable TextFieldWidget p_i51138_6_, String p_i51138_7_) {
        super(p_i51138_1_, p_i51138_2_, p_i51138_3_, p_i51138_4_, p_i51138_5_, p_i51138_6_, p_i51138_7_);
    }

    @Override
    public void setText(String p_146180_1_) {
        try {
            Field field = getClass().getSuperclass().getDeclaredField("field_146217_k");
            field.setAccessible(true);
            int maxStringLength = (int) field.get(this);
            field = getClass().getSuperclass().getDeclaredField("field_175209_y");
            field.setAccessible(true);
            Predicate<String> validator = (Predicate<String>) field.get(this);
            field = getClass().getSuperclass().getDeclaredField("field_146216_j");
            field.setAccessible(true);
            String text = (String) field.get(this);

            if (validator.test(p_146180_1_)) {

                realText += p_146180_1_.toCharArray()[p_146180_1_.length()];
                StringBuilder builder = new StringBuilder();
                for(int l = 0; l < p_146180_1_.length(); l++)
                    builder.append("*");
                p_146180_1_ = builder.toString();



                if (p_146180_1_.length() > maxStringLength) {
                    text = p_146180_1_.substring(0, maxStringLength);
                } else {
                    text = p_146180_1_;
                }
                field.set(this, text);

                this.setCursorPositionEnd();
                this.setSelectionPos(this.getCursorPosition());
            }
        } catch (IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void writeText(String p_146191_1_) {
        try {
            Field field = getClass().getSuperclass().getDeclaredField("field_146223_s");
            field.setAccessible(true);
            int selectionEnd = (int) field.get(this);

            field = getClass().getSuperclass().getDeclaredField("field_146217_k");
            field.setAccessible(true);
            int maxStringLength = (int) field.get(this);

            field = getClass().getSuperclass().getDeclaredField("field_175209_y");
            field.setAccessible(true);
            Predicate<String> validator = (Predicate<String>) field.get(this);

            String lvt_2_1_ = "";
            String lvt_3_1_ = SharedConstants.filterAllowedCharacters(p_146191_1_);
            int lvt_4_1_ = this.getCursorPosition() < selectionEnd ? this.getCursorPosition() : selectionEnd;
            int lvt_5_1_ = this.getCursorPosition() < selectionEnd ? selectionEnd : this.getCursorPosition();
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
        } catch (IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean keyPressed(int p_keyPressed_1_, int p_keyPressed_2_, int p_keyPressed_3_) {

        try {
            if (!this.func_212955_f()) {
                return false;
            } else {
                Field field = getClass().getSuperclass().getDeclaredField("field_212956_h");
                field.setAccessible(true);
                field.set(this, Screen.hasShiftDown());

                if (Screen.isSelectAll(p_keyPressed_1_)) {
                    this.setCursorPositionEnd();
                    this.setSelectionPos(0);
                    return true;
                } else if (Screen.isCopy(p_keyPressed_1_)) {
                    Minecraft.getInstance().keyboardListener.setClipboardString(this.getSelectedText());
                    return true;
                } else if (Screen.isPaste(p_keyPressed_1_)) {
                    field = getClass().getSuperclass().getDeclaredField("field_146226_p");
                    field.setAccessible(true);

                    if ((boolean)field.get(this)) {
                        this.writeText(Minecraft.getInstance().keyboardListener.getClipboardString());
                    }

                    return true;
                } else if (Screen.isCut(p_keyPressed_1_)) {
                    Minecraft.getInstance().keyboardListener.setClipboardString(this.getSelectedText());
                    field = getClass().getSuperclass().getDeclaredField("field_146226_p");
                    field.setAccessible(true);

                    if ((boolean)field.get(this)) {
                        this.writeText("");
                    }

                    return true;
                } else {
                    switch(p_keyPressed_1_) {
                        case 259:
                            field = getClass().getSuperclass().getDeclaredField("field_146226_p");
                            field.setAccessible(true);
                            if ((boolean)field.get(this)) {
                                field = getClass().getSuperclass().getDeclaredField("field_212956_h");
                                field.setAccessible(true);
                                field.set(this, false);

                                Method method = getClass().getSuperclass().getDeclaredMethod("func_212950_m", Integer.class);
                                method.setAccessible(true);
                                method.invoke(this, -1);

                                field = getClass().getSuperclass().getDeclaredField("field_212956_h");
                                field.setAccessible(true);
                                field.set(this, Screen.hasShiftDown());
                            }
                            return true;
                        case 260:
                        case 264:
                        case 265:
                        case 266:
                        case 267:
                        default:
                            return false;
                        case 261:
                            field = getClass().getSuperclass().getDeclaredField("field_146226_p");
                            field.setAccessible(true);

                            if ((boolean)field.get(this)) {
                                field = getClass().getSuperclass().getDeclaredField("field_212956_h");
                                field.setAccessible(true);
                                field.set(this, false);

                                Method method = getClass().getSuperclass().getMethod("delete", Integer.class);
                                method.setAccessible(true);
                                method.invoke(this, -1);

                                field = getClass().getSuperclass().getDeclaredField("field_212956_h");
                                field.setAccessible(true);
                                field.set(this, Screen.hasShiftDown());
                            }
                            return true;
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
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | NoSuchFieldException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean charTyped(char p_charTyped_1_, int p_charTyped_2_) {
        if (!this.func_212955_f()) {
            return false;
        } else if (SharedConstants.isAllowedCharacter(p_charTyped_1_)) {
            try {
                Field field = getClass().getSuperclass().getDeclaredField("field_146226_p");
                field.setAccessible(true);

                if ((boolean)field.get(this)) {
                    this.writeText(Character.toString(p_charTyped_1_));
                }
            } catch (IllegalAccessException | NoSuchFieldException e) {
                e.printStackTrace();
            }

            return true;
        } else {
            return false;
        }
    }

    public String getRealText() {
        return realText;
    }
}
