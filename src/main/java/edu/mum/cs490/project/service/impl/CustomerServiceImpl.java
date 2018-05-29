package edu.mum.cs490.project.service.impl;

import edu.mum.cs490.project.domain.Address;
import edu.mum.cs490.project.domain.Customer;
import edu.mum.cs490.project.domain.Status;
import edu.mum.cs490.project.repository.AddressRepository;
import edu.mum.cs490.project.repository.CustomerRepository;
import edu.mum.cs490.project.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Erdenebayar on 4/20/2018
 */
@Service
public class CustomerServiceImpl extends UserServiceImpl<Customer> implements CustomerService{

    private final CustomerRepository repository;
    private final AddressRepository addressRepository;

    @Autowired
    public CustomerServiceImpl(CustomerRepository repository, AddressRepository addressRepository) {
        super(repository);
        this.repository = repository;
        this.addressRepository = addressRepository;
    }


    @Override
    public List<Address> findByUser_id(Integer userId) {
        return this.addressRepository.findByUser_idAndStatus(userId, Status.ENABLED);
    }

    @Override
    public Address findAddressById(Integer addressId) {
        return this.addressRepository.findById(addressId).orElse(null);
    }

    @Override
    public List<Customer> find(String username, String firstName, String lastName, Status status) {
        return repository.find(username, firstName, lastName, status);
    }

    @Override
    public Page<Customer> findPage(String username, String firstName, String lastName, Status status, Pageable pageable) {
        return repository.findPage(username, firstName, lastName, status, pageable);
    }
}
