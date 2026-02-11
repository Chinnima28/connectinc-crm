package com.chinmayie.chinspring;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import java.util.List;

@RestController
@RequestMapping("api/v1/customers")
@CrossOrigin(origins = "*")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public List<Customer> getCustomers(@RequestHeader("X-User-Email") String ownerEmail) {
        return customerService.getAllCustomers(ownerEmail);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void registerCustomer(
            @RequestHeader("X-User-Email") String ownerEmail,
            @RequestBody Customer customer) {
        customer.setOwnerEmail(ownerEmail);
        customerService.saveCustomer(customer);
    }

    @DeleteMapping("{id}")
    public void deleteCustomer(
            @RequestHeader("X-User-Email") String ownerEmail,
            @PathVariable("id") Long id) {
        customerService.deleteCustomer(id, ownerEmail);
    }

    @PutMapping("{id}")
    public void updateCustomer(
            @RequestHeader("X-User-Email") String ownerEmail,
            @PathVariable("id") Long id, 
            @RequestBody Customer customer) {
        customerService.updateCustomer(id, customer, ownerEmail);
    }

    @PostMapping("{id}/interactions")
    @ResponseStatus(HttpStatus.CREATED)
    public void addInteraction(
            @RequestHeader("X-User-Email") String ownerEmail,
            @PathVariable("id") Long id,
            @RequestBody InteractionRequest request) {
        customerService.addInteraction(id, request.note(), request.type(), ownerEmail);
    }

    public record InteractionRequest(String note, String type) {}
}
