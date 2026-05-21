ooo은 CloudFront의 origin 이름인데, 이것이 정확히 어떤 AWS 서비스인지 확인하는 방법들이 있습니다.

## 확인 방법

### 1. **CloudFront 콘솔에서 직접 확인 (가장 확실)**
CloudFront 배포의 **Origins** 탭으로 가서:
- Origin name: `ooo` 찾기
- **Origin domain** 확인
- 여기서 실제 서비스 주소가 보임

**가능한 형식:**
- `xxx.s3.amazonaws.com` → **S3 버킷**
- `xxx.us-east-1.elb.amazonaws.com` → **ALB/NLB**
- `xxx.execute-api.us-east-1.amazonaws.com` → **API Gateway**
- `xxx.elasticbeanstalk.com` → **Elastic Beanstalk**
- IP 주소 → 온프레미스 또는 EC2 인스턴스

### 2. **AWS CLI로 확인**
```bash
# CloudFront 배포 ID 확인 (abc123) <- Route53에서 확인한 CloudFront value
aws cloudfront get-distribution-config \
  --id abc123 \
  --region us-east-1 | jq '.DistributionConfig.Origins'
```

결과에서 origin domain을 확인하면 서비스 타입을 알 수 있습니다.

### 3. **Behaviors 탭에서 추론**
현재 스크린샷에서:
- **Default (*)** 행의 origin: `ooo`
- 이름에 "api"가 있으므로 → **API Gateway** 또는 **ALB** 가능성 높음
- 지역이 `us-east-1`이고 `-fld` suffix는 아마 내부 환경 구분자인 듯

---

**가장 빠른 확인:**
CloudFront 배포 설정의 **Origins** 탭을 캡처해서 보여주면, origin domain을 보고 정확히 뭔지 알 수 있을 거 같습니다!
