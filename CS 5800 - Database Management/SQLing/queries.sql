-- QUERY 1 - LA Dodgers
-- List FN and LN of players who have played in LA Dodgers
select nameFirst, nameLast
from master 
where masterID in (select masterID
from appearances
where teamID = (select distinct teamID
from teams
where name = "Los Angeles Dodgers"
)
)
-- QUERY 2 - LA Dodgers only
-- List FN and LN of players who have ONLY played in LA Dodgers
select nameFirst, nameLast
from master 
where masterID in (select masterID
from appearances
where teamID = (select distinct teamID
from teams
where name = "Los Angeles Dodgers"
)
)
and masterID not in (select masterID
from appearances
where teamID in (select distinct teamID
from teams
where name != "Los Angeles Dodgers"
)
)
-- QUERY 3 - Expos Pitchers
-- List FN and LN of all players that have pitched for Montreal Expos
select distinct nameFirst, nameLast
from master 
where masterID in (select masterID
from pitching
where teamID = (select distinct teamID
from teams
where name = "Montreal Expos"
)
)
-- QUERY 4 - Error Kings
-- Name of team, year, and number of errors for teams with over 160 errors in a season
select yearID, name, E
from teams
where E > 160
-- QUERY 5 - USU Batters
-- List FN, LN, year played, and batting avg of every USU batter
-- Step 1: Start by creating a table with relevant info for all usu batters
create table usubats as
select masterID, H, AB, (H/AB), yearID
from batting 
where masterID in (select distinct masterID
from SchoolsPlayers
where schoolID = (select schoolID
from schools
where schoolName = 'Utah State University'))
and G_batting > 0
-- Step 2: mess with usubats table and master to get expected result
select m.nameFirst, m.nameLast, U.H, U.AB, (H/AB), U.yearID
from master m
join usubats U on U.masterID = m.masterID
-- QUERY 6 - Yankee Run Kings
-- List FN, LN, Year, and homeRuns for all yankees players who have scored the most HRs in a season
-- First make table of max homeruns for every year
create table maxHR
(select max(HR) as HRs, yearID 
from batting 
group by yearID)

-- Second get a table of all New York Yankee players
create table NYYbatting
select masterID, HR, yearID
from batting
where teamID in
(select distinct (teamID)
from teams
where name ="New York Yankees"
) and HR > 0

-- Third join NYYbatting and maxHR on homeruns and years. Works but it has masterId instead of first and last names
create table NYYHRK
select masterID, ny.yearID, ny.HR
from maxHR as m inner join NYYbatting as ny
on m.HRs = ny.HR and m.yearID = ny.yearID
order by ny.yearID

-- Get names of players
select nameFirst, nameLast, nyk.yearID, nyk.HR
from master as m inner join NYYHRK as nyk
on m.masterID=nyk.masterID
order by nyk.yearID


-- QUERY 7 - Bumper Salary Teams
-- List the total salary for two consecutive years, team name, and year for every team that had a total salary which was 1.5 times as much as for the previous year
-- Step 1: Create table with teams' salaries throughout all time
create table teamSalaries as
select teamID, yearId, sum(salary) as totalSalary
from salaries
group by teamID, yearID

-- Step 2: Use black magic to calculate the changes in the teamSalaries table the way we want them to
create table TSC
SELECT ts.*,
totalSalary / (SELECT totalSalary FROM teamSalaries prev WHERE prev.yearID < ts.yearID and prev.teamID = ts.teamID ORDER BY teamID, yearID DESC LIMIT 1) AS changes
FROM teamSalaries ts
-- Step 3: Use the teamSalaries table to change it into requested output
create table teamChangedSalaries
(select t.teamID,
yearID-1 as previousYear,
totalSalary/changes as previousSalary,
t.yearID,
t.totalSalary as Salary,
t.changes*100 as percentIncrease
from TSC t
where changes>1.5
order by t.yearID)
-- step 4 formatting (again)
select   distinct(t.name), t.lgID, tsc.previousYear, tsc.previousSalary, tsc.yearID, tsc.Salary, tsc.percentIncrease
from teams as t inner join teamChangedSalaries as tsc
on t.teamId =  tsc.teamID
order by tsc.previousYear, t.name

