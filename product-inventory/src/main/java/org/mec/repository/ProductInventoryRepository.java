package org.mec.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import org.mec.model.ProductInventory;
import org.mec.model.UnitsAvailable;

import javax.enterprise.context.ApplicationScoped;

/**
 * Agregar @ApplicationScoped para tener uno por instancia de aplicacion, implementar la interfaz PanacheRepository<T>
 * Trabajar directamente sobre el repo, metodos no deben ser estaticos como en active record
 */
@ApplicationScoped
public class ProductInventoryRepository implements PanacheRepository<ProductInventory> {

     public ProductInventory findBySku(String sku) {
        return find("sku", sku).firstResult();
     }


    /**
     * Solo recupera la cantidad de unidades disponibles segun sku provisto.
     * En este caso trabajar con proyecciones es mas eficiente porque no mapea toda la entidad
     * @param sku
     * @return cantidad de unidades disponibles (0 o +)
     */


     public int findCurrentStock(String sku) {
        UnitsAvailable unitsAvailable = find("sku", sku).project(UnitsAvailable.class).firstResult();
        if(unitsAvailable == null){
        return 0;
        }
        return unitsAvailable.unitsAvailable;
         }


}
