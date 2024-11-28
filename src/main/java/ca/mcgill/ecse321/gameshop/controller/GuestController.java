package ca.mcgill.ecse321.gameshop.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import ca.mcgill.ecse321.gameshop.dto.GuestListDto;
import ca.mcgill.ecse321.gameshop.dto.GuestRequestDto;
import ca.mcgill.ecse321.gameshop.dto.GuestResponseDto;
import ca.mcgill.ecse321.gameshop.model.Guest;
import ca.mcgill.ecse321.gameshop.service.GuestService;


@RestController
public class GuestController {
    @Autowired
    private GuestService guestService;

    @CrossOrigin(origins = "http://localhost:8087")
    @PostMapping("/guest")
    public GuestResponseDto createGuest(@RequestBody GuestRequestDto guestRequest) {
        Guest g = guestService.createGuest();
        return new GuestResponseDto(g);
    }
    @CrossOrigin(origins = "http://localhost:8087")
    @GetMapping("/guest/{id}")
    public GuestResponseDto findGuestByID(@PathVariable int id) {
        Guest g = guestService.findGuestByID(id);
        return new GuestResponseDto(g);
    }
    @CrossOrigin(origins = "http://localhost:8087")
    @GetMapping("/guest")
    public GuestListDto getAllGuests() {
        List<GuestResponseDto> guests = new ArrayList<>();

        for (Guest g: guestService.getAllGuests()) {
            guests.add(new GuestResponseDto(g));
        }
        
        return new GuestListDto(guests);
    }
    @CrossOrigin(origins = "http://localhost:8087")
    @DeleteMapping("/guest/{id}")
    public void deleteGuest(@PathVariable int id) {
        guestService.deleteGuest(id);
    }
}
