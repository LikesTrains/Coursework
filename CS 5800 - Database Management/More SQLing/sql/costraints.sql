-- Constraint 1
-- The default number of ABs is 20.
alter table batting
alter AB set default 20;
-- Insert to test the constraint
insert into batting(masterID, yearID)
values ("aardsda01", 2003);
-- Entry inserted ended up having a AB value of 20 despite it not being specified. So it worked.

-- Constraint 2
-- A player cannot have more H (hits) than AB (at bats).
alter table batting
add constraint checkbats check(AB>H);
-- Insert to test the constraint
insert into batting(masterID, yearID, H, AB)
values ("aardsda01", 2002, 20, 15);

-- Constraint 3
-- In the Teams table, the league can be only one of the values: NL or AL.
alter table teams
add constraint check_ID check(lgID = "NL" OR lgID = "AL");
-- Insert to test constraint
insert into teams(yearID, lgID)
values(1870, "HA");

-- Constraint 4
-- When a team loses more than 161 games in a season, the fans want to forget about the team forever, so all batting records for the team for that year should be deleted.
-- Add a trigger on update, so when a team is updated and it's shown it has more than 161 records delete the rows in batting with the same yearID and teamID
delimiter //
create trigger NoLosersUp before update on teams 
for each row
begin 
	if new.L > 161 then
	delete from batting
    where (batting.yearID = new.yearID and batting.teamID = new.teamID);
    end if;
end;
create trigger NoLosersIn before insert on teams 
for each row
begin 
	if new.L > 161 then
	delete from batting
    where (batting.yearID = new.yearID and batting.teamID = new.teamID);
    end if;
end;

delimiter ;
update teams
set L = 162
where (yearID = 1873 and teamID ='BS1');

insert into teams(yearID, teamID,lgID, L)
values(1870, 'BS1','NA', 162);

-- Constraint 5
-- If a player wins the MVP, WS MVP, and a Gold Glove in the same season, they are automatically inducted into the Hall of Fame.

-- Create trigger that adds individuals to hall of fame when they've been added to all three categories we wanted in the same season
delimiter //
create trigger zeroToHero after insert on awardsplayers
for each row
begin
	declare ct int;
	set ct = 0;
	if new.masterID in (select masterID from awardsplayers where awardID = 'Most Valuable Player' and yearID = new.yearID) then
		set ct = ct+1;
	end if;
	-- end if;
	if new.masterID in (select masterID from awardsplayers where awardID = 'World Series MVP' and yearID = new.yearID) then
		set ct = ct+1;
	end if;
	-- end if;
	if new.masterID in (select masterID from awardsplayers where awardID = 'Gold Glove' and yearID = new.yearID) then
		set ct = ct+1;
    -- end if;
    end if;
    if ct=3 then
        insert into halloffame(masterID, yearID, votedBy, inducted, category) values(new.masterID, new.yearID, 'The people', 'Y', 'Player');
    end if;
end;

-- inserts to test it worked
delimiter ;
insert into awardsplayers(masterID, awardID, yearID)
values ('aaa', 'Most Valuable Player', 1934);
insert into awardsplayers(masterID, awardID, yearID)
values ('aaa', 'World Series MVP', 1934);
insert into awardsplayers(masterID, awardID, yearID)
values ('aaa', 'Gold Glove', 1934);

-- deletes to avoid clutter

delete from awardsplayers
where (masterID='aaa' and awardID = 'Most Valuable Player' and yearID = 1934);
delete from awardsplayers
where (masterID='aaa' and awardID = 'World Series MVP' and yearID = 1934);
delete from awardsplayers
where (masterID='aaa' and awardID = 'Gold Glove' and yearID = 1934);

delete from halloffame
where(masterID = 'aaa');

-- Constraint 6
-- All teams must have some name, i.e., it cannot be null.

Alter table teams
change name name varchar(50) not null;

insert into teams(yearID, lgID, teamID, name)
values(1800, 'EZ','Dudes',null);

-- Constraint 7
-- Everybody has a unique name (combined first and last names).


describe master;
select * from master;
-- Constraint works, but it cannot be added to table due to there already being entries that break it.
alter table master
add constraint no_dupes unique(nameFirst, nameLast);

-- If constraint were to work, this would not be allowed in
insert into master(nameFirst, nameLast)
values('Ted','Abermathy');