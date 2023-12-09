package mate.academy.carsharing.specification;

import mate.academy.carsharing.model.Rental;
import org.springframework.data.jpa.domain.Specification;

public class RentalSpecifications {
    private RentalSpecifications() {

    }

    public static Specification<Rental> getSpecificationByIsActive(boolean isActive) {
        return (root, query, criteriaBuilder) -> {
            if (isActive) {
                return criteriaBuilder.isNull(root.get("actualReturnDate"));
            }
            return criteriaBuilder.isNotNull(root.get("actualReturnDate"));
        };
    }

    public static Specification<Rental> getSpecificationByUserId(Long userId) {
        return (root, query, criteriaBuilder) -> criteriaBuilder
                .equal(root.get("userId"), userId);
    }
}
