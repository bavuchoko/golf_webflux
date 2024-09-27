package com.pjs.golf_webflex.repository;

import com.pjs.golf_webflex.domain.Fields;
import com.pjs.golf_webflex.dto.info.FieldsInfo;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

public interface FieldsRepository{

    
    Mono<Map<Long, FieldsInfo>> findFieldsByGameIds(List<Long> fieldsIds);
}
