package com.company.demo.security

import groovy.transform.CompileStatic
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import org.hibernate.annotations.Type
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EntityListeners
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne

@CompileStatic
@EqualsAndHashCode(excludes = ['id', 'createdDate'])
@ToString(includePackage = false)
@Entity
@EntityListeners(value = AuditingEntityListener)
class Role implements Serializable {
    private static final long serialVersionUID = 1L

    @Id
    @GeneratedValue
    Long id

    @Enumerated(value = EnumType.STRING)
    @Column(name = 'name', nullable = false, unique = true)
    RoleType type

    @CreatedDate
    @Type(type = 'java.sql.Timestamp')
    @Column(updatable = false)
    Date createdDate

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, updatable = false)
    User user

}
