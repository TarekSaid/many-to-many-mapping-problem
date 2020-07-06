package com.example.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@MappedSuperclass
public abstract class DomainEntity implements Serializable {
    private static final long serialVersionUID = 8298178442304147262L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @Column
    protected boolean active;

    @Column(name = "created_by")
    protected String createdBy;

    @Column(name = "created_date")
    protected LocalDateTime createdDate;

    @Column(name = "last_modified_by")
    protected String lastModifiedBy;

    @Column(name = "last_modified_date")
    protected LocalDateTime lastModifiedDate;

    public Long getId() {
        return id;
    }

    public DomainEntity setId(Long id) {
        this.id = id;
        return this;
    }

    public boolean isActive() {
        return active;
    }

    public DomainEntity setActive(boolean active) {
        this.active = active;
        return this;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public DomainEntity setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public DomainEntity setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public DomainEntity setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
        return this;
    }

    public LocalDateTime getLastModifiedDate() {
        return lastModifiedDate;
    }

    public DomainEntity setLastModifiedDate(LocalDateTime lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
        return this;
    }
}
