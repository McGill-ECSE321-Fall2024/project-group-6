package ca.mcgill.ecse321.gameshop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.mcgill.ecse321.gameshop.model.Game;
import ca.mcgill.ecse321.gameshop.model.Guest;
import ca.mcgill.ecse321.gameshop.repository.GameRepository;
import ca.mcgill.ecse321.gameshop.repository.GuestRepository;
import jakarta.transaction.Transactional;

@Service
public class GuestService {
    // Inject GuestRepository dependency to interact with the database
    @Autowired
    private GuestRepository guestRepo;
    @Autowired
    private GameRepository gameRepo;

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

    public Iterable<Game> getAllGames() {
        return gameRepo.findAll();
    }

    // Find a guest by their ID
    public Guest findGuestByID(int id) {
        Guest g = guestRepo.findGuestByGuestId(id);

        // Exception if no guest is found
        if (g == null) {
            throw new RuntimeException("Guest with ID " + id + "does not exist.");
        }

        return g;
    }

    // Update an existing guest's details by their ID
    @Transactional
    public Guest updateGuest(int id) {
        Guest g = guestRepo.findGuestByGuestId(id);
        return guestRepo.save(g);
    }

    // Delete a guest by their ID
    @Transactional
    public void deleteGuest(int id) {
        Guest g = guestRepo.findGuestByGuestId(id);

        // Exception if no guest is found
        if (g == null) {
            throw new RuntimeException("Guest with ID " + id + "does not exist.");
        }

        guestRepo.delete(g);
    }
}
