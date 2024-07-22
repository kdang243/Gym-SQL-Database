-- INSERT: insert user specified values into Member table, clearanceLevel > 3 --
INSERT 
INTO Member(MID, name, phone, email, clearanceLevel)
VALUES (?, ?, ?, ?, ?);

-- DELETE: delete rows from staff tables where user chooses row to delete by SID (cascade delete Staff2 -> Staff1) --
DELETE 
FROM Staff1
WHERE SType = ?;

-- UPDATE: update a members phone number or email, found by MID--
UPDATE Member
SET phone = ?, email = ?
WHERE MID = ?;

-- SELECTION: find all members having specified clearance level --
SELECT *
FROM Member
WHERE clearanceLevel = ?;

-- PROJECTION: select any number of attributes from any table--
SELECT ?, ?
FROM ?;

-- JOIN: find all MIDs, names, phone numbers and emails of people who were in the gym on a certain day --
SELECT DISTINCT m.name, m.phone, m.email
FROM Member m, Attends a
WHERE m.MID = a.MID AND a.sessionDate = ?;

-- Aggregation with GROUP BY: Find number of equipment in each condition--
SELECT condition as condition, COUNT(*) as count
FROM Equipment2
GROUP BY condition;

-- Aggregation with HAVING: find MIDs for members who have had more than one session past input date--
SELECT m.MID as MID, COUNT(*) as count
FROM Member m, Attends a
WHERE m.MID = a.MID AND a.sessionDate > '2023-01-01'
GROUP BY m.MID
HAVING COUNT(*) > 1;

-- Nested Aggregation with GROUP BY: find number of long sessions (greater duration than average session) each member has had for members with 2 or more long sessions--
SELECT m.MID as MID, COUNT(*) as count
FROM Member m, Attends a, Session1 s1, Session2 s2
WHERE m.MID = a.MID AND a.sessionDate = s2.sessionDate AND a.startTime = s1.startTime AND s1.startTime = s2.startTime AND s1.endTime = s2.endTime AND
	s1.duration > (SELECT avg(duration) FROM Session1 s)
GROUP BY m.MID
HAVING COUNT(*) > 1;

-- Division: Find names of staff who have worked on all floors--
SELECT s.SID as SID, s.name as name
FROM Staff2 s
WHERE NOT EXISTS ((SELECT f.FNumber FROM Floor f) MINUS 
(SELECT wo.FNumber FROM WorksOn wo WHERE wo.SID = s.SID));