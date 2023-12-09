package mate.academy.carsharing.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDate;
import lombok.Data;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "rentals")
@Data
@Where(clause = "is_deleted=false")
@SQLDelete(sql = "UPDATE rentals SET is_deleted=true WHERE id = ?")
public class Rental {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "rental_date", nullable = false)
    private LocalDate rentalDate;
    @Column(name = "return_date", nullable = false)
    private LocalDate returnDate;
    @Column(name = "actual_return_date")
    private LocalDate actualReturnDate;
    @Column(name = "car_id", nullable = false)
    private Long carId;
    @Column(name = "user_id", nullable = false)
    private Long userId;
    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted = false;
}
