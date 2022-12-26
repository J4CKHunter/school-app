use school;

INSERT INTO `role` (`role_type`, `created_at`, `created_by`)
VALUES ('ADMIN', CURDATE(), 'DBA');

INSERT INTO `role` (`role_type`, `created_at`, `created_by`)
VALUES ('STUDENT', CURDATE(), 'DBA');