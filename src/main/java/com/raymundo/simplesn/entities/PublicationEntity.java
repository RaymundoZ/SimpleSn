package com.raymundo.simplesn.entities;

import com.raymundo.simplesn.dto.PublicationResponse;
import com.raymundo.simplesn.util.BaseEntity;
import com.raymundo.simplesn.util.ConvertableToDto;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "_publication")
@Data
public class PublicationEntity implements BaseEntity, ConvertableToDto<PublicationResponse> {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @Column(name = "text")
    private String text;

    @Column(name = "creation_date")
    @Temporal(value = TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "dd.MM.yyyy, HH:mm:ss")
    private LocalDateTime creationDate;

    @ManyToOne
    private UserEntity user;

    @Override
    public PublicationResponse toDto() {
        return new PublicationResponse(id.toString(), text, user.getUsername(), creationDate);
    }
}
