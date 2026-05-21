# Route53


#### Hosted Zone Record Type

Route53의 Hosted Zone에서 **A와 AAAA 레코드 타입**은 도메인 이름을 IP 주소로 변환하는 DNS 레코드입니다.

## A 레코드 (Address Record)
- **IPv4 주소**로 매핑하는 레코드
- 예: `example.com` → `192.0.2.1`
- 가장 일반적인 DNS 레코드 타입
- 웹사이트, 메일 서버 등 대부분의 서비스에 사용

## AAAA 레코드
- **IPv6 주소**로 매핑하는 레코드
- 예: `example.com` → `2001:0db8:85a3:0000:0000:8a2e:0370:7334`
- IPv6 기반 인터넷 환경을 지원하기 위한 레코드
- 현대적인 네트워크 인프라에서 점점 더 중요해지고 있음

## 차이점 비교

| 항목 | A 레코드 | AAAA 레코드 |
|------|---------|-----------|
| **IP 버전** | IPv4 | IPv6 |
| **주소 길이** | 32비트 (4옥텟) | 128비트 (8개 그룹) |
| **표기법** | 192.0.2.1 | 2001:db8::1 |
| **사용 현황** | 매우 광범위 | 점진적 증가 |

## Route53에서의 역할
Hosted Zone에서 이 레코드들을 설정하면, 사용자가 도메인 이름으로 접속할 때 Route53이 적절한 IP 주소로 쿼리를 응답하여 트래픽을 올바른 대상으로 라우팅합니다.

--- 

#### Route53에 연결된 AWS 서비스 탐색 방법

### 1. **AWS Management Console에서 확인**
- Route53 → Hosted Zones → 해당 도메인 선택
- 레코드 목록에서 각 레코드의 **"값(Value)"** 또는 **"라우팅 대상(Routing policy)"** 확인
- 레코드 타입과 값을 통해 연결된 서비스를 파악 가능

### 2. **Alias 레코드인 경우**
Route53의 **Alias** 기능을 사용하면 AWS 서비스에 직접 연결됩니다:

```
레코드명: example.com
타입: A
라우팅 대상: ALB (Application Load Balancer)
  → arn:aws:elasticloadbalancing:region:account:loadbalancer/...
```

**Alias로 연결 가능한 AWS 서비스:**
- **ALB/NLB** (Application/Network Load Balancer)
- **CloudFront** 배포
- **API Gateway**
- **Elastic Beanstalk** 환경
- **S3** 웹 호스팅 버킷
- **AppSync**
- **VPC 엔드포인트**

### 3. **일반 값(Non-Alias)인 경우**
- IP 주소나 도메인 이름 직접 입력
- AWS 서비스뿐 아니라 외부 서비스도 가능
- 값 자체를 보고 어떤 서버/서비스인지 파악해야 함

## AWS CLI로 확인

```bash
# Hosted Zone의 모든 레코드 조회
aws route53 list-resource-record-sets \
  --hosted-zone-id Z1234567890ABC \
  --region ap-northeast-2

# 결과에서 Type, Name, AliasTarget 등 확인
```

## 예시

```json
{
  "Name": "api.example.com",
  "Type": "A",
  "AliasTarget": {
    "HostedZoneId": "Z35SXDOTRQ7X7K",
    "DNSName": "d111111abcdef8.cloudfront.net",
    "EvaluateTargetHealth": false
  }
}
```
→ **CloudFront 배포로 연결됨**
