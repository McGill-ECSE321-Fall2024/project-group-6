package ca.mcgill.ecse321.gameshop.repository;


import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.gameshop.model.Guest;

public interface GuestRepository extends CrudRepository<Guest, Integer> {
    public Guest findGuestByGuestId(int guestId);
}
