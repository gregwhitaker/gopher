-- user_role
INSERT INTO user_role (role_id, role_name, role_desc) VALUES (1, 'Administrator', 'Administrator') ON CONFLICT DO NOTHING;
INSERT INTO user_role (role_id, role_name, role_desc) VALUES (2, 'User', 'User') ON CONFLICT DO NOTHING;

-- user_profile
INSERT INTO user_profile (user_id, user_username, user_pwdhash, user_firstname, user_lastname, role_id) VALUES ('00000000000000000000000000000000', 'system', '', 'System', 'System', 1);
