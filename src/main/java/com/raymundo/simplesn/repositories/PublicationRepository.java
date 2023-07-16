package com.raymundo.simplesn.repositories;

import com.raymundo.simplesn.entities.PublicationEntity;
import com.raymundo.simplesn.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PublicationRepository extends JpaRepository<PublicationEntity, UUID> {

    List<PublicationEntity> findAllByOrderByCreationDateAsc();

    List<PublicationEntity> findAllByUserOrderByCreationDateAsc(UserEntity user);
}
