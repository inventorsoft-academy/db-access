# spring-boot-task
Home task to learn how to use Spring Boot

## Task description

Create application that generates https://en.wikipedia.org/wiki/Bracket_(tournament). Application should support the following features:
1) Create a team: team name, capitan, coach
2) Create a tournament bracket: select included teams registered on step 1. Amount of teams should be power of 2 and at least 4(4, 8, 16, 32, etc). Tournament bracket should randomly distribute teams between each other;
3) You should be able to post match result(e.g. team X defended team Y with score 1:0);
4) You should be able to print tournament winner when tournament is complete;
5) Upcoming matches should be printable to console;
6) All results for specific tournament should be exportable in CSV format: 

```
Round, Team 1, Team 2, Score

1/4, Sunshine, Flower, 2:0
```

 The user interface should be console;
 
**Technical details:** application should use Spring Boot and benefit from Dependency Injection. Data should be stored on filesystem without usage of database engine (you free to choose format)

## Presentation
https://docs.google.com/presentation/d/1JlPmUVv0KJJ9zaHtqg8tWhgckUlFg3uzJcnDiuuVt3c/edit?usp=sharing
