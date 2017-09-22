package com.company.demo.security

import groovy.transform.CompileStatic
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import org.hibernate.annotations.Type
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener

import javax.persistence.CascadeType
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EntityListeners
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.OneToMany

@CompileStatic
@EqualsAndHashCode(excludes = ['id', 'createdDate', 'lastModifiedDate'])

@Entity
@EntityListeners(value = AuditingEntityListener)
class User implements Serializable {
    private static final long serialVersionUID = 1L

    @Id
    @GeneratedValue
    Long id

    String username

    String password

    @CreatedDate
    @Type(type="java.sql.Timestamp")
    @Column(updatable = false)
    Date createdDate

    @LastModifiedDate
    @Type(type="java.sql.Timestamp")
    @Column(updatable = false)
    Date lastModifiedDate

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    List<Role> roles

}
