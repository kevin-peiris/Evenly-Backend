package edu.icet;

import edu.icet.dto.User;
import edu.icet.entity.UserEntity;
import edu.icet.repository.UserRepository;
import edu.icet.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl service;

    @Mock
    private UserRepository repository;

    private User testUser;
    private UserEntity testUserEntity;

    @BeforeEach
    void setUp() {
        // Initialize test User and UserEntity
        testUser = new User();
        testUser.setId(1);
        testUser.setName("John Doe");

        testUserEntity = new UserEntity();
        testUserEntity.setId(1);
        testUserEntity.setName("John Doe");

        // Setting up model mapper for UserEntity to User
        when(repository.save(any(UserEntity.class))).thenReturn(testUserEntity);
    }

    @Test
    void testRegisterUser() {
        // Act
        boolean result = service.registerUser(testUser);

        // Assert
        assertTrue(result);
        verify(repository, times(1)).save(any(UserEntity.class));
    }

    @Test
    void testGetUser() {
        // Arrange
        when(repository.findAll()).thenReturn(Arrays.asList(testUserEntity));

        // Act
        List<User> users = service.getUser();

        // Assert
        assertNotNull(users);
        assertEquals(1, users.size());
        assertEquals("John Doe", users.get(0).getName());
        verify(repository, times(1)).findAll();
    }

    @Test
    void testFindByName() {
        // Arrange
        when(repository.findAll()).thenReturn(Arrays.asList(testUserEntity));

        // Act
        List<User> users = service.findByName("John Doe");

        // Assert
        assertNotNull(users);
        assertEquals(1, users.size());
        assertEquals("John Doe", users.get(0).getName());
        verify(repository, times(1)).findAll();
    }

    @Test
    void testDeleteUser() {
        // Arrange
        doNothing().when(repository).deleteById(testUser.getId());

        // Act
        boolean result = service.deleteUser(testUser.getId());

        // Assert
        assertTrue(result);
        verify(repository, times(1)).deleteById(testUser.getId());
    }

    @Test
    void testUpdateUser() {
        // Act
        boolean result = service.updateUser(testUser);

        // Assert
        assertTrue(result);
        verify(repository, times(1)).save(any(UserEntity.class));
    }
}
