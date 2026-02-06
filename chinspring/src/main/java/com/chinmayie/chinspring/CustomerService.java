package com.chinmayie.chinspring;

import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final InteractionRepository interactionRepository;

    public CustomerService(CustomerRepository customerRepository, InteractionRepository interactionRepository) {
        this.customerRepository = customerRepository;
        this.interactionRepository = interactionRepository;
    }

    public List<Customer> getAllCustomers(String ownerEmail) {
        return customerRepository.findAllByOwnerEmail(ownerEmail);
    }

    public Customer saveCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    public void deleteCustomer(Long id, String ownerEmail) {
        Customer customer = customerRepository.findByIdAndOwnerEmail(id, ownerEmail)
                .orElseThrow(() -> new RuntimeException("Customer not found or not owned by " + ownerEmail));
        customerRepository.delete(customer);
    }

    public Optional<Customer> getCustomerById(Long id, String ownerEmail) {
        return customerRepository.findByIdAndOwnerEmail(id, ownerEmail);
    }

    public Customer updateCustomer(Long id, Customer customerDetails, String ownerEmail) {
        Customer customer = customerRepository.findByIdAndOwnerEmail(id, ownerEmail)
                .orElseThrow(() -> new RuntimeException("Customer not found or not owned by " + ownerEmail));
        
        customer.setName(customerDetails.getName());
        customer.setEmail(customerDetails.getEmail());
        customer.setAge(customerDetails.getAge());
        customer.setPhone(customerDetails.getPhone());
        customer.setStatus(customerDetails.getStatus());
        customer.setPriority(customerDetails.getPriority());
        customer.setNextFollowUp(customerDetails.getNextFollowUp());
        
        return customerRepository.save(customer);
    }

    public Interaction addInteraction(Long customerId, String note, String type, String ownerEmail) {
        Customer customer = customerRepository.findByIdAndOwnerEmail(customerId, ownerEmail)
                .orElseThrow(() -> new RuntimeException("Customer not found or not owned by " + ownerEmail));
        
        Interaction interaction = new Interaction(note, type, LocalDateTime.now(), customer);
        return interactionRepository.save(interaction);
    }
}
