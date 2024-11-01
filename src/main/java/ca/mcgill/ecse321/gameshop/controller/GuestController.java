package ca.mcgill.ecse321.gameshop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321.gameshop.dto.GuestDto;
import ca.mcgill.ecse321.gameshop.model.Guest;
import ca.mcgill.ecse321.gameshop.service.GuestService;


@RestController
public class GuestController {
    @Autowired
    private GuestService guestService;

    @PostMapping("/guest")
    public GuestDto createGuest(@RequestBody GuestDto guest) {
        Guest g = guestService.createGuest();
        return new GuestDto(g);
    }

    @GetMapping("/guest/{id}")
    public GuestDto findGuestByID(@PathVariable int id) {
        Guest g = guestService.findGuestByID(id);
        return new GuestDto(g);
    }
    
    @GetMapping("/guest")
    public Iterable<Guest> getAllGuests() {
        return guestService.getAllGuests();
    }

    @PutMapping("/guest/{id}")
    public GuestDto updateGuest(@PathVariable int id, @RequestBody GuestDto guest) {
        Guest g = guestService.updateGuest(id);
        return new GuestDto(g);
    }

    @DeleteMapping("/guest/{id}")
    public void deleteGuest(@PathVariable int id, @RequestBody GuestDto guest) {
        guestService.deleteGuest(id);
    }
}
