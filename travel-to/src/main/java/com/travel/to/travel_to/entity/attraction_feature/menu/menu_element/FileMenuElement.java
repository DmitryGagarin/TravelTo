package com.travel.to.travel_to.entity.attraction_feature.menu.menu_element;

import com.travel.to.travel_to.entity.attraction_feature.menu.menu.FileMenu;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.io.Serial;
import java.io.Serializable;

@Entity
@Table(name = "file_menu_element")
public class FileMenuElement extends MenuElement implements Serializable {

    @Serial
    private static final long serialVersionUID = -1538265829509300925L;

    private byte[] file;

    @ManyToOne
    @JoinColumn(name = "menu_id")
    private FileMenu menu;


    public byte[] getFile() {
        return file;
    }

    public FileMenuElement setFile(byte[] files) {
        this.file = files;
        return this;
    }
}
