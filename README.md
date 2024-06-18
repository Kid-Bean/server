# 키드콩
간단한 문제 풀이를 통한 우리 아이 성장 발달 수준 확인 어플리케이션

# 🖥️ Introduce
이 프로젝트는 아이들이 흥미를 느끼며 자연스럽게 언어 발달을 할 수 있도록 다양한 활동을 제공하여 `어린이의 언어 발달을 촉진하기 위한 어플리케이션을 제작`하는 것을 목표로 시작하였습니다. 주요 기능으로는 `그림에 해당하는 단어를 맞추는 게임`과 `문장을 직접 말하고, 이를 바탕으로 형태소 분석을 해주는 기능`이 포함됩니다. 또한, 부모는 아이가 언어 발달을 어떤 수준에서 진행하고 있는지 쉽게 파악할 수 있도록, 앱을 사용하는 동 나이 대의 다른 아이들의 결과와 비교하여 아이의 언어 발달 상태를 확인할 수 있습니다.
  
## 🧚‍♀️ Member 
| 이동영<br/>([@leedy3838](https://github.com/leedy3838)) | 이현지<br/>([@Amepistheo](https://github.com/Amepistheo)) | 손유진<br/>([@syjdjr](https://github.com/syjdjr)) | 남기훈<br/>([@gikhoon](https://github.com/gikhoon))
| :---: | :---: | :---: | :---: |
| <img width="250" src="https://avatars.githubusercontent.com/u/43364585?v=4"/> | <img width="250" src="https://avatars.githubusercontent.com/u/110108243?v=4"/> | <img width="250" src="https://avatars.githubusercontent.com/u/81100851?v=4"/> | <img width="250" src="https://avatars.githubusercontent.com/u/52378919?v=4"/> |
| `퀴즈 풀이, 로그인, cicd 환경 구축`  | `퀴즈 등록, 수정, 삭제 기능 개발` | `프로그램 관련 기능 개발` | `마이페이지 기능 개발` |

## 🛠 Tech Stacks
<table>
   <tr><td>Language</td><td>Java 17</td></tr>
   <tr><td>Framework</td><td>Spring Boot, Gradle</td></tr>
   <tr><td>ORM</td><td>Spring Data JPA</td></tr>
   <tr><td>API Documentation</td><td>Swagger</td></tr>
   <tr><td>Database</td><td>MySQL</td></tr>
   <tr><td>Deploy</td><td>EC2, Docker, Github Action, Docker Compose, Nginx</td></tr>
</table>

### ERD
![image](https://github.com/Kid-Bean/server/assets/43364585/db1c0350-2e0f-4608-be16-90f014aad2ef)

### Infra Structure
![image](https://github.com/Kid-Bean/server/assets/43364585/43c2cac7-75ee-44a5-8ce4-70c7bc376715)

### 동작 화면
| 로그인 화면| 홈 화면 | 그림 맞추기 | 그림 맞추기 1세트 완료 후 |
| :---: | :---: | :---: | :---: |
| ![image](https://github.com/Kid-Bean/server/assets/43364585/3a71c7f4-90f0-4b86-981a-fe95e786fb79) | ![image](https://github.com/Kid-Bean/server/assets/43364585/a36e4d71-7b78-438e-9bf1-31ad59f31909) | ![image](https://github.com/Kid-Bean/server/assets/43364585/158e9d59-cf59-4fdd-864a-050bfc4421e2) | ![image](https://github.com/Kid-Bean/server/assets/43364585/384424e4-faed-426a-b11d-d8849754e248) |

| 단어 맞추기 | 단어 맞추기 1세트 완료 후 | 질문 대답하기 | 질문 대답하기 완료 후 |
| :---: | :---: | :---: | :---: |
| ![image](https://github.com/Kid-Bean/server/assets/43364585/7182842f-6cb7-45fd-9d66-d253a2a71e0f) | ![image](https://github.com/Kid-Bean/server/assets/43364585/c3c51cae-a1f8-40e4-be51-049f442f6014) | ![image](https://github.com/Kid-Bean/server/assets/43364585/2d0c32bf-c08e-41fc-856f-740a8a7a6d7e) | ![image](https://github.com/Kid-Bean/server/assets/43364585/d814e77a-bf63-48dd-9ab8-158c5526d851) |

| 프로그램 기본 화면 | 병원 프로그램 리스트 | 학습 프로그램 리스트 | 프로그램 상세 화면 |
| :---: | :---: | :---: | :---: |
| ![image](https://github.com/Kid-Bean/server/assets/43364585/54fb6be0-f3b3-4f4e-8d7a-9cfc87c079cc) | ![image](https://github.com/Kid-Bean/server/assets/43364585/50c50680-0a2c-4d0c-8570-75b9569464e3) | ![image](https://github.com/Kid-Bean/server/assets/43364585/f3bed563-a82b-4b95-9fda-deeb8abac24f) | ![image](https://github.com/Kid-Bean/server/assets/43364585/1f65b542-ea67-4bd6-bf54-d3d7456e126a) |

| 프로그램 등록 화면 | 프로그램 수정/삭제 화면 | 즐겨찾기 화면 |
| :---: | :---: | :---: |
| ![image](https://github.com/Kid-Bean/server/assets/43364585/36377101-6c6e-4483-ab60-3503e6dfa276) | ![image](https://github.com/Kid-Bean/server/assets/43364585/9228a1c3-c236-42dd-bdd6-2b19a7528837) | ![image](https://github.com/Kid-Bean/server/assets/43364585/36ce9abb-a5f6-4b18-a91e-35dd2dfab53f) |

| 마이페이지 기본 화면 | 개인 정보 수정 | 문제 풀이 분석 결과 | 내가 틀린 문제 |
| :---: | :---: | :---: | :---: |
| ![image](https://github.com/Kid-Bean/server/assets/43364585/4d3f30f0-17cf-45f7-b16d-04d7c0deddca) | ![image](https://github.com/Kid-Bean/server/assets/43364585/35a364a1-2e87-425b-a31b-d863dcef8d34) | ![image](https://github.com/Kid-Bean/server/assets/43364585/559a46ea-a717-4af5-aac4-6c77c1a8fd91) | ![image](https://github.com/Kid-Bean/server/assets/43364585/a2223ba2-d0b0-4288-aaae-a8a7f4eaefe7) |

| 내가 틀린 문제 상세 화면 | 내가 맞춘 문제 | 내가 맞춘 문제 상세 화면 - 이미지 맞추기 | 내가 맞춘 문제 상세 화면– 단어 맞추기 |
| :---: | :---: | :---: | :---: |
| ![image](https://github.com/Kid-Bean/server/assets/43364585/feb4a896-d5e1-4bf1-af9c-08bd3f01e286) | ![image](https://github.com/Kid-Bean/server/assets/43364585/1e7d372b-c85a-4899-b8b3-3e9aa8e48e42) | ![image](https://github.com/Kid-Bean/server/assets/43364585/07a07422-0838-4359-bf45-77cbbcfdb649) | ![image](https://github.com/Kid-Bean/server/assets/43364585/0b5d42ca-6c5d-4be0-9d37-bb282691fb53) |

| 자주 사용한 단어 분석 | 분석 결과 및 아이 음성 듣기 | 내가 등록한 문제 리스트 | 문제 등록 |
| :---: | :---: | :---: | :---: |
| ![image](https://github.com/Kid-Bean/server/assets/43364585/fd3fe183-9721-4cbe-9bee-f63da3db051a) | ![image](https://github.com/Kid-Bean/server/assets/43364585/32846c63-09c7-43ba-ac06-9ef83dacd302) | ![image](https://github.com/Kid-Bean/server/assets/43364585/270ac7e0-2a55-4e80-83a8-9555310ef98b) | ![image](https://github.com/Kid-Bean/server/assets/43364585/7bc277e3-188d-4753-b4ed-52d6ab13b9e4) |

| 그림 맞추기 수정/삭제 | 단어 맞추기 수정/삭제 | 질문 대답하기 수정/삭제 |
| :---: | :---: | :---: |
| ![image](https://github.com/Kid-Bean/server/assets/43364585/7ce8efdb-c1be-4467-95dc-c9c0a3bc253f) | ![image](https://github.com/Kid-Bean/server/assets/43364585/7f1ca4fb-5e27-409f-ad9f-02d26fc4cf61) | ![image](https://github.com/Kid-Bean/server/assets/43364585/93d620be-2f58-40d1-a35d-353b352d138b) | 
