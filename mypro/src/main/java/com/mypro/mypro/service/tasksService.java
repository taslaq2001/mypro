package com.mypro.mypro.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mypro.mypro.repository.tasksRepository;

@Service

public class tasksService{
    @Autowired
    private tasksRepository task;
}