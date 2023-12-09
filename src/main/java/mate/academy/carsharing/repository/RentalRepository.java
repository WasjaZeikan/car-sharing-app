package mate.academy.carsharing.repository;

import mate.academy.carsharing.model.Rental;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface RentalRepository extends JpaRepository<Rental,Long>,
        JpaSpecificationExecutor<Rental> {
}
