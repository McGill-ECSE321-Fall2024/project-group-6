package ca.mcgill.ecse321.gameshop.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.*;

import ca.mcgill.ecse321.gameshop.repository.RoleRepository;
import ca.mcgill.ecse321.gameshop.service.RoleService;
import ca.mcgill.ecse321.gameshop.model.*;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import ca.mcgill.ecse321.gameshop.repository.GameRepositoryRepository;
import org.springframework.web.SpringServletContainerInitializer;

import java.util.ArrayList;
import java.util.Optional;
import java.util.List;
import java.util.Arrays;

@SpringBootTest
public class RoleUnitTest {
    @Mock
    private RoleRepository repo;
    @InjectMocks
    private RoleService service;

    @SuppressWarnings("null")
    @Test
    public void testCreateValidRole() {
        //Arrange
        Person person = new Person("test", "test@email", "test", "12345");

        Role role = new Role(person);

        //Act
        Role createdRole = service.createRole(person);

        //Assert
        assertNotNull(createdRole);
        assertEquals(person, createdRole.getPerson());
        verify(repo, times(1)).save(role);
    }

    @Test
    public void testReadRoleByValidId() {
        //Arrange
        int id = 3;
        Person person = new Person("test", "test@email", "test", "12345");

        Role role = new Role(person);

        when(repo.findRoleByRoleId(id)).thenReturn(role);

        //Act
        Role roleToGet = service.getRoleById(id);

        //Assert
        assertNotNull(roleToGet);
        assertEquals(role.getPerson(), roleToGet.getPerson());
    }

    @Test
    public void testReadRoleByInvalidId() {
        //Arrange
        int id = 3;
        when(repo.findRoleByRoleId(id)).thenReturn(null);

        //Act
        //Assert
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () ->service.getRoleById(id));
        assertEquals("There is no role with ID " + id + ".", e.getMessage());
        verify(repo, times(1)).findRoleByRoleId(id);
    }

    @Test
    public void testUpdateRoleValidId() {
        // Arrange
        int id = 3;
        Person person = new Person("test", "test@email", "test", "12345");

        Role role = new Role(person);
        Role roleUpdated = new Role(new Person("newTest", "test@email", "test", "12345"));

        when(repo.findRoleByRoleId(id)).thenReturn(Optional.of(role));
        when(repo.save(any(Role.class))).thenReturn(roleUpdated);

        // Act
        Role updated = service.updateRole(id, new Person("newTest", "test@email", "test", "12345"));

        // Assert
        assertNotNull(updated);
        assertEquals(updated.getPerson(), roleUpdated.getPerson());
        verify(repo, times(1)).findRoleByRoleId(id);
        verify(repo, times(1)).save(any(Role.class));
    }

    @Test
    public void testUpdateRoleInvalidId() {
        // Arrange
        int invalidId = 99;
        Person person = new Person("test", "test@email", "test", "12345");

        // Mock findById to return an empty Optional, simulating a non-existent payment
        when(repo.findRoleByRoleId(invalidId)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                service.updateRole(invalidId, person);
        assertEquals("Role not found", exception.getMessage());

        verify(repo, times(1)).findRoleByRoleId(invalidId);
        verify(repo, times(0)).save(any(Role.class)); // Ensure save is not called
    }

    @Test
    public void testDeleteRole() {
        // Arrange
        int id= 1;
        doNothing().when(repo).deleteRole(id);

        // Act
        service.deleteRole(id);

        // Assert
        verify(repo, times(1)).deleteRole(id);
    }

    @Test
    public void testGetAllRoles() {
        // Arrange
        Person person1 = new Person("test", "test@email", "test", "12345");
        Person person2 = new Person("uservame", "username@email", "username", "6789");

        Role role1 = new Role(person1);
        Role role2 = new Role(person2);
        List<Role> roles = Arrays.asList(role2, role2);
        when(repo.findAll()).thenReturn(roles);

        // Act
        List<Role> allRoles = service.getAllRoles();

        // Assert
        assertNotNull(allRoles);
        assertEquals(2, allRoles.size());
        assertEquals(role1, allRoles.get(0));
        assertEquals(role2, allRoles.get(1));
        verify(repo, times(1)).findAll();
    }
}
