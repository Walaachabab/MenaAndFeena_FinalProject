package org.example.menaandfeena_finalproject.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@NoArgsConstructor
@Setter @Getter
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // PURCHASE, RENT
    @Column(columnDefinition = "varchar(10) not null")
    @NotBlank(message = "Type cannot be blank")
    @Pattern(regexp = "PURCHASE|RENT", message = "Type must be PURCHASE or RENT")
    private String type;

    // PENDING, PAID, ACTIVE, COMPLETED, CANCELLED
    @Column(columnDefinition = "varchar(10) not null")
    @NotBlank(message = "Status cannot be blank")
    @Pattern(regexp = "PENDING|PAID|ACTIVE|COMPLETED|CANCELLED", message = "Status must be PENDING, PAID, ACTIVE, COMPLETED or CANCELLED")
    private String status;

    @Column(columnDefinition = "int not null")
    @NotNull(message = "Total amount cannot be null")
    @Positive(message = "Total amount must be positive")
    private Integer totalAmount;

    @Column(columnDefinition = "date")
    private LocalDate startDate;

    @Column(columnDefinition = "date")
    private LocalDate endDate;

    @ManyToOne
    @JoinColumn(name = "marketplace_item_id", referencedColumnName = "id")
    private MarketPlaceItem marketPlaceItem;


    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;


    @OneToOne(mappedBy = "orders", cascade = CascadeType.ALL)
    private Payment payment;

    @OneToOne(mappedBy = "orders", cascade = CascadeType.ALL)
    private Insurance insurance;
}
