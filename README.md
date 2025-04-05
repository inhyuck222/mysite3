**이름:** 임인혁

**경력:** 카페24, 7년차 (2018-09 ~ )

**이메일:** [i](mailto:johnsmith@example.com)nhyuck222apply@gmail.com

**학력**: 한성대학교, 컴퓨터공학과 (2011 - 2017)

**7년차 백엔드 개발자**로서, Spring Boot 기반 API 서버 개발과 레거시 시스템 전환, 확장성과 운영 효율을 고려한 시스템 설계 경험을 보유하고 있습니다.
**확장성과 유연성을 지향하는 개발자**
PHP 기반 시스템을 Spring Boot로 전환하고, Gradle 멀티 모듈과 MSA, 이벤트 기반 구조를 도입 했습니다. 서비스 간 결합도를 낮추고, 수평 확장을 고려한 구조 설계에 주력 했습니다.
**성능 병목을 개선하는 개발자**
Redis와 RabbitMQ 기반 메시지 처리 구조를 구성하고, 비동기 처리 및 캐싱 전략으로 트래픽 대응 기반을 마련했습니다. Opentelementry로 인증 필터 병목을 분석해 응답 지연을 개선 했습니다.
**협업과 표준화로 안정성을 높이는 개발자**
광고 생성 로직에 공통 인터페이스를 설계하고, Docker Compose로 개발 환경을 표준화했습니다. 타 API 개발자와 협업해 성능 개선과 운영 안정성을 확보했습니다.

### 보유 기술
---
- Spring Framework(Boot 3, Cloud, Scheduler, AMQP, Batch), JPA(Hibernate, Spring Data Jpa, Querydsl), MyBatis, Java(21), JavaScript
- PostgreSQL, RabbitMQ, MariaDB, MongoDB
- Linux(CentOS), Docker, k8s, Git(Gitlab)
- Confluence, Jira, Slack

### **경력: 카페24 (2018.09 ~ 현재)**
---
**오디언스(Audience) (2024.11 ~ 2025.02) - 개발팀: 8명, 기획팀: 1명, QA팀: 1명**
- 쇼핑몰 고객 데이터를 다양한 기준으로 그룹화(오디언스)하고, 맞춤형 메시지(SMS/앱푸시 등)를 효율적으로 발송하기 위한 프로젝트
**수행 업무 상세**
- Spring Boot(WebMVC, Batch, AMQP) 기반 애플리케이션을 k8s 환경에 배포할 수 있도록 구성
- JWT + Spring Security로 API 인증 로직을 구현
- Spring Batch 서버에서 쇼핑몰 데이터 서비스를 연동해, 그룹화된 정보를 주기적으로 수집 및 전처리
- RabbitMQ 기반 이벤트 처리 서버(Spring AMQP)를 구축해 대규모 SMS 발송 요청과 외부 시스템 연동을 비동기 방식으로 처리
**이슈 및 해결**
- 애플리케이션 구성 환경 개선: Gradle 멀티 모듈 도입으로 개발 생산성을 높이고 휴먼 에러 발생을 줄임
- 공통 개발 환경 표준화: Docker Compose를 활용해 팀 내 동일한 환경을 공유, 통일된 환경 공유로 안정성 향상
- API 응답 속도 개선: Opentelmentry 메타 데이터 분석 후 100ms가 추가로 발생하던 필터를 Redis 접근 방식으로 변경 후 20ms로 개선
- SMS 요청 지연: 페이징 쿼리 최적화(불필요한 COUNT/JOIN 제거)로 대규모 대상자 처리 시 응답 시간을 단축
- 배치잡 트랜잭션 점유 문제: @Scheduler 동시 실행 시 발생하는 충돌을 isolation 옵션 조정으로 완화

**카페24애즈 (2022.11 ~ 2024.08, 2024.03 ~ 2024.05) - 개발팀: 8명, 기획팀: 4명, QA팀: 4명**
- 광고 채널(메타, 구글, 틱톡)의 광고를 직접 운영할 수 있도록 지원하는 마케팅 통합 플랫폼
**수행 업무 상세**
- 코드 유지보수성 향상을 위해 광고 생성,수정 로직 리팩토링 (Spring AOP, Scheduler)
    - 광고 채널 추가 시, 생성·수정 클래스만 추가하면 자동으로 프레임워크에 연결되도록 인터페이스 설계
- 도메인별 관리를 위해 MSA 아키텍쳐 도입 (Spring Cloud Gateway)
- 광고 채널 API 응답 지연 문제 해결을 위해 이벤트 기반 아키텍쳐 도입 (RabbitMQ, Spring AMQP)
- GPT를 활용해 해외 자회사에서 개발된 광고 채널 API 랩핑 서버 이전 (PHP Laravel → Python FastAPI)
**이슈 및 해결**
- Spring Cloud Gateway에서 잘못된 Bean 사용으로 인한 GC 지연 해결
    - Grafana JVM 상태 대시보드, 덤프 파일로 Old Gen 영역에 장기간 남아있던 객체 분석

**페이스북 광고 모듈 (2020.10 ~ 2020.12, 2021.05 ~ 2021.07) - 개발팀 4명, 기획팀: 2명, 운영팀: 1명**
- 사내 마케팅센터 운영팀 어드민 서비스의 페이스북 광고 관리 서비스
- k8s 환경으로 서비스 이전 (서비스별 Dockerfile 파일 관리)
- Spring Cloud Gateway 도입
- 쇼핑몰 개발팀과 협업하여 일배치로 수집되던 쇼핑몰 데이터를 실시간 데이터로 개선
    - 쇼핑몰 이벤트 수신하여 상품 데이터 변경 발생시 수집되도록 개선
- 힙 메모리 분석으로 잘못 사용된 JPA(hibernate) 기능 수정 (in_caluse_parameter_padding 옵션 수정)
