-- Trigger for the "message" table
CREATE OR REPLACE FUNCTION increment_message_id()
    RETURNS TRIGGER AS $$
BEGIN
    NEW.id = NEW.id + 6; -- Increment the ID by 6
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER increment_message_id_trigger
    BEFORE INSERT ON message
    FOR EACH ROW
EXECUTE FUNCTION increment_message_id();

-- Trigger for the "chat" table
CREATE OR REPLACE FUNCTION increment_chat_id()
    RETURNS TRIGGER AS $$
BEGIN
    NEW.id = NEW.id + 6; -- Increment the ID by 6
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER increment_chat_id_trigger
    BEFORE INSERT ON chat
    FOR EACH ROW
EXECUTE FUNCTION increment_chat_id();