package mate.academy.carsharing.config;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.NullValueCheckStrategy;

@org.mapstruct.MapperConfig(componentModel = "spring",
        implementationPackage = "<PACKAGE_NAME>.impl",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public class MapperConfig {
}
