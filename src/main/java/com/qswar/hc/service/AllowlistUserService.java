package com.qswar.hc.service;

import com.qswar.hc.model.AllowlistUser;

import java.util.List;
import java.util.Optional;

public interface AllowlistUserService {

    public AllowlistUser createByOnlyPhone(String phone);
    public AllowlistUser createByOnlyEmail(String email);
    public AllowlistUser createByEmailAndPhone(String email, String phone);
    public AllowlistUser updatePhone(String email, String phone);
    public AllowlistUser updateEmail(String phone, String email);
    public Optional<AllowlistUser> findByPhone(String phone);
    public Optional<AllowlistUser> findByEmail(String email);
    public List<AllowlistUser> findAll();
    public void deleteByPhone(String phone);
    public void deleteByEmail(String email);


}
