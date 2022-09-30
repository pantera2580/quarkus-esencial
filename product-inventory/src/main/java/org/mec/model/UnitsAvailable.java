package org.mec.model;

import io.quarkus.runtime.annotations.RegisterForReflection;

/**
 * Clase auxiliar para realizar proyeccion en base de datos
 * Solo mapea el campo unitsAvailable de la entidad ProductInventory
 * @RegisterForReflection evita que en compilacion nativa se elimine
 */
@RegisterForReflection
public class UnitsAvailable {
    public final int unitsAvailable;

    public UnitsAvailable(int unitsAvailable) {
        this.unitsAvailable = unitsAvailable;
    }
}
