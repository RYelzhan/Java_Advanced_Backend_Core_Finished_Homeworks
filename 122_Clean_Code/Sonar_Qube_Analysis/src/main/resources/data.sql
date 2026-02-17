-- Initialize test data
CREATE TABLE IF NOT EXISTS users (
                                     id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                     username VARCHAR(255),
    password VARCHAR(255),
    email VARCHAR(255),
    role VARCHAR(50)
    );

INSERT INTO users (username, password, email, role) VALUES
                                                        ('admin', 'admin123', 'admin@example.com', 'ADMIN'),
                                                        ('user1', 'pass123', 'user1@example.com', 'USER'),
                                                        ('user2', 'pass456', 'user2@example.com', 'USER');