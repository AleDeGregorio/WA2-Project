CREATE OR REPLACE FUNCTION increment_id()
    RETURNS TRIGGER AS $$
BEGIN
    NEW.id = NEW.id + 6; -- Increment the ID by 6
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER increment_id_trigger
    BEFORE INSERT ON message
    FOR EACH ROW
EXECUTE FUNCTION increment_id();
-- this trigger is neede to be executed on the database to start from ID 7 to avoid problem with inserting by jpa