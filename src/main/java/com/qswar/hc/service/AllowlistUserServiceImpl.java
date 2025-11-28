package com.qswar.hc.service;

import com.qswar.hc.exception.DuplicateEntryException; // Custom Exception
import com.qswar.hc.exception.ResourceNotFoundException; // Custom Exception
import com.qswar.hc.model.AllowlistUser; // Renamed
import com.qswar.hc.repository.AllowlistUserRepository; // Renamed
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional // Ensures database operations are atomic (all or nothing)
public class AllowlistUserServiceImpl implements AllowlistUserService { // Renamed

    private final AllowlistUserRepository allowlistUserRepository; // Renamed

    public AllowlistUserServiceImpl(AllowlistUserRepository allowlistUserRepository) {
        this.allowlistUserRepository = allowlistUserRepository;
    }

    // --- CREATE METHODS ---

    @Override
    public AllowlistUser createByOnlyPhone(String phone) {
        if (allowlistUserRepository.existsByPhone(phone)) {
            throw new DuplicateEntryException("Allowlist entry already exists for phone: " + phone);
        }
        AllowlistUser user = new AllowlistUser();
        user.setPhone(phone);
        return allowlistUserRepository.save(user); // Return the saved entity
    }

    @Override
    public AllowlistUser createByOnlyEmail(String email) {
        if (allowlistUserRepository.existsByEmail(email)) {
            throw new DuplicateEntryException("Allowlist entry already exists for email: " + email);
        }
        AllowlistUser user = new AllowlistUser();
        user.setEmail(email);
        return allowlistUserRepository.save(user);
    }

    @Override
    public AllowlistUser createByEmailAndPhone(String email, String phone) {
        if (allowlistUserRepository.existsByEmail(email) || allowlistUserRepository.existsByPhone(phone)) {
            throw new DuplicateEntryException("Allowlist entry already exists with the provided email or phone.");
        }
        AllowlistUser user = new AllowlistUser();
        user.setEmail(email);
        user.setPhone(phone);
        return allowlistUserRepository.save(user);
    }

    // --- UPDATE METHODS ---

    @Override
    public AllowlistUser updatePhone(String email, String phone) {
        // Find by email (key) and throw if not found
        AllowlistUser user = allowlistUserRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Allowlist entry not found with email: " + email));

        user.setPhone(phone);
        return allowlistUserRepository.save(user); // Return the updated entity
    }

    @Override
    public AllowlistUser updateEmail(String phone, String email) {
        // Find by phone (key) and throw if not found
        AllowlistUser user = allowlistUserRepository.findByPhone(phone)
                .orElseThrow(() -> new ResourceNotFoundException("Allowlist entry not found with phone: " + phone));

        user.setEmail(email);
        return allowlistUserRepository.save(user);
    }

    // --- FIND METHODS ---

    @Override
    public Optional<AllowlistUser> findByPhone(String phone) {
        return allowlistUserRepository.findByPhone(phone); // Return Optional
    }

    @Override
    public Optional<AllowlistUser> findByEmail(String email) {
        return allowlistUserRepository.findByEmail(email); // Return Optional
    }

    @Override
    public List<AllowlistUser> findAll() {
        List<AllowlistUser> list = allowlistUserRepository.findAll();
        if( list == null || list.isEmpty() ) { return null; }
        return list;
    }

    // --- DELETE METHODS ---

    @Override
    public void deleteByPhone(String phone) {
        AllowlistUser user = allowlistUserRepository.findByPhone(phone)
                .orElseThrow(() -> new ResourceNotFoundException("Cannot delete: Allowlist entry not found with phone: " + phone));

        allowlistUserRepository.delete(user); // Void return for delete
    }

    @Override
    public void deleteByEmail(String email) {
        AllowlistUser user = allowlistUserRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Cannot delete: Allowlist entry not found with email: " + email));

        allowlistUserRepository.delete(user);
    }
}