-- Query 8 - Montreal Expos Three
-- List the first name and last name of every player that has batted for the Montreal Expos in at least three consecutive years. List each player only once.
-- STEP 1 getting all HRs from montreal expos players. Assume that if they have any HR data they were batting at the time
create table MonHRs
(select masterID,yearID,HR
from batting
where teamID in
(select teamID
from teams
where name = "Montreal Expos")
group by masterID,yearID)
order by yearID DESC

-- Step 2. Magic
SELECT 
    nameFirst, nameLast
FROM
    master
WHERE
    masterID IN (SELECT 
            masterID
        FROM
            (SELECT 
                masterID, COUNT(masterID) AS years
            FROM
                (SELECT 
                p1.masterID
            FROM
                monhrs p1
            INNER JOIN monhrs p2 ON p1.yearID = p2.yearID + 1
                AND p1.masterID = p2.masterID
            ORDER BY p1.masterID) AS temp
            GROUP BY masterID) AS t2
        WHERE
            years >= 2)


-- Query 9
-- List the first name, last name, year, and number of HRs of every player that has hit the 
-- most home runs in a single season. Order by the year.
-- create table of maximum homeruns per year
create table maxHRs as
select max(HR) as HRs, yearID 
from batting 
group by yearID

-- Step 2: join maxHRs with batting and join on year and player home runs
create table maxHRPlayers
select mh.yearID, masterID, HRs
from maxHRs as mh inner join batting as b
on mh.yearID = b.yearID and mh.HRs = b.HR
order by mh.yearID,masterID

-- Step 3: formating
--
select mhrp.yearID, m.nameFirst, m.nameLast, mhrp.HRS
from master as m inner join maxHRPlayers as mhrp
on m.masterID = mhrp.masterID
order by mhrp.yearID, m.nameLast

-- Query 10
-- Third best home runs each year

-- creates a table of max home runs
create table maxHRs as
select max(HR) as HRs, yearID 
from batting 
group by yearID

-- creates a table of all homeruns per year
create table HRsPerYear
select distinct(HR), YearID
from batting
order by yearID,HR DESC


-- query eliminates the top homeruns from the maxHRs table
create table HRMinusTopOne
select distinct(HR), hrpy.yearID
from maxHRs mh inner join HRsPerYear hrpy
on mh.HRs != hrpy.HR and mh.yearID = hrpy.yearID
order by yearID, HR DESC

-- create table with second most homeruns
create table SecondMaxHRs as
select max(HR) as HRs, yearID 
from HRMinusTopOne 
group by yearID

-- eliminate top homeruns from the second max homeruns
create table HRMinusTopTwo
select distinct(HR), hrmto.yearID
from SecondMaxHRs smh inner join HRMinusTopOne hrmto
on smh.HRs != hrmto.HR and smh.yearID = hrmto.yearID
order by smh.yearID, HR DESC

-- create table of third most homeruns
create table ThirdMaxHRs as
select max(HR) as HRs, yearID 
from HRMinusTopTwo 
group by yearID

-- join maxHRs with batting and join on year and player home runs
create table thirdMaxHRPlayers
select tmh.yearID, masterID, HRs
from ThirdMaxHRs as tmh inner join batting as b
on tmh.yearID = b.yearID and tmh.HRs = b.HR
order by tmh.yearID,masterID

-- formating
select  m.nameFirst, m.nameLast, tmhrp.yearID, tmhrp.HRS
from master as m inner join thirdMaxHRPlayers as tmhrp
on m.masterID = tmhrp.masterID
order by tmhrp.yearID, m.nameLast

-- QUERY 11 - Triple happy team mates
-- List the team name, year, names of player, the number of triples hit (column "3B" in the batting table), in which two or more players on the same team hit 10 or more triples each.
-- create table of all triples >= 10
create table triples
select distinct(masterID),3B as triples,teamID, yearID
from batting 
where 3B >= 10
order by yearID

-- join triples table to itself and then only allow it to join on same team,
-- same year, but different names. use < to ensure we have no copies
create table tripleMates
select t1.masterID as id1, t2.masterID as id2, t1.teamID, t1.yearID, t1.triples as triples1, t2.triples as triples2
from triples t1 inner join triples t2
on t1.teamID=t2.teamID and t1.yearID=t2.yearID and t1.masterID < t2.masterID
order by yearID, teamID, id1, id2


