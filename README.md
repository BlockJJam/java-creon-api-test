# ������� Creon api dll�� com4j�� �׽�Ʈ

---

### �ý��� ����

---

- Windows 64��Ʈ
- Intellij 64��Ʈ
- Java 8 32��Ʈ
- Com4j �� ���⼭ ������ ���� ����
- gradle

### �ڵ带 ¥�� ���� Command Line���� Com4j�� �̿��ؾ� �Ѵ�

---

> ����: Com4j ���� ����Ʈ���� Com4j�� ���� download �������� 404.. �ش� �̹��� ������ �°� �ϳ��� dir�� �غ� �ص���(�ؿ� �̹���) �� ���۸�
>
>
> ![a1](https://user-images.githubusercontent.com/57485510/163903390-75582dc5-89a8-429d-88a6-f5464d940efa.png)
>



- ������� API�� [Creon Plus �ٿ�ε� ����Ʈ](https://www.creontrade.com/g.ds?m=2194&p=12294&v=11951)���� Creon Plus�� �ٿ� ������, C:\��ġ�� ��dashin���� ��CREON�� dir�� ����
- dashin > CYBOSPLUS ���͸��� dll�� Ȱ��
- java 8���� 32��Ʈ�� �ٿ�ε� �Ǿ��ִٴ� ���� �� CommandLine�� �� �̹����� ���͸��� ��ġ
- ������ ���� ��ɾ�� convert dll to java ����� ���� �̹����� Ȯ��
    - ��ɾ����

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

        - - jar tlbimp.jar : tlbimp.jar������ ����
        - - o ds: ds��� ���͸��� ��ġ��
        - - p dashin.XXX : dashin ��Ű���� XXX ��Ű�� �ȿ�
        - "C:\daishin\CYBOSPLUS\cpdib.dll": �ش� ��ġ�� dll ������ java���Ϸ� converting
    - java ���� ��ġ Ȯ��

      ![a2](https://user-images.githubusercontent.com/57485510/163903396-673fcbda-2c52-49b7-b5a8-edf71bacd5d5.png)


### ������ �غ�Ǿ�����, java������ �ű��, gradle���� �غ��غ���

---

- gradle ������Ʈ�� ���� > build.gradle Ȯ��

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

    - com4j�� ���� ���� ���̺귯���� ����������

- com4j�� ���� java ��Ű���� ��� �ű⳪?

  ![a3](https://user-images.githubusercontent.com/57485510/163903399-df10a55c-3516-44e5-86e4-281c460ab360.png)

    - src > main > java �ȿ� �ű��, module�� �ۼ��ڰ� ������ ����

### �غ�� �� �Ǿ�����, �׽�Ʈ �ڵ带 �ۼ�

---

- test > java > DashinBasicTest �� ����ִ� �׽�Ʈ���
    - Creon API�� �ٴ� �������̽��� ClassFactory Ŭ�������� �ν��Ͻ��� createXXX �޼���� ������ �� �ִ�
    - ���� ������ ����Ʈ�� �α��� ���� �޾ƿ� �� ������, �ü� ������ ���� ���� ������ ��� �α����� �ʿ�
    - �α��� �׽�Ʈ �ÿ��� taskill�� ���μ����� �׿��� Creon Plus ��⿡��(coStart.exe, DibServer.exe) ������� �� ������ �߻