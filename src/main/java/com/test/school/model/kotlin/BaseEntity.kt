package com.test.school.model.kotlin//package com.test.school.model
//
//import org.hibernate.annotations.CreationTimestamp
//import org.hibernate.annotations.GenericGenerator
//import org.hibernate.annotations.UpdateTimestamp
//import org.springframework.data.annotation.CreatedBy
//import org.springframework.data.annotation.CreatedDate
//import org.springframework.data.annotation.LastModifiedBy
//import org.springframework.data.annotation.LastModifiedDate
//import org.springframework.data.domain.Persistable
//import org.springframework.data.jpa.domain.support.AuditingEntityListener
//import java.io.Serializable
//import java.time.Instant
//import java.time.LocalDateTime
//import javax.persistence.*
//
//@MappedSuperclass
//@EntityListeners(AuditingEntityListener::class)
//abstract class BaseEntity<T: Serializable>(
//    @Id
//    private val id: T? = null
//)  : Persistable<T>{
//
//    @field:CreatedDate
//    @field:Column(updatable = false)
//    private val createdAt: LocalDateTime? = null
//
//    @field:CreatedBy
//    @field:Column(updatable = false)
//    private val createdBy: String? = null
//
//    @field:LastModifiedDate
//    @field:Column(insertable = false)
//    private val updatedAt: LocalDateTime? = null
//
//    @field:LastModifiedBy
//    @field:Column(insertable = false)
//    private val updatedBy: String? = null
//
//    override fun getId(): T? {
//        return id
//    }
//
//    override fun toString(): String {
//        return "BaseEntity(id=$id, createdAt=$createdAt, createdBy=$createdBy, updatedAt=$updatedAt, updatedBy=$updatedBy)"
//    }
//
//    // See details here https://vladmihalcea.com/the-best-way-to-implement-equals-hashcode-and-tostring-with-jpa-and-hibernate/
//    override fun equals(other: Any?): Boolean {
//        return if (this === other) true
//        else if (!javaClass.isInstance(other)) false
//        else id != null && id == (other as BaseEntity<*>).id
//    }
//
//    override fun hashCode(): Int {
//        return 31
//    }
//}