package com.travel.to.travel_to.entity.attraction_feature.menu.menu;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.travel.to.travel_to.entity.attraction_feature.menu.menu_element.FileMenuElement;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "file_menu")
public class FileMenu extends Menu implements Serializable {

    @Serial
    private static final long serialVersionUID = 3782563102469735487L;

    @OneToMany(mappedBy = "menu", cascade = CascadeType.ALL)
    private List<FileMenuElement> elements;

    public List<FileMenuElement> getElements() {
        return elements;
    }

    public FileMenu setElements(List<FileMenuElement> elements) {
        this.elements = elements;
        return this;
    }
}
