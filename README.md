# jwt-practice
## 작업 환경
- Java 11
- Gradle 6.8.3
- Spring Tool Suite 4
- MySQL 8.0.23

## 파일 구조
#####  Debugging에 직접적으로 필요한 파일 및 디렉토리에 ![#f03c15](https://via.placeholder.com/15/f03c15/000000?text=+) 를 달아놓았습니다. 해당 구조가 빠른 디버깅에 도움이 되기를 바랍니다!
src/main/java
  - com/caterpie
    - timeletter
      - ![#f03c15](https://via.placeholder.com/15/f03c15/000000?text=+) `config` : jwt 강의 내 config 파일들과 swagger 사용을 위한 config 파일 포함
      - controller
        - ![#f03c15](https://via.placeholder.com/15/f03c15/000000?text=+) `UserController` : Spring Security JWT를 이용한 로그인 및 회원관리를 위한 컨트롤러
        - ClubController : 복수의 회원들에 대한 클럽 기능 제공을 위한 컨트롤러
        - SaltSHA256 : 암호를 SaltSHA256으로 해시하기 위한 파일. 현재 미사용
      - dto
        - JoinDto : 회원 가입을 위한 Dto 객체
        - ![#f03c15](https://via.placeholder.com/15/f03c15/000000?text=+) `LoginDto` : 로그인을 위한 Dto 객체 (이메일과 패스워드를 이용)
        - TokenDto : 토큰 생성을 위한 Dto 객체
        - UserDto : 유저 정보를 조회하기 위한 Dto 객체
        - UserModifyDto : 유저 정보를 업데이트 하기 위한 Dto 객체
      - entity
        - User : 사용자 엔티티 (Club 테이블과 단방향 OneToOne 조인 및 user_authority 테이블로부터 양방향 조인)
        - Club : 클럽 엔티티
        - Authority: 권한 설정을 위한 엔티티
      - jwt
        - ![#f03c15](https://via.placeholder.com/15/f03c15/000000?text=+) `JwtFilter`
        - ...
      - repository : JPA 사용을 위한 레포지토리
      - service
        - ClubService, ClubServiceImpl : 클럽 객체를 사용한 비즈니스 로직 구현 인터페이스 및 객체
        - UserService, UserServiceImpl : 유저 객체를 사용한 비즈니스 로직 구현 인터페이스 및 객체
        - ![#f03c15](https://via.placeholder.com/15/f03c15/000000?text=+) `CustomUserDetailsService`

src/main/resources
  - ![#f03c15](https://via.placeholder.com/15/f03c15/000000?text=+) `sql_files` : DB 세팅을 위한 SQL 파일들
    - timeletter_user.sql : timeletter 스키마 생성 후 유저 테이블 생성 덤프 파일
    - timeletter_authority.sql : 권한 테이블 생성 덤프 파일
    - timeletter_user_authority.sql : 관계 테이블 생성 덤프 파일
