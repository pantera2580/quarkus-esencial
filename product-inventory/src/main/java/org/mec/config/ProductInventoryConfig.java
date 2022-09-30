package org.mec.config;

import io.smallrye.config.ConfigMapping;
import io.smallrye.config.WithDefault;
import io.smallrye.config.WithName;

import javax.validation.constraints.NotNull;

/**
 * Interfaz de configuraciones
 */
@ConfigMapping(prefix = "org.mec") //para no escribir toda la propiedad
public interface ProductInventoryConfig {
    @WithName("greeting-message") //setea el nombre de la propiedad
    String message();
    @WithName("full-catalog")
    @WithDefault("true")
    @NotNull
    boolean retrieveFullCatalog();
}
