
-- Insert sample data for Member table
INSERT INTO member (provider, provider_id, username, password, email, age, gender, role)
VALUES
    ('Google', 'google123', 'john_doe', 'password123', 'john@example.com', '30', 'MALE', 'ROLE_MEMBER'),
    ('Facebook', 'fb456', 'jane_smith', 'pass456', 'jane@example.com', '25', 'FEMALE', 'ROLE_MEMBER');

-- Insert sample data for Profile table
INSERT INTO profile (member_id, mbti_type, gpa_value, region_name, sport, exercise_period)
VALUES
    (1, 'INFJ', '3.8', 'New York', 'RUNNING', 'Morning'),
    (2, 'ENFP', '3.5', 'Los Angeles', 'HIKING', 'Evening');

