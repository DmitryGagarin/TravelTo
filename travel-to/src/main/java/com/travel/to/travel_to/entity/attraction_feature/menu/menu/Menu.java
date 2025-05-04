package com.travel.to.travel_to.entity.attraction_feature.menu.menu;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.travel.to.travel_to.entity.common.UuidAbleEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;

@Entity
@Inheritance(strategy = InheritanceType.JOINED) // or SINGLE_TABLE or TABLE_PER_CLASS
@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.PROPERTY,
    property = "type"
)
@JsonSubTypes({
    @JsonSubTypes.Type(value = TextMenu.class, name = "text"),
    @JsonSubTypes.Type(value = FileMenu.class, name = "file")
})
public abstract class Menu extends UuidAbleEntity {
    private Long attractionId;

    public Long getAttractionId() {
        return attractionId;
    }

    public Menu setAttractionId(Long attractionId) {
        this.attractionId = attractionId;
        return this;
    }
}
