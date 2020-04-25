---
layout : "Java"
title : "Java"
---

## Bigdecimal

- 숫자를 정밀하게 저장하고 표현하는 방법
- double은 소수점 정밀도에 있어 한계를 갖는다
  - double은 근사치를 저장하므로 정밀도가 떨어진다.
  - Bigdecimal은 내부적으로 십진수를 저장하므로 정밀도 보장
- java에서 돈과 소수점을 다루면 Bigdecimal 필수
- 단점 : 느린 속도와 불편한 사용법



### 사용법

```java
BigDecimal a = new BigDecimal("1.02"); // 초기화
BigDecimal b = new BigDecimal("1.011");

a.equals(b);
a.compareTo(b);  //비교법

a.add(b);
a.subtract(b);
a.multiply(b);
a.dibide(b);

```

