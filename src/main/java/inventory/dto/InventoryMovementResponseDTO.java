package inventory.dto;

import java.time.LocalDateTime;

public class InventoryMovementResponseDTO {
    private Long id;
    private MovementTypeEnum movementType;
    private int quantity;
    private LocalDateTime createdAt;
    private String description;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public MovementTypeEnum getMovementTypeEnum() {
        return movementType;
    }

    public void setMovementTypeEnum(MovementTypeEnum movementTypeEnum) {
        this.movementType = movementTypeEnum;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
