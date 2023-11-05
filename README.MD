## Git Rule

### 커밋 룰
- 선 이슈 생성 & 후 개발

  ex) `git commit -m "feat: ~~~"`
    - `feat` 기능 구현
    - `refactor` 리팩토링
    - `fix` 버그 수정
    - `test` 테스트코드
    - `config` 빌드, 패키지, 환경변수 등의 수정
    - `comment` 주석 추가 및 변경
    - `docs` 문서 수정
    - `rename` 파일/폴더명 수정
    - `remove` 파일/폴더 삭제
    - `etc` → 그 외

### 브랜치 룰
- `[이름 이니셜]/[수행한 작업]` 

  ex) `kms/select-join-fix`

### Merge 룰
- `개인브랜치 → (PR) → develop`, `develop → main` 방향으로만 머지한다