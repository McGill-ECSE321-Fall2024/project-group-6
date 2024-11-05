package ca.mcgill.ecse321.gameshop.dto;

import jakarta.validation.constraints.NotBlank;

public class CommandRequestDto {
    //Command attributes
    @NotBlank(message="Command cost is required.")
    private float total;

    @SuppressWarnings("unused")
    private CommandRequestDto() {
    }

    public CommandRequestDto(float total) {
        this.total = total;
    }

    public float getTotal(){
        return total;
    }

    public void setTotal(float total){
        this.total = total;
    }
}