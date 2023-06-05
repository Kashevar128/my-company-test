package org.example.application.dto;

import lombok.Builder;
import lombok.Data;
@Builder
@Data
public class PositionDto {
    private int id;
    private String positionName;
}
