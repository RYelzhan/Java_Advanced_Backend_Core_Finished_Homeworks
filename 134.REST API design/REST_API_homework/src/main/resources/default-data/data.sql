INSERT INTO users (username, email, password, role) VALUES
                                                        ('alice', 'alice@example.com', '$2a$10$btSpMVDGxQUCxpsTwKN/8.IGptsYmLRjQM8/xQfDc3NGwhdHhQnj6', 'ROLE_USER'),
                                                        ('bob', 'bob@example.com', '$2a$10$btSpMVDGxQUCxpsTwKN/8.IGptsYmLRjQM8/xQfDc3NGwhdHhQnj6', 'ROLE_USER'),
                                                        ('charlie', 'charlie@example.com', '$2a$10$btSpMVDGxQUCxpsTwKN/8.IGptsYmLRjQM8/xQfDc3NGwhdHhQnj6', 'ROLE_USER');

-- Set the auto-increment starting value for id
ALTER TABLE users ALTER COLUMN id RESTART WITH 20;