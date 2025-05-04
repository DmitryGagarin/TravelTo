package com.travel.to.travel_to.entity.attraction_feature.menu.menu_element;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.travel.to.travel_to.entity.attraction_feature.menu.menu.TextMenu;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.io.Serial;
import java.io.Serializable;

@Entity
@Table(name = "text_menu_element")
public class TextMenuElement extends MenuElement implements Serializable {

    @Serial
    private static final long serialVersionUID = 4451551539831755615L;

    private String dishName;
    private String dishDescription;
    private String dishPrice;
    private byte[] dishImage;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "menu_id")
    private TextMenu menu;

    public String getDishName() {
        return dishName;
    }

    public TextMenuElement setDishName(String dishName) {
        this.dishName = dishName;
        return this;
    }

    public String getDishDescription() {
        return dishDescription;
    }

    public TextMenuElement setDishDescription(String dishDescription) {
        this.dishDescription = dishDescription;
        return this;
    }

    public String getDishPrice() {
        return dishPrice;
    }

    public TextMenuElement setDishPrice(String dishPrice) {
        this.dishPrice = dishPrice;
        return this;
    }

    public byte[] getDishImage() {
        return dishImage;
    }

    public TextMenuElement setDishImage(byte[] dishImage) {
        this.dishImage = dishImage;
        return this;
    }

    public TextMenu getMenu() {
        return menu;
    }

    public TextMenuElement setMenu(TextMenu menu) {
        this.menu = menu;
        return this;
    }
}
