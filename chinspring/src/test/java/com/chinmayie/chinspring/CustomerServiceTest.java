package com.chinmayie.chinspring;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private InteractionRepository interactionRepository;

    @InjectMocks
    private CustomerService customerService;

    @Test
    void shouldDeleteCustomerOnlyIfOwnedByEmail() {
        // 1. Arrange (Setup fake data)
        Long customerId = 1L;
        String owner = "admin@example.com";
        Customer alex = new Customer();
        alex.setId(customerId);
        alex.setOwnerEmail(owner);

        // Tell the fake repository what to return
        when(customerRepository.findByIdAndOwnerEmail(customerId, owner))
            .thenReturn(Optional.of(alex));

        // 2. Act (Perform the action)
        customerService.deleteCustomer(customerId, owner);

        // 3. Assert (Verify it worked)
        verify(customerRepository, times(1)).delete(alex);
    }
    @Test
    void shouldThrowExceptionWhenDeletingNonExistentOrUnownedCustomer() {
        // 1. Arrange
        Long customerId = 999L; // An ID that doesn't exist
        String owner = "hacker@example.com";

        // Tell the mock: "If someone asks for this ID/Owner, return NOTHING"
        when(customerRepository.findByIdAndOwnerEmail(customerId, owner))
                .thenReturn(Optional.empty());

        // 2. Act & 3. Assert (We check if it throws a RuntimeException)
        assertThrows(RuntimeException.class, () -> {
            customerService.deleteCustomer(customerId, owner);
        });

        // Final Verify: Make sure the repository's delete() was NEVER called
        verify(customerRepository, never()).delete(any());
    }
}
