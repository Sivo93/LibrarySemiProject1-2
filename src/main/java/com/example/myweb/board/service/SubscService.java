package com.example.myweb.board.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.myweb.board.domain.Subsc;
import com.example.myweb.board.repository.SubscRepository;

import java.util.List;

@Service
public class SubscService {

    @Autowired
    private SubscRepository subscRepository;

    public List<Subsc> getAllSubscs() {
        return subscRepository.findAll();
    }

    public Subsc getSubscById(Long id) {
        return subscRepository.findById(id).orElse(null);
    }

    public void addSubsc(Subsc subsc) {
        subscRepository.save(subsc);
    }

    public void updateSubsc(Subsc subsc) {
        subscRepository.save(subsc);
    }

    public void deleteSubscById(Long id) {
        subscRepository.deleteById(id);
    }
}