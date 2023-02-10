package com.eureka.client.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;
import org.springframework.data.domain.Persistable;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "translation")
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class Translation implements Persistable<String> {
    @Id
    @Column(name = "id", nullable = false, unique = true)
    private String message;
    @Column(name = "_from")
    private String from;
    @Column(name = "_to")
    private String to;
    @Column
    private String translated;
    @Column
    private Boolean isActive;
    @Column
    private LocalDateTime created;
    @Column
    private LocalDateTime updated;
    @Transient
    private boolean isNew;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Translation that = (Translation) o;
        return message != null && Objects.equals(message, that.message);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String getId() {
        return message;
    }

    @Override
    public boolean isNew() {
        return isNew;
    }
}
