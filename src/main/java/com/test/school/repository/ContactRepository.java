package com.test.school.repository;

import com.test.school.model.Contact;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface ContactRepository extends PagingAndSortingRepository<Contact, Integer> {

//    List<Contact> findByStatus(Contact.StatusType statusType);


    Page<Contact> findContactByStatusType(Contact.StatusType statusType, Pageable pageable);

//    @Transactional
//    @Modifying
//    int updateMessageStatus(Contact.StatusType close, Integer id);

//    @Query(value = "SELECT * FROM contact_msg WHERE contact_msg.status = :status",nativeQuery = true)
//    Page<Contact> findByStatusWithQuery(@Param("status")String state, Pageable pageable);
//
//    @Transactional
//    @Modifying
//    @Query(nativeQuery = true)
//    int updateMsgStatusNative(String status, int id);
}
