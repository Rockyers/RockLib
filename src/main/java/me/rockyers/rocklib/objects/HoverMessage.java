package me.rockyers.rocklib.objects;

import me.rockyers.rocklib.utils.CC;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;

import java.util.Collection;
import java.util.List;

public class HoverMessage {
    TextComponent mainComponent;
    String msg;
    String hoverMsg;
    ClickEvent.Action clickEvent;
    String clickString;

    public HoverMessage(TextComponent component) {
        mainComponent = component;
    }

    public HoverMessage(String msg, String hoverMsg, ClickEvent.Action clickEvent, String clickString) {
        this.msg = CC.translate(msg);
        this.hoverMsg = CC.translate(hoverMsg);
        this.clickEvent = clickEvent;
        this.clickString = clickString;
    }

    public HoverMessage(String msg, String hoverMsg, String command) {
        this.msg = CC.translate(msg);
        this.hoverMsg = CC.translate(hoverMsg);
        this.clickEvent = ClickEvent.Action.RUN_COMMAND;
        this.clickString = command;
    }

    public HoverMessage(String msg, ClickEvent.Action clickEvent, String clickString) {
        this.msg = CC.translate(msg);
        this.clickEvent = clickEvent;
        this.clickString = clickString;
    }

    public HoverMessage(String msg, String hoverMsg) {
        this.msg = CC.translate(msg);
        this.hoverMsg = CC.translate(hoverMsg);
    }

    public HoverMessage(String msg) {
        this.msg = CC.translate(msg);
    }

    public TextComponent toTextComponent() {
        mainComponent = new TextComponent(msg);
        if (hoverMsg != null) {
            mainComponent.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(hoverMsg).create()));
        }
        if (clickEvent != null && clickString != null) {
            mainComponent.setClickEvent(new ClickEvent(clickEvent, clickString));
        }
        return mainComponent;
    }

    public HoverMessage setClickEvent(ClickEvent.Action clickEvent) {
        this.clickEvent = clickEvent;
        return this;
    }

    public ClickEvent.Action getClickEvent() {
        return clickEvent;
    }

    public HoverMessage setClickString(String clickString) {
        this.clickString = clickString;
        return this;
    }

    public String getClickString() {
        return clickString;
    }

    public HoverMessage setHoverMsg(String hoverMsg) {
        this.hoverMsg = hoverMsg;
        return this;
    }

    public String getHoverMsg() {
        return hoverMsg;
    }

    public HoverMessage setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public String getMsg() {
        return msg;
    }

    public HoverMessage setTextComponent(TextComponent mainComponent) {
        this.mainComponent = mainComponent;
        return this;
    }
}
