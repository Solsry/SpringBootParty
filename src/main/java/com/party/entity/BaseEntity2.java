package com.party.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

@EntityListeners(value ={AuditingEntityListener.class})
@MappedSuperclass
@Getter@Setter
public abstract class BaseEntity2  {

    @LastModifiedBy //엔터티 수정시 수정자의 id기록
    private String modifiedBy; //수정자










}
