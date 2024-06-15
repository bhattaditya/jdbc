-- joined in 2024
SELECT * FROM dbo.employee where date_of_joining > '2024-01-01';

-- HR department
SELECT * FROM dbo.employee where department = 'HR';

-- sum of all female employees salary
SELECT SUM(salary) FROM dbo.employee WHERE gender = 'Female';
