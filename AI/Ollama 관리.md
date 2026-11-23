# Ollama

brew services로 실행한 Ollama는 간단한 명령어 한 줄로 즉시 안전하게 종료할 수 있으며, 로그 용량 역시 수 기가바이트씩 무제한으로 쌓여 드라이브를 채우지 않도록 관리할 수 있습니다.

0. 백그라운드 Ollama 실행 
    brew services start ollama

0-1. 터미널에서 수동 실행
    ollama serve
    
1. 백그라운드 Ollama 서비스 종료하기

1. 서비스 중지 명령어 입력:
   brew services stop ollama
   
  ⚬ 확인 방법: 터미널에 Stopping ollama... (success) 메시지가 출력되면 정상 종료된 것입니다.
2. 완전 종료 여부 확인:
   brew services list
   
  ⚬ 확인 방법: 목록에서 ollama의 상태(Status)가 none 또는 stopped로 표시되고, curl http://localhost:11434를 쳤을 때 Failed to connect가 나오면 완전히 꺼진 상태입니다.

3. 모델만 종료
ollama stop qwen-meeting


2. 로그가 계속 쌓여서 용량이 꽉 차지 않을까?

결론부터 말씀드리면 기본적으로 드라이브가 찰 정도로 커지지는 않습니다.

⚬ 로그 저장 위치:
  Homebrew 서비스 로그는 보통 아래 경로에 텍스트 형태로 저장됩니다.
  ⚬ 표준 출력 로그: /opt/homebrew/var/log/ollama.log (또는 /usr/local/var/log/ollama.log)
  ⚬ 에러 로그: /opt/homebrew/var/log/ollama.err.log
⚬ 실제 용량:
  텍스트 파일이기 때문에 몇 주 동안 켜두어도 수십~수백 MB 수준에 불과합니다. 또한 모델 파일(~/.ollama/models)과 달리 로그 자체가 기가바이트 단위로 급증하는 일은 드뭅니다.

3. 현재 로그 용량 확인 및 안전하게 비우는 법

혹시라도 찜찜하거나 용량을 직접 확인하고 싶다면 아래 단계로 점검할 수 있습니다.

1. 현재 로그 파일 용량 확인:
   ls -lh /opt/homebrew/var/log/ollama*
   
  ⚬ (인텔 Mac의 경우 /usr/local/var/log/ollama*)
2. 로그 파일 내용 비우기 (파일 삭제 없이 0바이트로 초기화):
   cat /dev/null > /opt/homebrew/var/log/ollama.log
   cat /dev/null > /opt/homebrew/var/log/ollama.err.log
   
  ⚬ 확인 방법: 다시 ls -lh /opt/homebrew/var/log/ollama*를 입력했을 때 파일 크기가 0B로 줄어들었는지 확인합니다.

참고: 평소에 필요할 때만 Ollama를 띄워 쓰고 싶다면, brew services로 백그라운드에 계속 띄워두지 않고 사용할 때만 터미널 창 하나에서 ollama serve를 켜두고 작업이 끝나면 Ctrl + C로 바로 꺼버리는 방식을 사용하셔도 좋습니다.



# 1. Flash Attention 활성화 (긴 회의록 읽는 속도 대폭 향상)
launchctl setenv OLLAMA_FLASH_ATTENTION 1

# 2. KV 캐시를 8비트로 압축 (32k/16k 컨텍스트 메모리 사용량 50% 절감)
launchctl setenv OLLAMA_KV_CACHE_TYPE q8_0

# 3. 모델을 1시간 동안 메모리에 유지 (중간에 질문해도 즉시 응답)
launchctl setenv OLLAMA_KEEP_ALIVE 1h



----
### Model not found

model 'qwen-meeting:latest' not found 에러는 현재 실행 중인 Ollama 서버(localhost:11434)에서 해당 모델을 찾지 못할 때 발생합니다. 주로 모델이 삭제되었거나, Ollama.app과 Homebrew 백그라운드 서비스(brew services)가 충돌했을 때 생깁니다.

아래 순서대로 터미널에서 확인하고 조치해 보세요.

1. 현재 Ollama에 모델이 남아있는지 확인하기
   터미널에 아래 명령어를 입력합니다.
   ollama list
   
   확인: 출력 목록에 qwen-meeting:latest가 있는지 확인합니다.
2. 목록에 qwen-meeting:latest가 없다면 다시 생성하기
   기존에 만들어 둔 Modelfile로 모델을 바로 복구합니다.
   ollama create qwen-meeting -f ~/Modelfile-meeting
   
   (만약 no such file or directory 에러가 나면 아래 명령어로 파일부터 다시 생성합니다.)
   cat << 'EOF' > ~/Modelfile-meeting
   FROM qwen3:14b
   PARAMETER num_ctx 32768
   PARAMETER temperature 0.2
   SYSTEM "You are a bilingual meeting assistant. Always respond in the exact same language as the user's prompt. Keep technical terms in English."
   EOF
   ollama create qwen-meeting -f ~/Modelfile-meeting
   
   확인: ollama list를 다시 실행했을 때 qwen-meeting:latest가 목록에 표시되어야 합니다.
3. 목록에는 있는데도 에러가 난다면 Ollama 서버 충돌 정리하기
   맥북 상단 메뉴바에 라마 아이콘(Ollama.app)이 떠 있다면 종료(Quit)한 뒤, Homebrew 서비스만 단독으로 재시작합니다.
   brew services restart ollama
   
   확인: 터미널에 curl http://localhost:11434/api/tags를 입력했을 때, 응답 JSON 안에 "qwen-meeting:latest"가 포함되어 출력되는지 확인합니다.
4. MacWhisper에서 모델 다시 불러오기
   MacWhisper에서 Settings (Cmd + ,) → AI → Services로 이동해 qwen-meeting:latest 항목을 클릭한 뒤 모델 목록을 새로고침(Refresh)하거나, MacWhisper를 완전히 종료(Cmd + Q)했다가 다시 실행합니다.
   확인: 우측의 Summarize 버튼을 눌렀을 때 에러 팝업 없이 요약이 정상적으로 시작되는지 확인합니다.