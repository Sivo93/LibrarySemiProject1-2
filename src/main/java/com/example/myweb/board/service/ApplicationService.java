package com.example.myweb.board.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.myweb.board.domain.Application;
import com.example.myweb.board.repository.ApplicationRepository;

import java.util.List;

@Service
public class ApplicationService {

    @Autowired
    private ApplicationRepository applicationRepository;

    public List<Application> getAllApplications() {
        return applicationRepository.findAll();
    }

    public Application getApplicationById(Long id) {
        return applicationRepository.findById(id).orElse(null);
    }

    public void addApplication(Application application) {
        applicationRepository.save(application);
    }

    public void updateApplication(Application application) {
        applicationRepository.save(application);
    }

    public void deleteApplicationById(Long id) {
        applicationRepository.deleteById(id);
    }
}