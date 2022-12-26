package com.test.school.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
//@SqlResultSetMappings({
//        @SqlResultSetMapping(name = "SqlResultSetMapping.count", columns = @ColumnResult(name = "cnt"))
//})
//@NamedQueries({
//        @NamedQuery(name = "Contact.findOpenMsgs",
//                query = "SELECT c FROM Contact c where c.status = :status"),
//        @NamedQuery(name = "Contact.updateMessageStatus",
//                query = "UPDATE Contact contact SET contact.statusType = ?1 WHERE contact.id = ?2")
//})
//@NamedNativeQueries({
//        @NamedNativeQuery(name = "Contact.findOpenMsgsNative",
//                query = "SELECT * FROM contact_msg c WHERE c.status = :status"
//                ,resultClass = Contact.class),
//        @NamedNativeQuery(name = "Contact.findOpenMsgsNative.count",
//                query = "select count(*) as cnt from contact_msg c where c.status = :status",
//                resultSetMapping = "SqlResultSetMapping.count"),
//        @NamedNativeQuery(name = "Contact.updateMsgStatusNative",
//                query = "UPDATE contact_msg c SET c.status = ?1 WHERE c.contact_id = ?2")
//})
public class Contact extends BaseEntity{

    private String name;

    private String mobileNumber;

    private String email;

    private String subject;

    private String message;

    @Enumerated(EnumType.STRING)
    private StatusType statusType;

    public enum StatusType{
        OPEN, CLOSE
    }
}
