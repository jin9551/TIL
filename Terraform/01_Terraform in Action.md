## Terraform in Action

#### Terraform plan

* Terraform plan previews the changes Terraform will make, such as creating an AWS EC2 instance, showing details like AMI and instance type.

* The plan links directly to the Terraform code, which defines the infrastructure resources and their configurations.

* This process helps you see what infrastructure will be created or changed before applying it, making infrastructure management safer and more predictable.

#### Details ft. Gemini

Terraform에서 **`terraform plan`**은 코드로 정의한 인프라 설정이 실제 환경에 어떻게 적용될지 미리 보여주는 **'실행 계획 확인'** 단계입니다.

쉽게 비유하자면, 건물을 짓기 전 설계도를 보고 "어디에 기둥을 세우고, 어디에 벽을 허물지" 미리 시뮬레이션해 보는 과정이라고 할 수 있습니다.

---

## 1. 주요 역할
`terraform plan`은 크게 세 가지 역할을 수행합니다.

* **상태 비교 (Diff):** 현재 운영 중인 인프라 상태(`State` 파일)와 사용자가 새로 작성한 코드(`Configuration`)를 비교합니다.
* **변경 사항 예측:** 어떤 리소스가 **추가(+)**, **수정(~)**, 또는 **삭제(-)**될지 상세하게 나열합니다.
* **오류 검출:** 문법 오류나 설정상의 결함이 없는지 `apply` 전에 미리 확인하여 사고를 방지합니다.



## 2. 결과 읽는 법
명령어를 실행하면 터미널에 다음과 같은 기호들이 표시됩니다.

| 기호 | 의미 | 설명 |
| :--- | :--- | :--- |
| **`+`** | **Create** | 새로운 리소스를 생성함 |
| **`~`** | **Update** | 기존 리소스의 설정을 변경함 (데이터 유지) |
| **`-`** | **Destroy** | 기존 리소스를 삭제함 |
| **`-/+`** | **Replace** | 리소스를 삭제한 후 다시 생성함 (수정 불가능한 옵션 변경 시) |

---

## 3. 왜 중요한가요?
인프라 환경은 한 번 잘못 건드리면 서비스 전체에 장애가 발생할 수 있습니다. `plan` 명령어를 통해 다음과 같은 이점을 얻을 수 있습니다.

1.  **안전 장치:** 실제로 인프라를 변경하기 전, 의도치 않은 삭제나 변경이 포함되어 있지 않은지 검토할 수 있습니다.
2.  **협업 효율성:** 코드 리뷰 단계에서 `plan` 결과를 공유하여 팀원들과 변경 사항을 확정할 수 있습니다.
3.  **예측 가능성:** 인프라가 어떻게 변화할지 100% 확신한 상태에서 다음 단계(`apply`)로 넘어갈 수 있습니다.

---

**💡 한 줄 요약**
> "실제로 적용(`apply`)하기 전에 **미리보기**를 통해 사고를 방지하는 필수 절차"입니다.


#### Terraform run

* Applying Terraform plans in Terraform Cloud not only provisions resources but also tracks who applied changes and why, helping with accountability.

* After applying changes, you can verify the created AWS EC2 instance in the AWS console, including details like public IP and DNS.

* It's important to manage costs by terminating instances when they're no longer needed, which also deletes associated storage volumes.

* Terraform Cloud allows you to start new runs, detect changes like destroyed instances, and reapply configurations efficiently.


#### How Terraform works

* Terraform lets you define your cloud infrastructure as code, enabling flexible and efficient management of resources by allowing data sharing between them.

* It automatically figures out the correct order to create or modify resources using a directed acyclic graph, preventing cyclic dependencies.

* The execution plan feature shows exactly what changes Terraform will make before applying them, helping avoid unexpected disruptions in your infrastructure.


#### Terraform states

* Terraform keeps track of your AWS infrastructure using a state file, which records the current status of your resources.

* The state file is a JSON-formatted text file that can be found locally or viewed in Terraform Cloud's user interface.

* This state tracking allows Terraform to detect changes and plan updates accurately, making infrastructure management more reliable.

* Reviewing state files from previous runs helps troubleshoot issues by showing what changed over time.


#### Editing Terraform code

* You can efficiently edit Terraform code in GitHub using the github.dev editor to combine changes into a single commit, avoiding noisy multiple commits.

* Terraform variables allow you to make your infrastructure code more flexible; you can define variables and then reference them in your resource configurations.

* After committing changes, Terraform Cloud automatically runs a plan to show what will change in your infrastructure, helping you review before applying.

* Terraform Cloud provides a user-friendly interface to monitor runs, see plans, and confirm and apply changes, which then updates your AWS infrastructure accordingly.


#### Understanding Terraform errors

* Terraform provides detailed error messages that help identify issues like undeclared resources or syntax errors in your code.

* Errors are shown clearly in Terraform Cloud, making it easier to troubleshoot and fix problems by editing and committing code changes.

* Fixing errors involves updating your Terraform files (like outputs.tf) and committing those changes to trigger new Terraform runs.

* Terraform's error messages often include helpful hints, such as pointing out missing braces or incorrect references, which aids in quick resolution.