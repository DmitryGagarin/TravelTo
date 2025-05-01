package com.travel.to.travel_to.entity.attraction_feature.menu.menu;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.travel.to.travel_to.entity.attraction_feature.menu.menu_element.TextMenuElement;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "text_menu")
public class TextMenu extends Menu implements Serializable {

    @Serial
    private static final long serialVersionUID = 7230751146591221829L;

    @JsonBackReference
    @OneToMany(mappedBy = "menu", cascade = CascadeType.ALL)
    private List<TextMenuElement> elements;

    public List<TextMenuElement> getElements() {
        return elements;
    }

    public TextMenu setElements(List<TextMenuElement> elements) {
        this.elements = elements;
        return this;
    }
}
