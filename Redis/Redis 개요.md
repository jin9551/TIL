# Redis란?

Redis란 Remote Dictionary Server의 약자로 Key-Value(키-값) 구조의 비정형 데이터를 저장하고 관리하기 위한 오픈 소스 기반의 비관계형 데이터베이스 관리 시스템이다(DBMS).

> Redis는 데이터처리 속도가 엄청 빠른 NoSQL 데이터베이스이다.

## Redis의 장점

* 레디스는 인메모리(in-memory)에 모든 데이터를 저장한다. 그래서 데이터의 처리 성능이 굉장히 빠르다.

MySQL과 같은 RDBMS의 데이터베이스는 대부분 디스크에 데이터를 저장한다. 하지만 Redis는 메모리(RAM)에 데이터를 저장한다. 디스크보다 메모리에서의 데이터 처리속도가 월등하게 빠르다. 이 때문에 Redis의 데이터 처리 속도가 RDBMS에 비해 훨씬 빠르다.



## Redis 주요 사용 사례

Redis의 사용 사례를 검색해보면 아주 다양한다.

* **캐싱 (Caching)**

* 세션 관리 (Session Management)

* 실시간 분석 및 통계 (Real-time Analystics)

* 메시지 큐 (Message Queue)

* 지리공간 인덱싱 (Geospatial Indexing)

* 속도 제한 (Rate Limiting)

* 실시간 채팅 및 메시징 (Real-time Chat And Messaging)


레디스에 내장된 기능이 다양하다보니 여러 용도로 사용된다.
이 모든 사례를 익히려면 막막할 수 밖에 없다.
그러므로 파레토의 법칙에 의해 현업에서 많이 사용되는 **'캐싱'(데이터 조희 성능 향상)**만 집중적으로 살펴 볼 것이다.