-- Formatting everything
select tr2.year, tr2.teamName, tr2.nameFirst, tr2.nameLast, tr2.triples, m2.nameFirst as teammateFirstName, m2.nameLast as teammateLastName, tr2.triples2 as teammateTriples
from master m2,
(select tr.year, tr.name as teamName, m1.nameFirst, m1.nameLast, tr.triples1 as triples, tr.id2, tr.triples2
from master m1,
(select tM.yearID as year, t.name, id1, id2, triples1, triples2
from triplemates tM
join teams as t on t.teamID = tM.teamID and t.yearID = tM.yearID) as tr
where m1.masterID = tr.id1) as tr2
where m2.masterID = tr2.id2

-- QUERY 12 - Ranking the teams
-- Rank each team in terms of the winning percentage (wins divided by (wins + losses)) over its entire history.
-- Consider a "team" to be a team with the same name, so if the team changes name,it is considered to be two different teams. Show the team name, win percentage, and the rank.

-- determine winrate for all teams
create table data as
select name, sum(W) as wins, sum(L) as losses, sum(W)/(sum(W)+sum(L)) as winrate
from teams
group by name
order by winrate desc

-- rank teams by using a variable rank.
set @rank =0;
select d.name, @rank:=@rank+1 as rank, d.winrate as winPercentage, d.wins, d.losses
from data d
-- QUERY 13 - Pitchers for Mangaer Casey Stengel
-- List the year, first name, and last name of each pitcher who was a on a team managed by Casey Stengel (pitched in the same season on a team managed by Casey).
-- get all teams casey managed
create table casey as
select masterID, teamID, yearID from managers
where masterID = (select masterID
from master 
where nameFirst = 'Casey' and nameLast = 'Stengel')

-- get all pitchers that were on the teams casey managed
create table casey2 as
select c.*, p.masterID as playerID
from pitching p
join casey c on p.teamID = c.teamID and p.yearID = c.yearID

-- use the master IDs from pitchers to get their data
select t.name,c.yearID as year,mPlay.nameFirst,mPlay.nameLast, mCas.nameFirst, mCas.nameLast
from casey2 c
join master mCas on c.masterID = mCas.masterID
join master mPlay on c.playerID = mPlay.masterID
join teams t on t.teamID = c.teamID and c.yearID = t.yearID
group by c.yearID, t.name,mPlay.nameFirst

-- QUERY 14 - Two degrees from Yogi Berra

-- store all teams, and the years yogi played in those teams
create table yogiBoye as
select teamID, yearID, masterID as nope
from appearances
where masterID = (select masterID
from master
where nameFirst = 'Yogi' and nameLast = 'Berra')

-- get every player that played with yogi
create table degOne as
select distinct a.masterID as ID
from appearances a
join yogiBoye y on a.yearID = y.yearID and a.teamID = y.teamID and a.masterID != y.nope

-- get every team any player who played with yogi played in at any time.
create table degOneBoyes as
select distinct teamID, yearID
from appearances
where masterID in(select ID from degOne)

-- get every player these players played with
create table degTwo as
select distinct a.masterID as ID
from appearances a
join degOneBoyes d on a.yearID = d.yearID and a.teamID = d.teamID

-- get all distinct players who played here. Except yogi
select distinct m.nameFirst, m.nameLast
from degTwo d
join master m on d.ID = m.masterID and nameFirst != 'Yogi' and nameLast != 'Berra'
order by nameLast

-- QUERY 15 - Median team wins
-- calculate all wins for teams in the NL region in the 70s
create table q15 as
select teamID, sum(W) as totalWins
from teams
where lgID = 'NL' and yearID>=1970 and yearID<=1979
group by teamID

-- find median team based on their rank
create table q15a as
SELECT x.teamID, COUNT(*)/2 as rank
from q15 x, q15 y
GROUP BY x.totalWins
HAVING SUM(SIGN(1-SIGN(y.totalWins-x.totalWins))) = COUNT(*)/2

-- formatting based on data acquired
alter table q15a modify rank integer

select distinct t.name, q.rank
from q15a q
join teams t on q.teamID = t.teamID and yearID>=1970 and yearID<=1979