package org.mec.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.quarkus.runtime.annotations.RegisterForReflection;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Null;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
@Entity
@RegisterForReflection //para evitar que la GraalVm se lo garche en compilacion nativa
/**
 * Extender de PanacheEntity para proveer de primarykey (tipo long)
 * Patron active record necesita que los atributos sean publicos, entidades mas compactas
 */

public class ProductInventory extends PanacheEntity {
    @Null(groups = ValidationGroups.Put.class)
    @NotBlank(groups = ValidationGroups.Post.class)
    public String sku;
    public String category;

    @NotBlank(message = "Name is mandatory and should be provided")
    public String name;

    public int quantity;
    public String powerWatts;
    public String footprint;
    public BigDecimal manufacturingCost;
    public BigDecimal price;

    @Enumerated(EnumType.STRING)
    public ProductLine productLine;

    @Convert(converter = ConsumerTypeConverter.class)
    public List<ConsumerType> targetConsumer = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    public ProductAvailability productAvailability;

    @PositiveOrZero
    public int unitsAvailable;

    public ProductInventory() {
    }

    public static ProductInventory findBySku(String sku) {
        return find("sku", sku).firstResult();
    }

    /**
     * Solo recupera la cantidad de unidades disponibles segun sku provisto.
     * En este caso trabajar con proyecciones es mas eficiente porque no mapea toda la entidad
     * @param sku
     * @return cantidad de unidades disponibles (0 o +)
     */

    public static int findCurrentStock(String sku) {
        UnitsAvailable unitsAvailable = find("sku", sku).project(UnitsAvailable.class).firstResult();
        if(unitsAvailable == null){
            return 0;
        }
        return unitsAvailable.unitsAvailable;
    }
    /**
     * No se requieren getters y setters en ActiveRecord
     */

}
