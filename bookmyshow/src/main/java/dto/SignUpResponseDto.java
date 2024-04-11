package dto;

import lombok.Getter;
import lombok.Setter;
import models.ResponseStatus;

@Getter
@Setter
public class SignUpResponseDto {
    public Long userId;
    private ResponseStatus responseStatus;
}
