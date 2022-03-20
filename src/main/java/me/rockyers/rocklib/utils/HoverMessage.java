package me.rockyers.rocklib.utils;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;

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

    public TextComponent getTextComponent() {
        mainComponent = new TextComponent(msg);
        if (hoverMsg != null) {
            mainComponent.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(hoverMsg).create()));
        }
        if (clickEvent != null && clickString != null) {
            mainComponent.setClickEvent(new ClickEvent(clickEvent, clickString));
        }
        return mainComponent;
    }

    public void setClickEvent(ClickEvent.Action clickEvent) {
        this.clickEvent = clickEvent;
    }

    public ClickEvent.Action getClickEvent() {
        return clickEvent;
    }

    public void setClickString(String clickString) {
        this.clickString = clickString;
    }

    public String getClickString() {
        return clickString;
    }

    public void setHoverMsg(String hoverMsg) {
        this.hoverMsg = hoverMsg;
    }

    public String getHoverMsg() {
        return hoverMsg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setTextComponent(TextComponent mainComponent) {
        this.mainComponent = mainComponent;
    }
}
