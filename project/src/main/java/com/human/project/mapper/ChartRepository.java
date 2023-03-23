package com.human.project.mapper;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.human.project.domain.Chart;

public interface ChartRepository extends MongoRepository<Chart, String> {
}


