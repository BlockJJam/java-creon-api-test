# 대신증권 Creon api dll을 com4j로 테스트

---

### 시스템 스펙

---

- Windows 64비트
- Intellij 64비트
- Java 8 32비트
- Com4j ← 여기서 주의할 점이 존재
- gradle

### 코드를 짜기 전에 Command Line에서 Com4j를 이용해야 한다

---

> 주의: Com4j 공식 사이트에서 Com4j에 대한 download 페이지가 404.. 해당 이미지 구성에 맞게 하나의 dir에 준비를 해두자(밑에 이미지) → 구글링
>
>
> ![a1](https://user-images.githubusercontent.com/57485510/163903390-75582dc5-89a8-429d-88a6-f5464d940efa.png)
>



- 대신증권 API는 [Creon Plus 다운로드 사이트](https://www.creontrade.com/g.ds?m=2194&p=12294&v=11951)에서 Creon Plus를 다운 받으면, C:\위치에 “dashin”과 “CREON” dir이 생성
- dashin > CYBOSPLUS 디렉터리의 dll을 활용
- java 8버전 32비트가 다운로드 되어있다는 가정 → CommandLine을 위 이미지의 디렉터리에 위치
- 다음과 같은 명령어로 convert dll to java 결과를 다음 이미지로 확인
    - 명령어부터

        ```bash
        PS C:\Users\TY\com4j-test> java -jar tlbimp.jar -o ds -p dashin.cplib "C:\daishin\CYBOSPLUS\cpdib.dll"
        Generating definitions from DSCBO1Lib
        PS C:\Users\TY\com4j-test> java -jar tlbimp.jar -o ds -p dashin.cputil "C:\daishin\CYBOSPLUS\cputil.dll"
        Generating definitions from CPUTILLib
        PS C:\Users\TY\com4j-test> java -jar tlbimp.jar -o ds -p dashin.cpsysdib "C:\daishin\CYBOSPLUS\CpSysDib.dll"
        Generating definitions from CPSYSDIBLib
        PS C:\Users\TY\com4j-test> java -jar tlbimp.jar -o ds -p dashin.cptrade "C:\daishin\CYBOSPLUS\cptrade.dll"
        Generating definitions from CPTRADELib
        ```

        - - jar tlbimp.jar : tlbimp.jar파일을 구동
        - - o ds: ds라는 디렉터리에 위치에
        - - p dashin.XXX : dashin 패키지에 XXX 패키지 안에
        - "C:\daishin\CYBOSPLUS\cpdib.dll": 해당 위치의 dll 파일을 java파일로 converting
    - java 파일 위치 확인

      ![a2](https://user-images.githubusercontent.com/57485510/163903396-673fcbda-2c52-49b7-b5a8-edf71bacd5d5.png)


### 파일은 준비되었으니, java파일을 옮기고, gradle부터 준비해보자

---

- gradle 프로젝트를 생성 > build.gradle 확인

    ```java
    ...
  
    dependencies {
        implementation 'org.jvnet.com4j:com4j:2.1'
        implementation 'org.jvnet.com4j:tlbimp:2.1'
    
        testImplementation 'org.junit.jupiter:junit-jupiter-api:5.7.0'
        testRuntimeOnly 'org.junit.jupiter:junit-jupiter-engine:5.7.0'
    }

    ...
    ```

    - com4j에 대한 의존 라이브러리를 설정해주자

- com4j로 만든 java 패키지는 어디에 옮기나?

  ![a3](https://user-images.githubusercontent.com/57485510/163903399-df10a55c-3516-44e5-86e4-281c460ab360.png)

    - src > main > java 안에 옮기고, module은 작성자가 개별로 생성

### 준비는 다 되었으니, 테스트 코드를 작성

---

- test > java > DashinBasicTest 에 들어있는 테스트방식
    - Creon API에 붙는 인터페이스는 ClassFactory 클래스에서 인스턴스를 createXXX 메서드로 생성할 수 있다
    - 종목 정보나 리스트는 로그인 없이 받아올 수 있지만, 시세 정보와 같은 가격 정보의 경우 로그인이 필요
    - 로그인 테스트 시에는 taskill로 프로세스를 죽여도 Creon Plus 모듈에서(coStart.exe, DibServer.exe) 재시작할 때 오류가 발생