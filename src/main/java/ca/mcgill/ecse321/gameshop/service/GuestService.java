package ca.mcgill.ecse321.gameshop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import ca.mcgill.ecse321.gameshop.exception.GameShopException;
import ca.mcgill.ecse321.gameshop.model.Guest;
import ca.mcgill.ecse321.gameshop.repository.GuestRepository;
import jakarta.transaction.Transactional;

@Service
public class GuestService {
    // Inject GuestRepository dependency to interact with the database
    @Autowired
    private GuestRepository guestRepo;

    // Create a new guest and save it to the repository
    @Transactional
    public Guest createGuest() {
        Guest g = new Guest();
        return guestRepo.save(g);
    }

    // Retrieve all guests from the repository
    public Iterable<Guest> getAllGuests() {
        return guestRepo.findAll();
    }

    // Find a guest by their ID
    public Guest findGuestByID(int id) {
        Guest g = guestRepo.findGuestByGuestId(id);

        // Exception if no guest is found
        if (g == null) {
			throw new GameShopException(HttpStatus.NOT_FOUND, String.format("Guest with ID " + id + " does not exist."));
        }

        return g;
    }

    // Delete a guest by their ID
    @Transactional
    public void deleteGuest(int id) {
        Guest g = guestRepo.findGuestByGuestId(id);

        if (g == null) {
			throw new GameShopException(HttpStatus.NOT_FOUND, String.format("Guest with ID " + id + " does not exist."));
        }

        guestRepo.delete(g);
    }
}
