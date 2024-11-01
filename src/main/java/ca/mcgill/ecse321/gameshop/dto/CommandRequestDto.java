package ca.mcgill.ecse321.gameshop.dto;

public class CommandRequestDto {
    private int id;
    private String commandDate;
    private float total;

    public int getId(){
        return id;
    }
    public String getCommandDate(){
        return commandDate;
    }
    public float getTotal(){
        return total;
    }
}
