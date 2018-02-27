DROP KEYSPACE IF EXISTS mykeyspace;
CREATE KEYSPACE mykeyspace WITH REPLICATION = { 'class' : 'SimpleStrategy', 'replication_factor' : 1 };

use mykeyspace;

CREATE TABLE messages (
	content text,
	recordid timeuuid,
    PRIMARY KEY (recordid)
);