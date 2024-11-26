package ca.mcgill.ecse321.gameshop.service;

import ca.mcgill.ecse321.gameshop.exception.GameShopException;
import ca.mcgill.ecse321.gameshop.model.Guest;
import ca.mcgill.ecse321.gameshop.repository.GuestRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
/**
 * @author Mario
 */
@SpringBootTest
@MockitoSettings(strictness = Strictness.STRICT_STUBS)
public class GuestServiceTests {
    @Mock
    private GuestRepository repo;
    @InjectMocks
    private GuestService service;

    private static final int ID = 10;

    @Test
    public void testCreateValidGuest() {
        // Arrange
        // Return object when repo.save() is called
        when(repo.save(any(Guest.class))).thenAnswer((InvocationOnMock iom) -> iom.getArgument(0));

        // Act
        Guest createdGuest = service.createGuest();

        // Assert
        assertNotNull(createdGuest);

        // Check that save() was called exactly once with given argument
        verify(repo, times(1)).save(createdGuest);
    }

    @Test
    public void testGetGuestByValidId() {
        // Arrange
        when(repo.findGuestByGuestId(ID)).thenReturn(new Guest());

        // Act
        Guest g = service.findGuestByID(ID);

        // Assert
        assertNotNull(g);
    }

    @Test
    public void testGetGuestByInvalidId() {
        // Arrange
        // Act
        // Assert
		GameShopException ex = assertThrows(GameShopException.class,
				() -> service.findGuestByID(ID));
		assertEquals(HttpStatus.NOT_FOUND, ex.getStatus());
		assertEquals("Guest with ID " + ID + " does not exist.", ex.getMessage());
    }

    @Test
    public void testGetAllGuests() {
        // Arrange
        Guest g1 = new Guest();
        Guest g2 = new Guest();
        List<Guest> guestList = Arrays.asList(g1, g2);
        when(repo.findAll()).thenReturn(guestList);

        // Act
        Iterable<Guest> guestIterable = service.getAllGuests();
        List<Guest> guests = new ArrayList<>();
        guestIterable.forEach(guests::add);

        // Assert
        assertNotNull(guests);
        assertEquals(2, guests.size());
    }

    @Test
    public void testDeleteGuestByValidId() {
        // Arrange
        Guest existingGuest = new Guest();
    
        when(repo.findGuestByGuestId(ID)).thenReturn(existingGuest);

        // Act
        service.deleteGuest(ID);

        // Assert
        verify(repo, times(1)).delete(existingGuest);
    }

    @Test
    public void testDeleteGuestByInvalidId() {
        // Arrange
        // Act
        // Assert
		GameShopException ex = assertThrows(GameShopException.class,
				() -> service.deleteGuest(ID));
		assertEquals(HttpStatus.NOT_FOUND, ex.getStatus());
		assertEquals("Guest with ID " + ID + " does not exist.", ex.getMessage());
    }
}
