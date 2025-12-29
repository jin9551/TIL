**Caffeine**은 Java 8 이상을 위해 설계된 **고성능 로컬 캐시 라이브러리**입니다. Google의 Guava Cache 제작자가 그 경험을 바탕으로 새롭게 설계했으며, 현재 Java 생태계에서 로컬 캐시가 필요할 때 가장 먼저 고려되는 표준적인 라이브러리입니다.

---

## 1. 주요 특징 및 장점

* **압도적인 성능**: 내부적으로 **W-TinyLFU** 알고리즘을 사용하여 데이터 적중률(Hit Rate)을 극대화하면서도 메모리 사용량은 최소화합니다.
* **다양한 제거(Eviction) 정책**:
* **크기 기반**: 최대 항목 수 또는 무게(weight)를 설정할 수 있습니다.
* **시간 기반**: 마지막 기록(write) 또는 마지막 접근(access) 후 일정 시간이 지나면 삭제합니다.
* **참조 기반**: JVM의 Garbage Collection에 의해 제거될 수 있도록 Soft Reference나 Weak Reference를 지원합니다.


* **비동기 지원**: `AsyncLoadingCache`를 통해 값을 비동기로 로드하고 `CompletableFuture`를 반환받을 수 있어 논블로킹(Non-blocking) 애플리케이션에 적합합니다.
* **통계 제공**: 캐시 적중률, 로드 시간, 제거 횟수 등의 데이터를 실시간으로 수집하여 모니터링할 수 있습니다.

## 2. Guava Cache와의 차이점

Caffeine은 Guava Cache의 정신적 후속작으로 불리며, 성능과 API 면에서 개선되었습니다.

| 구분 | Guava Cache | Caffeine Cache |
| --- | --- | --- |
| **알고리즘** | LRU (Least Recently Used) 기반 | **W-TinyLFU** (빈도+최신성 통합) |
| **성능** | 보통 | **매우 높음** (동시성 처리 최적화) |
| **Java 버전** | Java 6 이상 | Java 8 이상 |
| **비동기 로딩** | 미지원 (일반 Future) | **지원 (CompletableFuture)** |

## 3. 기본 사용법 (Java/Spring)

### 의존성 추가 (Gradle)

```gradle
implementation 'com.github.ben-manes.caffeine:caffeine:3.1.8'

```

### 캐시 생성 예시

```java
Cache<String, Object> cache = Caffeine.newBuilder()
    .maximumSize(10_000) // 최대 1만 개 항목 저장
    .expireAfterWrite(Duration.ofMinutes(5)) // 쓰기 후 5분 뒤 만료
    .recordStats() // 통계 수집 활성화
    .build();

// 값 저장
cache.put("key", "value");

// 값 조회 (없으면 null 반환)
Object val = cache.getIfPresent("key");

// 값 조회 (없으면 생성해서 반환)
Object result = cache.get("key", k -> "new_value");

```

## 4. 언제 사용하나요?

* **로컬 캐시**: Redis 같은 외부 저장소 없이 애플리케이션 메모리 내에서 빠르게 데이터를 관리하고 싶을 때.
* **Spring Boot**: Spring의 `@Cacheable` 추상화와 결합하여 매우 쉽게 적용 가능합니다.
* **높은 처리량**: 동시 접근이 많은 고성능 백엔드 서버에서 데이터베이스 부하를 줄이고 싶을 때.

---

Spring Boot에서 Caffeine 캐시를 활용하기 위해 `@Cacheable` 어노테이션을 사용하는 방법과 데이터 흐름에 대해 설명해 드리겠습니다.

---

## 1. Spring Boot 설정 (Caffeine 연동)

먼저, `Caffeine` 라이브러리를 의존성에 추가한 후 캐시 매니저를 설정해야 합니다.

### 의존성 추가 (Gradle)

```gradle
implementation 'org.springframework.boot:spring-boot-starter-cache'
implementation 'com.github.ben-manes.caffeine:caffeine'

```

### 캐시 설정 (Configuration)

`@EnableCaching`을 설정 클래스에 추가하여 캐시 기능을 활성화합니다.

```java
@Configuration
@EnableCaching
public class CacheConfig {
    @Bean
    public CacheManager cacheManager() {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager("userCache", "postCache");
        cacheManager.setCaffeine(Caffeine.newBuilder()
                .expireAfterWrite(10, TimeUnit.MINUTES) // 10분 후 만료
                .maximumSize(1000)); // 최대 1000개 저장
        return cacheManager;
    }
}

```

---

## 2. @Cacheable 메서드 정의 및 데이터 흐름

`@Cacheable`은 메서드의 결과를 캐시에 저장합니다. 동일한 인자로 메서드가 호출되면, **실제 메서드 로직을 실행하지 않고 캐시된 값을 반환**합니다.

### 메서드 정의 예시

```java
@Service
public class UserService {

    // "userCache"라는 이름의 캐시에 저장하며, 파라미터 id를 키로 사용함
    @Cacheable(value = "userCache", key = "#id")
    public User getUserById(Long id) {
        // 이 로그는 캐시에 데이터가 없을 때만 출력됩니다.
        System.out.println("데이터베이스에서 사용자 조회 중: " + id);
        return userRepository.findById(id).orElseThrow();
    }
}

```

---

## 3. 캐시된 데이터를 활용하고 관리하는 방법

데이터를 단순히 읽는 것 외에도, 데이터가 변경되었을 때 캐시를 갱신하거나 삭제하는 과정이 중요합니다.

### A. @CachePut: 캐시 갱신

데이터를 업데이트할 때 사용합니다. 메서드 로직을 항상 실행하고 그 결과를 캐시에 업데이트합니다.

```java
@CachePut(value = "userCache", key = "#user.id")
public User updateUser(User user) {
    return userRepository.save(user);
}

```

### B. @CacheEvict: 캐시 삭제

데이터가 삭제되거나 유효하지 않게 되었을 때 캐시를 비웁니다.

```java
@CacheEvict(value = "userCache", key = "#id")
public void deleteUser(Long id) {
    userRepository.deleteById(id);
}

// 해당 캐시의 모든 데이터를 한 번에 비우고 싶을 때
@CacheEvict(value = "userCache", allEntries = true)
public void clearAllCaches() {}

```

---

## 4. 요약 및 주의사항

| 기능 | 어노테이션 | 설명 |
| --- | --- | --- |
| **조회 및 저장** | `@Cacheable` | 캐시에 있으면 반환, 없으면 메서드 실행 후 캐시에 저장 |
| **갱신** | `@CachePut` | 메서드 실행 결과를 무조건 캐시에 저장 (업데이트) |
| **삭제** | `@CacheEvict` | 지정된 키 혹은 전체 캐시 데이터 삭제 |

> **주의**:
> 1. **Proxy 한계**: 같은 클래스 내의 메서드끼리 호출할 때는 `@Cacheable`이 동작하지 않습니다. (Spring AOP는 외부 호출만 가로챕니다.)
> 2. **객체 직렬화**: 외부 저장소(Redis 등)를 쓸 때는 객체가 `Serializable`을 구현해야 하지만, Caffeine 같은 로컬 캐시는 JVM 힙 메모리를 직접 쓰므로 필수사항은 아닙니다.
> 
> 

---

서비스 클래스에서 `@Cacheable` 같은 어노테이션을 사용하지 않고, **코드로 직접(Programmatically)** 캐시에 접근하여 데이터를 가져오거나 조작해야 할 때가 있습니다.

이때는 Spring이 제공하는 `CacheManager`를 주입받아 사용합니다.

---

## 1. CacheManager를 이용한 직접 접근

가장 일반적인 방법은 `CacheManager` 인터페이스를 통해 특정 이름의 캐시 인스턴스를 가져오는 것입니다.

```java
@Service
@RequiredArgsConstructor // 생성자 주입을 위한 Lombok 어노테이션
public class UserCacheService {

    private final CacheManager cacheManager;

    public User getCachedUser(Long id) {
        // 1. "userCache"라는 이름의 캐시 저장소를 가져옴
        Cache cache = cacheManager.getCache("userCache");

        if (cache != null) {
            // 2. 캐시에서 특정 키(id)로 값을 조회
            // .get(key, type)을 사용하면 자동으로 형변환까지 해줍니다.
            User user = cache.get(id, User.class);
            
            if (user != null) {
                return user;
            }
        }
        
        // 3. 캐시에 없다면 로직 수행 (예: DB 조회 후 수동 저장)
        return fetchAndCacheUser(id);
    }

    private User fetchAndCacheUser(Long id) {
        User user = userRepository.findById(id).orElseThrow();
        
        // 캐시에 수동으로 저장
        Cache cache = cacheManager.getCache("userCache");
        if (cache != null) {
            cache.put(id, user);
        }
        return user;
    }
}

```

---

## 2. Caffeine 직접 주입 (고급 활용)

Spring의 추상화 계층(`CacheManager`)을 거치지 않고, Caffeine 라이브러리의 기능을 온전히 사용하고 싶을 때는 `Caffeine` 객체 자체를 빈으로 등록해 직접 주입받을 수도 있습니다.

### 설정 (Config)

```java
@Configuration
public class NativeCacheConfig {
    @Bean
    public Cache<Long, User> userNativeCache() {
        return Caffeine.newBuilder()
                .maximumSize(1000)
                .build();
    }
}

```

### 서비스 계층에서의 사용

```java
@Service
public class NativeService {
    private final Cache<Long, User> userNativeCache;

    public NativeService(Cache<Long, User> userNativeCache) {
        this.userNativeCache = userNativeCache;
    }

    public User getUser(Long id) {
        // get(key, mappingFunction)은 Caffeine의 강력한 기능 중 하나입니다.
        // 키가 없으면 두 번째 인자로 전달된 함수를 실행해 값을 계산하고 캐시에 넣은 뒤 반환합니다.
        return userNativeCache.get(id, key -> userRepository.findById(key).orElseThrow());
    }
}

```

---

## 3. 어떤 방법을 선택해야 할까요?

* **`CacheManager` 사용**: 표준적인 방법입니다. 나중에 Caffeine에서 Redis 등 다른 캐시 엔진으로 교체하더라도 서비스 코드를 수정할 필요가 없어 **유지보수**에 유리합니다.
* **Caffeine `Cache` 직접 사용**: Caffeine의 특수한 기능(고급 통계, 세밀한 비동기 처리 등)이 꼭 필요하거나, Spring Cache 추상화가 제공하지 않는 복잡한 로직을 구현할 때 사용합니다.

---

### 번역 (Translation)

**In English:**
To access the cache programmatically in a service class, you can inject the `CacheManager` bean. Use `cacheManager.getCache("cacheName")` to retrieve the cache instance and then call `.get(key, type)` or `.put(key, value)` to manage data. Alternatively, for more advanced features, you can directly inject a Caffeine `Cache` object defined as a Bean.

---
`SimpleCacheManager`는 Spring이 제공하는 가장 기본적인 캐시 매니저 구현체입니다. **CaffeineCacheManager**와 비교했을 때 가장 큰 차이점은 **"자동화 여부"**와 **"기능의 범위"**입니다.

---

## 1. 주요 차이점 비교

| 구분 | CaffeineCacheManager | SimpleCacheManager |
| --- | --- | --- |
| **성격** | Caffeine 라이브러리에 특화된 매니저 | 범용적인 캐시 컨테이너 (단순 저장소) |
| **자동 생성** | 캐시 이름만 지정하면 설정값에 따라 자동 생성 | **사용할 캐시 객체를 직접 생성해서 주입해야 함** |
| **기능** | 만료(Expire), 크기 제한, 통계 등 고급 기능 제공 | 주입된 캐시 저장소의 기본 기능에만 의존 |
| **유연성** | Caffeine 설정으로 모든 캐시 통제 | 서로 다른 종류의 캐시(Caffeine, ConcurrentMap 등)를 혼합 가능 |

---

## 2. SimpleCacheManager 설정 방법

`SimpleCacheManager`는 스스로 캐시를 만들지 못하기 때문에, 개발자가 `CaffeineCache`나 `ConcurrentMapCache` 같은 구체적인 캐시 객체를 생성해서 리스트로 넘겨줘야 합니다.

```java
@Configuration
@EnableCaching
public class CacheConfig {

    @Bean
    public CacheManager cacheManager() {
        SimpleCacheManager cacheManager = new SimpleCacheManager();

        // 1. 각 캐시 저장소를 수동으로 생성하고 설정함
        CaffeineCache userCache = new CaffeineCache("userCache", 
            Caffeine.newBuilder()
                .expireAfterWrite(10, TimeUnit.MINUTES)
                .maximumSize(100)
                .build());

        CaffeineCache productCache = new CaffeineCache("productCache", 
            Caffeine.newBuilder()
                .expireAfterWrite(1, TimeUnit.HOURS)
                .maximumSize(500)
                .build());

        // 2. 생성한 캐시들을 리스트 형태로 등록
        cacheManager.setCaches(Arrays.asList(userCache, productCache));
        
        return cacheManager;
    }
}

```

---

## 3. 언제 SimpleCacheManager를 쓰나요?

1. **캐시별로 설정이 완전히 다를 때**: 어떤 캐시는 10분 만료, 어떤 캐시는 1시간 만료, 어떤 캐시는 크기 제한 없음 등 각각의 캐시 전략이 매우 다를 경우 유용합니다.
2. **혼합 캐시 구성**: `CaffeineCache`와 Spring 기본 `ConcurrentMapCache`를 하나의 매니저 안에서 동시에 관리하고 싶을 때 사용합니다.
3. **명시적 관리**: 애플리케이션에서 사용할 캐시들을 코드상에 명확히 정의(White-list)해두고 싶을 때 사용합니다. (존재하지 않는 캐시 이름을 호출하면 에러가 발생하므로 실수를 방지할 수 있습니다.)

---

## 4. 요약 및 선택 가이드

* **대부분의 경우**: `CaffeineCacheManager`를 사용하세요. 설정이 간결하고, 캐시를 동적으로 생성하기 편합니다.
* **세밀한 제어가 필요할 때**: 캐시마다 만료 시간이나 크기 제한을 다르게 가져가야 한다면 `SimpleCacheManager`를 사용하여 각각의 `Caffeine` 인스턴스를 수동으로 등록하는 것이 좋습니다.

---

### 번역 (Translation)

**In English:**
The main difference is automation. `CaffeineCacheManager` automatically creates caches based on a common configuration, while `SimpleCacheManager` requires you to manually instantiate and register each cache object (e.g., `CaffeineCache`). Use `SimpleCacheManager` when you need highly specific, different settings for each cache store or when mixing different cache implementations.

---