use school;

INSERT INTO `person` (`name`,`email`,`mobile_number`,`password`,`role_id`,`created_at`, `created_by`)
VALUES ('admin','admin@school.com.tr','3443434343','$2a$10$XhU4UcSxDPb5G0I0fT/CZ.Lfj2VW2fkLkUP5cOEM.xM8EzyUQXaD2', 1 ,CURDATE(),'DBA');
