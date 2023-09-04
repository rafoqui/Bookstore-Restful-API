package com.restful.bookstore.repositories;

import com.restful.bookstore.entities.Publishing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PublishingRepository extends JpaRepository<Publishing, Long> {

    Optional<Publishing> findByName(String name);
}
